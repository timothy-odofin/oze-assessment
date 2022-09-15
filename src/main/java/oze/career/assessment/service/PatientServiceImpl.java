package oze.career.assessment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.*;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import oze.career.assessment.exception.BadRequestException;
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

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static oze.career.assessment.util.MessageUtil.*;
import static oze.career.assessment.util.ParamName.AGE;
import static oze.career.assessment.util.ParamName.CREATE;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final StaffService staffService;

    @Override
    public ApiResponse<String> addPatient(PatientRequest payload) {
        Staff staff = staffService.findStaff(payload.getStaffId());
        Patient patient = Mapper.convertObject(payload, Patient.class);
        patient.setCreatedBy(staff);
        patientRepository.save(patient);
        return ApiResponse.<String>builder()
                .code(HttpStatus.CREATED)
                .data(String.format(PATIENT_MESSAGE, CREATE))
                .message(SUCCESS)
                .build();
    }

    @Override
    public ApiResponse<Object> uploadPatient(MultipartFile file) {
        return null;
    }

    @Override
    public ApiResponse<PatientResponseData> fetchPatients(Integer minAge,
                                                          UUID staffId,
                                                          Integer page,
                                                          Integer size) {
        staffService.findStaff(staffId);
        Page<Patient> patientPage = patientRepository.listByMinAge(minAge,
                PageRequest.of(page, size, Sort.by(AGE)));
        PatientResponseData data = PatientResponseData.builder()
                .patients(Mapper.convertList(patientPage.getContent(), PatientResponse.class))
                .currentPage(patientPage.getNumber())
                .totalItems(patientPage.getTotalElements())
                .totalPages(patientPage.getTotalPages())
                .build();
        return ApiResponse.<PatientResponseData>builder()
                .message(SUCCESS)
                .data(data)
                .build();
    }

    @Override
    public ResponseEntity<Resource> downloadPatient(Integer minAge,
                                                    UUID staffId,
                                                    Integer page,
                                                    Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<Resource> downloadSinglePatient(UUID staffId, String patientCode) {
        return null;
    }

    private List<PatientUploadResult> csvToPatient(InputStream is) {
        List<PatientUploadResult> dataList = new ArrayList<>();
//        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//             CSVParser csvParser = new CSVParser(fileReader,
//                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
//
//            List<Patient> tutorials = new ArrayList<Patient>();
//
//            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
//
//            for (CSVRecord csvRecord : csvRecords) {
//                Patient tutorial = Patient.builder()
//                        .age(csvRecord.get(PatientCsvHeader.AGE))
//                        .
//                .build();
//
//                tutorials.add(tutorial);
//            }

        return dataList;

    }

    private ByteArrayInputStream patientToCSV(List<Patient> patients) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (Patient result : patients) {
                csvPrinter.printRecord(Arrays.asList(
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
