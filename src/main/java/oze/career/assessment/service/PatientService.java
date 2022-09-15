package oze.career.assessment.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import oze.career.assessment.model.dto.request.PatientFetchRequest;
import oze.career.assessment.model.dto.request.PatientRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.dto.response.PatientResponse;

import java.util.List;

public interface PatientService {
    ApiResponse<String> addPatient(PatientRequest payload);
    ApiResponse<Object> uploadPatient(MultipartFile file);
    ApiResponse<List<PatientResponse>> fetchPatients(PatientFetchRequest payload);
    ResponseEntity<Resource> downloadPatient(PatientFetchRequest payload);
    ResponseEntity<Resource> downloadSinglePatient(String staffId, String patientCode);

}
