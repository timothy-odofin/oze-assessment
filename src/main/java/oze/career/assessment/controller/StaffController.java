package oze.career.assessment.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import oze.career.assessment.model.dto.request.StaffRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.dto.response.StaffResponse;
import oze.career.assessment.service.StaffService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static oze.career.assessment.util.AppCode.*;
import static oze.career.assessment.util.MessageUtil.SUCCESS;
import static oze.career.assessment.util.ParamName.*;
import static oze.career.assessment.util.StaffEndpoint.BASE;
import static oze.career.assessment.util.StaffEndpoint.RETRIEVE_OR_UPDATE;

@RestController
@RequestMapping(BASE)
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;

    @PostMapping()
    @ApiOperation(value = "Endpoint for adding new staff into the system, only validated payload would be saved", response = String.class)
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = SUCCESS),
            @io.swagger.annotations.ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "An error occur kindly contact support"),
            @io.swagger.annotations.ApiResponse(code = 417, message = "Expectation failed"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Request not supported or Method type not valid")})
    ApiResponse<String> addStaff(@Valid @RequestBody StaffRequest payload) {
        return staffService.addStaff(payload);
    }

    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = SUCCESS),
            @io.swagger.annotations.ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "An error occur kindly contact support"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Request not supported or Method type not valid")})
    @GetMapping()
    @ApiOperation(value = "Endpoint for retrieving paginated list of staff profile", response = StaffResponse.class, responseContainer = "List")
    ApiResponse<List<StaffResponse>> listStaff(
            @ApiParam(value = PAGE_MEANING, required = true, allowEmptyValue = false)
            @RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
            @ApiParam(value = SIZE_MEANING, required = true, allowEmptyValue = false)
            @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size) {
        return staffService.listStaff(page, size);
    }

    @GetMapping(RETRIEVE_OR_UPDATE)
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = SUCCESS),
            @io.swagger.annotations.ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "An error occur kindly contact support"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Request not supported or Method type not valid")})
    @ApiOperation(value = "Endpoint for retrieving single staff profile", response = StaffResponse.class)
    public ApiResponse<StaffResponse> retrieveStaff(
            @ApiParam(value = STAFF_ID_MEANING, required = true, allowEmptyValue = false)
            @PathVariable(UUID) UUID uuid) {
        return staffService.retrieveStaff(uuid);
    }

    @PatchMapping(RETRIEVE_OR_UPDATE)
    @ApiOperation(value = "Endpoint for updating staff profile")
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = SUCCESS),
            @io.swagger.annotations.ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "An error occur kindly contact support"),
            @io.swagger.annotations.ApiResponse(code = 417, message = "Expectation failed"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Request not supported or Method type not valid")})
    ApiResponse<String> updateStaff(@Valid @RequestBody StaffRequest payload,
                                    @ApiParam(value = STAFF_ID_MEANING, required = true, allowEmptyValue = false)
                                    @PathVariable(UUID) UUID uuid) {
        return staffService.updateStaff(payload, uuid);

    }
}
