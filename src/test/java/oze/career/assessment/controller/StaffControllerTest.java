package oze.career.assessment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import oze.career.assessment.model.dto.response.ApiResponse;
import oze.career.assessment.service.StaffService;
import oze.career.assessment.util.DataUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static oze.career.assessment.util.MessageUtil.STAFF_UPDATED;
import static oze.career.assessment.util.MessageUtil.SUCCESS;
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
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void test_add_staff_return_success() throws Exception {
        when(staffService.addStaff(any())).thenReturn(DataUtils.getAddStaffSuccessData());
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD_MEMBER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.getAddStaffPayload())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), CREATED.value());
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(),String.format(STAFF_UPDATED,CREATE));
    }

    @Test
    void updateStaff() {
    }
}
