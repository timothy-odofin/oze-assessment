package oze.career.assessment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.*;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import oze.career.assessment.exception.BadRequestException;
import oze.career.assessment.model.dto.request.PatientFetchRequest;
import oze.career.assessment.model.dto.request.PatientRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.dto.response.PatientResponse;
import oze.career.assessment.model.dto.response.PatientUploadResult;
import oze.career.assessment.model.entity.Patient;
import oze.career.assessment.util.PatientCsvHeader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService{
    @Override
    public ApiResponse<String> addPatient(PatientRequest payload) {
        return null;
    }

    @Override
    public ApiResponse<Object> uploadPatient(MultipartFile file) {
        return null;
    }

    @Override
    public ApiResponse<List<PatientResponse>> fetchPatients(PatientFetchRequest payload) {
        return null;
    }

    @Override
    public ResponseEntity<Resource> downloadPatient(PatientFetchRequest payload) {
        return null;
    }

    @Override
    public ResponseEntity<Resource> downloadSinglePatient(String staffId, String patientCode) {
        return null;
    }

    private  List<PatientUploadResult> csvToPatient(InputStream is) {
        List<PatientUploadResult> dataList =new ArrayList<>();
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
//            for (Tutorial tutorial : tutorials) {
//                List<String> data = Arrays.asList(
//                        String.valueOf(tutorial.getId()),
//                        tutorial.getTitle(),
//                        tutorial.getDescription(),
//                        String.valueOf(tutorial.isPublished())
//                );
//
//                csvPrinter.printRecord(data);
//            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new BadRequestException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
