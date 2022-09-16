package oze.career.assessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.google.gson.internal.LinkedTreeMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import oze.career.assessment.exception.BadRequestException;
import oze.career.assessment.exception.RecordNotFoundException;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.dto.response.PatientResponseData;
import oze.career.assessment.service.PatientService;
import oze.career.assessment.util.DataUtils;
import oze.career.assessment.util.RestMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static oze.career.assessment.util.DataUtils.getAddStaffBadRequestData;
import static oze.career.assessment.util.MessageUtil.*;
import static oze.career.assessment.util.PatientEndpoint.BASE;
import static oze.career.assessment.util.PatientEndpoint.FETCH;
import static oze.career.assessment.util.RestMapper.mapFromJson;

@WebMvcTest(PatientController.class)
@Slf4j
class PatientControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private PatientService patientService;
    private ObjectMapper objectMapper;
    @BeforeEach
    public void setUp() {
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer localDateTimeDeserializer =  new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        LocalDateDeserializer localDateDeserializer =  new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        module.addDeserializer(LocalDate.class, localDateDeserializer);
        objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void test_fetch_patient_return_success() throws Exception {
        when(patientService.fetchPatients(anyInt(),any(),anyInt(),anyInt())).thenReturn(DataUtils.getPatientTestDataResponse());
        MvcResult mvcResult = mockMvc.perform(get(BASE + FETCH+"?minAge=2&staffId="+DataUtils.testUUID())
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse result = objectMapper.readValue(content, ApiResponse.class);
        assertEquals(result.getCode(), HttpStatus.OK.value());
        assertEquals(result.getMessage(), SUCCESS);
        PatientResponseData data = objectMapper.readValue(objectMapper.writeValueAsString(result.getData()),PatientResponseData.class);
        assertNotNull(data);
        assertEquals(data.getTotalPages(),1);
        assertEquals(data.getCurrentPage(),0);
        assertEquals(data.getTotalItems(),2);
        assertEquals(data.getPatients().size(),2);
    }

    @Test
    void test_fetch_patient_return_record_not_found() throws Exception {
        Mockito.doThrow(new RecordNotFoundException(PATIENT_NOT_FOUND))
                .when(patientService).fetchPatients(anyInt(),any(),anyInt(),anyInt());
        MvcResult mvcResult = mockMvc.perform(get(BASE + FETCH+"?minAge=2&staffId="+DataUtils.testUUID())
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = objectMapper.readValue(content, ApiResponse.class);
        assertEquals(result.getCode(), HttpStatus.NOT_FOUND.value());
        assertEquals(result.getMessage(), FAILED);
       assertEquals(result.getData(),PATIENT_NOT_FOUND);
    }

    @Test
    void test_fetch_patient_return_staff_not_found() throws Exception {
        Mockito.doThrow(new RecordNotFoundException(INVALID_STAFF_UUID))
                .when(patientService).fetchPatients(anyInt(),any(),anyInt(),anyInt());

        MvcResult mvcResult = mockMvc.perform(get(BASE + FETCH+"?minAge=2&staffId="+DataUtils.testUUID())
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = objectMapper.readValue(content, ApiResponse.class);
        assertEquals(result.getCode(), HttpStatus.NOT_FOUND.value());
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),INVALID_STAFF_UUID);
    }
}
