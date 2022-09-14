package oze.career.assessment.service;

import oze.career.assessment.model.dto.request.StaffRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.dto.response.StaffResponse;
import oze.career.assessment.model.entity.Staff;

import java.util.List;
import java.util.UUID;

public interface StaffService {
    ApiResponse<String> addStaff(StaffRequest payload);
    Staff findStaff(UUID uuid);
    ApiResponse<List<StaffResponse>> listStaff(int page, int size);
}
