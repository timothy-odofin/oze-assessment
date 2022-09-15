package oze.career.assessment.service;

import oze.career.assessment.model.dto.request.PatientRequest;
import oze.career.assessment.model.dto.response.ApiResponse;

public interface PatientService {
    ApiResponse<String> addPatient(PatientRequest payload);
}
