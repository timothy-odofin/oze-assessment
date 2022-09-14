package oze.career.assessment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oze.career.assessment.model.dto.request.StaffRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.service.StaffService;

import javax.validation.Valid;

import static oze.career.assessment.util.StaffEndpoint.BASE;

@RestController
@RequestMapping(BASE)
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;
    ApiResponse<String> addStaff(@Valid StaffRequest payload){
        return staffService.addStaff(payload);
    }
}
