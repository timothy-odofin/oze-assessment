package oze.career.assessment.util;

import org.springframework.http.HttpStatus;
import oze.career.assessment.model.dto.request.StaffRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.dto.response.PatientResponse;
import oze.career.assessment.model.dto.response.PatientResponseData;
import oze.career.assessment.model.dto.response.StaffHelper;

import java.time.LocalDate;
import java.util.List;

import static oze.career.assessment.util.MessageUtil.*;
import static oze.career.assessment.util.ParamName.CREATE;

public class DataUtils {
    public static ApiResponse<String> getAddStaffSuccessData(){
        return ApiResponse.<String>builder()
                .code(HttpStatus.CREATED.value())
                .data(String.format(STAFF_UPDATED,CREATE))
                .message(SUCCESS)
                .build();
    }
    public static String getAddStaffBadRequestData(){
        return String.format(",",List.of(INVALID_FIRSTNAME, INVALID_LASTNAME));

    }

    public static StaffRequest getAddStaffValidPayload(){
        return StaffRequest.builder()
                .firstName("Oyejide")
                .lastName("Odofin")
                .middleName("Timothy")
                .build();

    }
    public static String testUUID(){
        return "e1608833-30c2-43fc-b0f7-5016c7344a12";

    }
    private static List<PatientResponse> patientResponses(){
        return List.of(PatientResponse.builder()
                        .age(20)
                        .createdBy(StaffHelper.builder()
                                .name("Test")
                                .uuid("e1608833-30c2-43fc-b0f7-5016c7344a12")
                                .name("Tester")
                                .build())
                        .lastVisitDate(LocalDate.now())
                        .patientCode("123")
                        .lastVisitDate(LocalDate.now().minusYears(5))
                        .build(),
                PatientResponse.builder()
                        .age(20)
                        .createdBy(StaffHelper.builder()
                                .name("Test")
                                .uuid("e1608833-30c2-43fc-b0f7-5016c7344a")
                                .name("Tester")
                                .build())
                        .lastVisitDate(LocalDate.now())
                        .patientCode("456")
                        .lastVisitDate(LocalDate.now().minusYears(2))
                        .build()
                );

    }
    public static ApiResponse<PatientResponseData> getPatientTestDataResponse(){
        PatientResponseData data = PatientResponseData.builder()
                .totalPages(1)
                .currentPage(0)
                .totalItems(Long.parseLong("2"))
                .patients(patientResponses())
                .build();
        return ApiResponse.<PatientResponseData>builder()
                .data(data)
                .message(SUCCESS)
                .code(HttpStatus.OK.value())
                .build();

    }
}
