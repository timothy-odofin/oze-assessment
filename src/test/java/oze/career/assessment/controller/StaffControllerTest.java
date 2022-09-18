package oze.career.assessment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import oze.career.assessment.exception.BadRequestException;
import oze.career.assessment.model.dto.request.StaffRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.dto.response.StaffResponse;
import oze.career.assessment.service.StaffService;
import oze.career.assessment.util.DataUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static oze.career.assessment.util.DataUtils.getAddStaffBadRequestData;
import static oze.career.assessment.util.DataUtils.getStaffResponse;
import static oze.career.assessment.util.MessageUtil.*;
import static oze.career.assessment.util.ParamName.CREATE;
import static oze.career.assessment.util.RestMapper.mapFromJson;
import static oze.career.assessment.util.RestMapper.mapToJson;
import static oze.career.assessment.util.StaffEndpoint.*;
@WebMvcTest(StaffController.class)
@Slf4j
class StaffControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private StaffService staffService;
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
    void test_add_staff_return_success() throws Exception {
        when(staffService.addStaff(any())).thenReturn(DataUtils.getAddStaffSuccessData());
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD_MEMBER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.getAddStaffValidPayload())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse result = objectMapper.readValue(content, ApiResponse.class);
        assertEquals(result.getCode(), CREATED.value());
        assertEquals(result.getMessage(), SUCCESS);
        StaffResponse response = objectMapper.readValue(objectMapper.writeValueAsString(result.getData()),StaffResponse.class);
        assertEquals(response.getUuid(),getStaffResponse().getUuid());
    }
    @Test
    void test_add_staff_return_bad_request() throws Exception {
        Mockito.doThrow(new BadRequestException(getAddStaffBadRequestData()))
                .when(staffService).addStaff(any());
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD_MEMBER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.getAddStaffValidPayload())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST.value());
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),getAddStaffBadRequestData());
    }
}
