package oze.career.assessment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MvcResult;
import oze.career.assessment.exception.RecordNotFoundException;
import oze.career.assessment.model.dto.request.StaffRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.dto.response.PatientResponseData;
import oze.career.assessment.model.entity.Patient;
import oze.career.assessment.model.entity.Staff;
import oze.career.assessment.repo.BaseIT;
import oze.career.assessment.repository.PatientRepository;
import oze.career.assessment.repository.StaffRepository;
import oze.career.assessment.util.DataUtils;
import oze.career.assessment.util.PatientEndpoint;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static oze.career.assessment.util.DataUtils.getAddStaffBadRequestData;
import static oze.career.assessment.util.MessageUtil.*;
import static oze.career.assessment.util.ParamName.CREATE;
import static oze.career.assessment.util.PatientEndpoint.FETCH;
import static oze.career.assessment.util.RestMapper.mapFromJson;
import static oze.career.assessment.util.RestMapper.mapToJson;
import static oze.career.assessment.util.StaffEndpoint.ADD_MEMBER;
import static oze.career.assessment.util.StaffEndpoint.BASE;

class PatientServiceImplTest extends BaseIT {
@Autowired
private PatientRepository patientRepository;
@Autowired
private StaffRepository staffRepository;
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer localDateTimeDeserializer =  new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        LocalDateDeserializer localDateDeserializer =  new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        module.addDeserializer(LocalDate.class, localDateDeserializer);
        objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        patientRepository.deleteAllInBatch();
        staffRepository.deleteAllInBatch();
    }

Staff initStaff(){
    return staffRepository.save(DataUtils.getStaffData());
}
List<Patient> initPatient(Staff staff){
   return patientRepository.saveAll(DataUtils.getPatientList(staff));
}
    @Test
    void test_fetch_patient_return_success() throws Exception {
       Staff staff = initStaff();
       List<Patient> patientList=initPatient(staff);
        MvcResult mvcResult = mockMvc.perform(get(PatientEndpoint.BASE + FETCH+"?minAge=2&staffId="+staff.getUuid().toString())
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse result = objectMapper.readValue(content, ApiResponse.class);
        assertEquals(result.getCode(), HttpStatus.OK.value());
        assertEquals(result.getMessage(), SUCCESS);
        PatientResponseData data = objectMapper.readValue(objectMapper.writeValueAsString(result.getData()),PatientResponseData.class);
        assertNotNull(data);
        assertEquals(data.getTotalPages(),1);
        assertEquals(data.getCurrentPage(),0);
        assertEquals(data.getTotalItems(),3);
        assertEquals(patientList.size(),4);
    }

    @Test
    void test_fetch_patient_return_record_not_found() throws Exception {
        Staff staff = initStaff();
        MvcResult mvcResult = mockMvc.perform(get(PatientEndpoint.BASE + FETCH+"?minAge=2&staffId="+staff.getUuid().toString())
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = objectMapper.readValue(content, ApiResponse.class);
        assertEquals(result.getCode(), HttpStatus.NOT_FOUND.value());
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),PATIENT_NOT_FOUND);
    }

    @Test
    void test_fetch_patient_return_staff_not_found() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(PatientEndpoint.BASE + FETCH+"?minAge=2&staffId="+DataUtils.testUUID())
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = objectMapper.readValue(content, ApiResponse.class);
        assertEquals(result.getCode(), HttpStatus.NOT_FOUND.value());
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),INVALID_STAFF_UUID);
    }

    @Test
    void test_fetch_patient_return_bad_request() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(PatientEndpoint.BASE + FETCH+"?minAge=2&staffId=45633")
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = objectMapper.readValue(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST.value());
        assertEquals(result.getMessage(), FAILED);
    }
}
