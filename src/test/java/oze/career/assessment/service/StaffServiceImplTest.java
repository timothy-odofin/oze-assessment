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
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MvcResult;
import oze.career.assessment.exception.BadRequestException;
import oze.career.assessment.model.dto.request.StaffRequest;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.model.entity.Staff;
import oze.career.assessment.repo.BaseIT;
import oze.career.assessment.repository.PatientRepository;
import oze.career.assessment.repository.StaffRepository;
import oze.career.assessment.util.DataUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static oze.career.assessment.util.DataUtils.getAddStaffBadRequestData;
import static oze.career.assessment.util.MessageUtil.*;
import static oze.career.assessment.util.ParamName.CREATE;
import static oze.career.assessment.util.RestMapper.mapFromJson;
import static oze.career.assessment.util.RestMapper.mapToJson;
import static oze.career.assessment.util.StaffEndpoint.ADD_MEMBER;
import static oze.career.assessment.util.StaffEndpoint.BASE;

class StaffServiceImplTest  extends BaseIT {
@Autowired
private StaffRepository staffRepository;

    @BeforeEach
    void setUp() {
        staffRepository.deleteAllInBatch();
    }
    @Test
    void test_add_staff_return_success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD_MEMBER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.getAddStaffValidPayload())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), CREATED.value());
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(),String.format(STAFF_UPDATED,CREATE));
        List<Staff> staffList =staffRepository.findAll();
        assertNotNull(staffList);
        assertFalse(staffList.isEmpty());
        assertEquals(staffList.size(),1);
    }
    @Test
    void test_add_staff_return_bad_request() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD_MEMBER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(new StaffRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST.value());
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),getAddStaffBadRequestData());
    }
}
