package oze.career.assessment.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import oze.career.assessment.model.dto.request.PatientRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.dto.response.PatientResponseData;

import java.io.IOException;
import java.util.UUID;

public interface PatientService {
    ApiResponse<String> addPatient(PatientRequest payload);
    ApiResponse uploadPatient(UUID staffId, MultipartFile file) throws IOException;
    ApiResponse<PatientResponseData> fetchPatients(Integer minAge,
                                                   UUID staffId,
                                                   Integer page,
                                                   Integer size);
    ResponseEntity<Resource> downloadPatient(Integer minAge,
                                             UUID staffId,
                                             Integer page,
                                             Integer size);
    ApiResponse<String> deletePatient(UUID staffId, String dateFrom, String dateTo);
    ResponseEntity<Resource> downloadSinglePatient(UUID staffId, String patientCode);

}
