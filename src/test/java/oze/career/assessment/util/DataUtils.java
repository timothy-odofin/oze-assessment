package oze.career.assessment.util;

import org.springframework.http.HttpStatus;
import oze.career.assessment.model.dto.request.StaffRequest;
import oze.career.assessment.model.dto.response.ApiResponse;

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
}
