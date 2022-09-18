package oze.career.assessment.util;

import org.springframework.http.HttpStatus;
import oze.career.assessment.model.dto.request.StaffRequest;
import oze.career.assessment.model.dto.response.*;
import oze.career.assessment.model.entity.Patient;
import oze.career.assessment.model.entity.Staff;

import java.time.LocalDate;
import java.util.List;

import static oze.career.assessment.util.MessageUtil.*;

public class DataUtils {
    public static StaffResponse getStaffResponse(){
        return StaffResponse.builder()
                .name("Odofin Oyejide")
                .uuid("Test")
                .photoImage("NA")
                .build();

    }
    public static ApiResponse<StaffResponse> getAddStaffSuccessData(){
        return ApiResponse.<StaffResponse>builder()
                .code(HttpStatus.CREATED.value())
                .data(getStaffResponse())
                .message(SUCCESS)
                .build();
    }
    public static String getAddStaffBadRequestData(){
        return String.format(",",List.of("firstName" + ": " + INVALID_FIRSTNAME,"lastName" + ": " +  INVALID_LASTNAME));

    }

    public static StaffRequest getAddStaffValidPayload(){
        return StaffRequest.builder()
                .firstName("Olakunle")
                .lastName("Balogun")
                .middleName("")
                .build();
    }
    public static Staff getStaffData(){
        return Staff.builder()
                .firstName("Olakunle")
                .lastName("Balogun")
                .middleName("Sango")
                .build();

    }

    public static List<Patient> getPatientList(Staff createdBy){
     return  List.of(
              Patient.builder()
                      .middleName("Alingo")
                      .lastVisitDate(LocalDate.now().minusYears(2))
                      .lastName("Bulus")
                      .firstName("Mark")
                      .createdBy(createdBy)
                      .age(1)
                      .build(),
               Patient.builder()
                       .middleName("Alonso")
                       .lastVisitDate(LocalDate.now().minusYears(2))
                       .lastName("Xavi")
                       .firstName("")
                       .createdBy(createdBy)
                       .age(40)
                       .build(),
               Patient.builder()
                       .middleName("Gabriel")
                       .lastVisitDate(LocalDate.now().minusYears(2))
                       .lastName("Jesus")
                       .firstName("")
                       .createdBy(createdBy)
                       .age(35)
                       .build(),
               Patient.builder()
                       .middleName("Jide")
                       .lastVisitDate(LocalDate.now().minusYears(2))
                       .lastName("Oloye")
                       .firstName("Julius")
                       .createdBy(createdBy)
                       .age(90)
                       .build()

       );
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
