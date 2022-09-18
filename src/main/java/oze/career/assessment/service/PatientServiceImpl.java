package oze.career.assessment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import oze.career.assessment.exception.BadRequestException;
import oze.career.assessment.exception.RecordNotFoundException;
import oze.career.assessment.mapper.Mapper;
import oze.career.assessment.model.dto.request.PatientRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.dto.response.PatientResponse;
import oze.career.assessment.model.dto.response.PatientResponseData;
import oze.career.assessment.model.dto.response.PatientUploadResult;
import oze.career.assessment.model.entity.Patient;
import oze.career.assessment.model.entity.Staff;
import oze.career.assessment.repository.PatientRepository;
import oze.career.assessment.util.AppUtil;
import oze.career.assessment.util.PatientCsvHeader;

import java.io.*;
import java.time.LocalDate;
import java.util.UUID;
import java.util.*;

import static oze.career.assessment.util.AppUtil.getResourceBody;
import static oze.career.assessment.util.MessageUtil.*;
import static oze.career.assessment.util.ParamName.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final StaffService staffService;

    @Override
    public ApiResponse<String> addPatient(PatientRequest payload) {
        Staff staff = staffService.validateStaff(payload.getStaffId());
        if(payload.getLastVisitDate().isAfter(LocalDate.now()))
            throw new BadRequestException(INVALIDATE_LAST_VISIT);
        Patient patient = Mapper.convertObject(payload, Patient.class);
        patient.setCreatedBy(staff);
        patientRepository.save(patient);
        return ApiResponse.<String>builder()
                .code(HttpStatus.CREATED.value())
                .data(String.format(PATIENT_MESSAGE, CREATE))
                .message(SUCCESS)
                .build();
    }

    @Override
    public ApiResponse uploadPatient(UUID staffId, MultipartFile file) throws IOException {
        Staff staff = staffService.validateStaff(staffId);
        if(!AppUtil.hasCSVFormat(file))
            throw new BadRequestException(SERVER_ERROR_CV);
        List<PatientUploadResult> resultList = csvToPatient(file.getInputStream(),staff);
        if(resultList.isEmpty())
            return ApiResponse.<String>builder()
                    .code(HttpStatus.CREATED.value())
                    .data(String.format(PATIENT_MESSAGE, CREATE))
                    .message(SUCCESS)
                    .build();
        return ApiResponse.<List<PatientUploadResult>>builder()
                .code(HttpStatus.FAILED_DEPENDENCY.value())
                .data(resultList)
                .message(FAILED)
                .build();
    }

    private Page<Patient> listPatient(Integer minAge, UUID staffId, int page, int size) {
        staffService.validateStaff(staffId);
        Page<Patient> pages = patientRepository.listByMinAge(minAge,
                PageRequest.of(page, size, Sort.by(AGE)));
        if (pages.isEmpty())
            throw new RecordNotFoundException(PATIENT_NOT_FOUND);
        return pages;
    }

    @Override
    public ApiResponse<PatientResponseData> fetchPatients(Integer minAge,
                                                          UUID staffId,
                                                          Integer page,
                                                          Integer size) {
        Page<Patient> patientPage = listPatient(minAge, staffId, page, size);
        PatientResponseData data = PatientResponseData.builder()
                .patients(Mapper.convertList(patientPage.getContent(), PatientResponse.class))
                .currentPage(patientPage.getNumber())
                .totalItems(patientPage.getTotalElements())
                .totalPages(patientPage.getTotalPages())
                .build();
        return ApiResponse.<PatientResponseData>builder()
                .message(SUCCESS)
                .code(HttpStatus.OK.value())
                .data(data)
                .build();
    }

    @Override
    public ResponseEntity<Resource> downloadPatient(Integer minAge,
                                                    UUID staffId,
                                                    Integer page,
                                                    Integer size) {
        Page<Patient> patientPage = listPatient(minAge, staffId, page, size);
        return getResourceBody(new InputStreamResource(patientToCSV(patientPage.getContent())), "patient");
    }

    @Override
    public ApiResponse<String> deletePatient(UUID staffId, String dateFrom, String dateTo) {
        staffService.validateStaff(staffId);
        Optional<LocalDate> fromDate = AppUtil.validateLocalDate(dateFrom);
        Optional<LocalDate> toDate = AppUtil.validateLocalDate(dateTo);
        if (fromDate.isEmpty() || toDate.isEmpty())
            throw new BadRequestException(INVALID_DATE_FILTER);
        LocalDate fDate = fromDate.get();
        LocalDate tDate = toDate.get();
        if (patientRepository.checkPatientExistence(fDate, tDate, PageRequest.of(0, 1)).isEmpty())
            throw new RecordNotFoundException(PATIENT_NOT_FOUND);
        patientRepository.deletePatient(fDate, tDate);
        return ApiResponse.<String>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .data(String.format(PATIENT_MESSAGE, DELETED))
                .message(SUCCESS)
                .build();
    }

    @Override
    public ResponseEntity<Resource> downloadSinglePatient(UUID staffId, String patientCode) {
        staffService.validateStaff(staffId);
        Optional<Patient> patientsOptional = patientRepository.findByPatientCode(patientCode);
        if (patientsOptional.isEmpty())
            throw new RecordNotFoundException(PATIENT_NOT_FOUND);
        Patient patient = patientsOptional.get();
        return getResourceBody(new InputStreamResource(patientToCSV(Collections.singletonList(patient))), patient.getName());
    }
    private Optional<PatientUploadResult> validateAndSave(CSVRecord record, Staff postedBy){
        String age = record.get(PatientCsvHeader.AGE);
        String firstName = record.get(PatientCsvHeader.FIRST_NAME);
       String lastName =record.get(PatientCsvHeader.LAST_NAME);
        String middleName = record.get(PatientCsvHeader.MIDDLE_NAME);
        String lastVisitDate =record.get(PatientCsvHeader.LAST_VISIT_DATE);
        List<String> errorList = new ArrayList<>();
        Optional<Integer> ageOptional = AppUtil.isValidNumber(age);
        Optional<LocalDate> lastVisitDateOptional = AppUtil.validateLocalDate(lastVisitDate);
        if(ageOptional.isEmpty())
            errorList.add("Invalid age");
        if(!AppUtil.isValidString(firstName))
            errorList.add("Invalid first name");
        if(!AppUtil.isValidString(lastName))
            errorList.add("Invalid last name");
        if(lastVisitDateOptional.isEmpty())
            errorList.add("Invalid last visit date");
        if(!errorList.isEmpty())
            return Optional.of(PatientUploadResult.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .age(age)
                            .errors(errorList)
                            .lastVisitDate(lastVisitDate)
                            .middleName(middleName)
                    .build());
        patientRepository.save(Patient.builder()
                        .age(ageOptional.get())
                        .createdBy(postedBy)
                        .firstName(firstName)
                        .lastName(lastName)
                        .lastVisitDate(lastVisitDateOptional.get())
                        .middleName(middleName)
                .build());
        return Optional.empty();
    }

    private List<PatientUploadResult> csvToPatient(InputStream is, Staff postedBy) {
        List<PatientUploadResult> dataList = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Optional<PatientUploadResult> result = validateAndSave(csvRecord, postedBy);
                result.ifPresent(dataList::add);
            }
        }catch(IOException ioException){
           ioException.printStackTrace();
        }
        return dataList;
    }
    private ByteArrayInputStream patientToCSV(List<Patient> patients) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            //Create csv header
            csvPrinter.printRecord(Arrays.asList(
                    PatientCsvHeader.PATIENT_CODE,
                    PatientCsvHeader.NAME,
                    PatientCsvHeader.AGE,
                    PatientCsvHeader.LAST_VISIT_DATE,
                    PatientCsvHeader.DATE_CREATED));
            for (Patient result : patients) {
                csvPrinter.printRecord(Arrays.asList(
                        result.getPatientCode(),
                        result.getName(),
                        String.valueOf(result.getAge()),
                        result.getLastVisitDate().toString(),
                        AppUtil.convertDate(result.getDateCreated())));
            }
            csvPrinter.flush();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new BadRequestException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
