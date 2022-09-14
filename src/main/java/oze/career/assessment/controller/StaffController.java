package oze.career.assessment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oze.career.assessment.model.dto.request.StaffRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.service.StaffService;

import javax.validation.Valid;

import static oze.career.assessment.util.StaffEndpoint.ADD;
import static oze.career.assessment.util.StaffEndpoint.BASE;

@RestController
@RequestMapping(BASE)
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;
    @PostMapping(ADD)
    ApiResponse<String> addStaff(@Valid @RequestBody StaffRequest payload){
        return staffService.addStaff(payload);
    }
}
