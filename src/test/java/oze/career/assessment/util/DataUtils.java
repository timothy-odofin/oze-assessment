package oze.career.assessment.util;

import org.springframework.http.HttpStatus;
import oze.career.assessment.model.dto.request.StaffRequest;
import oze.career.assessment.model.dto.response.ApiResponse;

import static oze.career.assessment.util.MessageUtil.STAFF_UPDATED;
import static oze.career.assessment.util.MessageUtil.SUCCESS;
import static oze.career.assessment.util.ParamName.CREATE;

public class DataUtils {
    public static ApiResponse<String> getAddStaffSuccessData(){
        return ApiResponse.<String>builder()
                .code(HttpStatus.CREATED.value())
                .data(String.format(STAFF_UPDATED,CREATE))
                .message(SUCCESS)
                .build();

    }
    public static StaffRequest getAddStaffPayload(){
        return StaffRequest.builder()
                .firstName("Oyejide")
                .lastName("Odofin")
                .middleName("Timothy")
                .build();

    }
}
