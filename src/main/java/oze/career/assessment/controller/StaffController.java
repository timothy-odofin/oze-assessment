package oze.career.assessment.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import oze.career.assessment.model.dto.request.StaffRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.dto.response.StaffResponse;
import oze.career.assessment.service.StaffService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static oze.career.assessment.util.ParamName.*;
import static oze.career.assessment.util.StaffEndpoint.BASE;
import static oze.career.assessment.util.StaffEndpoint.RETRIEVE_OR_UPDATE;

@RestController
@RequestMapping(BASE)
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;
    @PostMapping()
    ApiResponse<String> addStaff(@Valid @RequestBody StaffRequest payload){
        return staffService.addStaff(payload);
    }
    @GetMapping()
    @ApiOperation(value="Endpoint for retrieving single staff", response = StaffResponse.class, responseContainer = "List")
    ApiResponse<List<StaffResponse>> listStaff(@RequestParam(value=PAGE, defaultValue = PAGE_DEFAULT) int page,
                                               @RequestParam(value=SIZE,defaultValue = SIZE_DEFAULT) int size){
        return staffService.listStaff(page,size);
    }
    @GetMapping(RETRIEVE_OR_UPDATE)
    @ApiOperation(value="Endpoint for retrieving single staff", response = StaffResponse.class)
    public ApiResponse<StaffResponse> retrieveStaff(@PathVariable(UUID) UUID uuid) {
        return staffService.retrieveStaff(uuid);
    }
    @PatchMapping(RETRIEVE_OR_UPDATE)
    ApiResponse<String> updateStaff(@Valid @RequestBody StaffRequest payload, @PathVariable(UUID)UUID uuid){
        return staffService.updateStaff(payload, uuid);

    }
}
