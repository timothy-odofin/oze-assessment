package oze.career.assessment.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import oze.career.assessment.model.dto.request.PatientRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.dto.response.PatientResponseData;
import oze.career.assessment.service.PatientService;
import oze.career.assessment.validation.ValidUUID;

import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

import static oze.career.assessment.util.AppCode.*;
import static oze.career.assessment.util.MessageUtil.SUCCESS;
import static oze.career.assessment.util.ParamName.*;
import static oze.career.assessment.util.PatientEndpoint.*;

@RestController
@RequestMapping(BASE)
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @PostMapping(ADD)
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = SUCCESS),
            @io.swagger.annotations.ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "An error occur kindly contact support"),
            @io.swagger.annotations.ApiResponse(code = 417, message = "Expectation failed"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Request not supported or Method type not valid")})
    @ApiOperation(value="This endpoint accept single patient request, validate the request body and save the content, NB: only the valid request would be saved. NB: Staff with the given identification number(UUID) must exists",
    response = String.class)
    ApiResponse<String> addPatient(
            @Valid @RequestBody PatientRequest payload) {
        return patientService.addPatient(payload);
    }
    @PostMapping(UPLOAD)
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = SUCCESS),
            @io.swagger.annotations.ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "An error occur kindly contact support"),
            @io.swagger.annotations.ApiResponse(code = 417, message = "Expectation failed"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Request not supported or Method type not valid")})
    @ApiOperation(value="This endpoint accept bulk patient request in csv file, validate the content of the file and save the valid record. Any error response would be return. NB: Staff with the given identification number(UUID) must exists",
    response = String.class)
    ApiResponse uploadPatient(
            @ApiParam(value=STAFF_ID_MEANING, required = true, allowEmptyValue = false)
            @RequestParam(STAFF_ID) UUID staffId,
            @ApiParam(value=FILE_UPLOAD_MEANING, required = true, allowEmptyValue = false)
                              @RequestPart(FILE) MultipartFile file) throws IOException {
        return patientService.uploadPatient(staffId, file);

    }

    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = SUCCESS),
            @io.swagger.annotations.ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "An error occur kindly contact support"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Request not supported or Method type not valid")})
    @GetMapping(FETCH)
    @ApiOperation(value="This endpoint fetch patient with is greater than or equal to the given min age. NB: Staff with the given identification number(UUID) must exists",response = PatientResponseData.class)
    ApiResponse<PatientResponseData> fetchPatients(
            @ApiParam(value=MIN_AGE_MEANING, required = true, allowEmptyValue = false, defaultValue = MIN_AGE_DEFAULT)
            @RequestParam(MIN_AGE) Integer minAge,
            @ApiParam(value=STAFF_ID_MEANING, required = true, allowEmptyValue = false)
            @ValidUUID
            @RequestParam(STAFF_ID) UUID staffId,
            @ApiParam(value=PAGE_MEANING, required = true, allowEmptyValue = false, defaultValue = PAGE_DEFAULT)
            @RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
            @ApiParam(value=SIZE_MEANING, required = true, allowEmptyValue = false, defaultValue = SIZE_DEFAULT)
            @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size) {
        return patientService.fetchPatients(minAge, staffId, page, size);

    }

    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = SUCCESS),
            @io.swagger.annotations.ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "An error occur kindly contact support"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Request not supported or Method type not valid")})
    @GetMapping(DOWNLOAD_BULK)
    @ApiOperation(value="This endpoint download patient with is greater than or equal to the given min age into a csv, if no patient with such age exists, a record not found message would be returned.NB: Staff with the given identification number(UUID) must exists")
    ResponseEntity<Resource> downloadPatient(
            @ApiParam(value=MIN_AGE_MEANING, required = true, allowEmptyValue = false, defaultValue = MIN_AGE_DEFAULT)
            @RequestParam(MIN_AGE) Integer minAge,
            @ApiParam(value=STAFF_ID_MEANING, required = true, allowEmptyValue = false)
                                             @RequestParam(STAFF_ID) UUID staffId,
            @ApiParam(value=PAGE_MEANING, required = true, allowEmptyValue = false, defaultValue = PAGE_DEFAULT)
            @RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
            @ApiParam(value=SIZE_MEANING, required = true, allowEmptyValue = false, defaultValue = SIZE_DEFAULT)
                                             @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size) {
        return patientService.downloadPatient(minAge, staffId, page, size);
    }

    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = SUCCESS),
            @io.swagger.annotations.ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "An error occur kindly contact support"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Request not supported or Method type not valid")})
    @DeleteMapping(DELETE)
    @ApiOperation(value="Patient record with the given last_visit_date range can be deleted, if no such record exits Record not found message would be returned. NB: Staff with the given identification number(UUID) must exists")
    ApiResponse<String> deletePatient(
            @ApiParam(value=STAFF_ID_MEANING, required = true, allowEmptyValue = false)
            @RequestParam(STAFF_ID) UUID staffId,
            @ApiParam(value=DATE_FROM_MEANING, required = true, allowEmptyValue = false, format = "yyyy-MM-dd")
                                      @RequestParam(DATE_FROM) String dateFrom,
            @ApiParam(value=DATE_TO_MEANING, required = true, allowEmptyValue = false, format = "yyyy-MM-dd")
            @RequestParam(DATE_TO) String dateTo) {
        return patientService.deletePatient(staffId, dateFrom, dateTo);

    }
    @GetMapping(DOWNLOAD)
    @ApiResponses(value = {@io.swagger.annotations.ApiResponse(code = 200, message = SUCCESS),
            @io.swagger.annotations.ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @io.swagger.annotations.ApiResponse(code = 500, message = "An error occur kindly contact support"),
            @io.swagger.annotations.ApiResponse(code = 417, message = "Expectation failed"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Request not supported or Method type not valid")})
    @ApiOperation(value="Download a single patient record into csv, using the auto generated Id of the patient: NB: Staff with the given identification number(UUID) must exists")
    ResponseEntity<Resource> downloadSinglePatient(
            @ApiParam(value=STAFF_ID_MEANING, required = true, allowEmptyValue = false)
            @RequestParam(STAFF_ID) UUID staffId,
            @ApiParam(value=PATIENT_ID_MEANING, required = true, allowEmptyValue = false)
                                                   @RequestParam(PATIENT_CODE) String patientCode) {
        return patientService.downloadSinglePatient(staffId, patientCode);
    }

}
