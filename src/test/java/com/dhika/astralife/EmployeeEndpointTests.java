package com.dhika.astralife;

import com.dhika.astralife.entity.EmployeeEntity;
import com.dhika.astralife.model.EmployeeModel;
import com.dhika.astralife.model.GenderEnumModel;
import com.dhika.astralife.model.SalaryModel;
import com.dhika.astralife.repository.EmployeeRepository;
import com.dhika.astralife.service.*;
import com.dhika.astralife.service.impl.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeEndpointTests {

    private EmployeeModel employee;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void init() throws ParseException {
        EmployeeModel employee = EmployeeModel.builder()
                .firstName("dhika")
                .lastName("ade putra")
                .hireDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"))
                .gender(GenderEnumModel.M)
                .birthDate(new SimpleDateFormat("yyyy-MM-dd").parse("1996-08-12"))
                .build();
        this.employee = employee;

    }

    @Test
    public void doGetAllEmployee_success() throws Exception {

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.get("/employee/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockReq).andExpect(status().isOk());

    }

    @Test
    public void doGetEmployee_success() throws Exception {

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.get("/employee/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockReq).andExpect(status().isOk());

    }

    @Test
    public void doAddEmployee_success() throws Exception {

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.employee));

        mockMvc.perform(mockReq).andExpect(status().isCreated());

    }

    @Test
    public void doUpdateEmployee_success() throws Exception {

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.patch("/employee/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.employee));

        mockMvc.perform(mockReq).andExpect(status().isOk());

    }

    @Test
    public void doGetSalaryEmployee_success() throws Exception {
        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.get("/employee/1/salary")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockReq).andExpect(status().isOk());
    }

    @Test
    public void doGetAllSalaryEmployee_success() throws Exception {
        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.get("/employee/1/salary/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockReq).andExpect(status().isOk());
    }

    @Test
    public void doAddSalaryEmployee() throws Exception {
        SalaryModel salaryModel = SalaryModel.builder()
                .salary(10000000)
                .fromDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-28"))
                .toDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-06-28"))
                .build();

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.post("/employee/1/salary")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(salaryModel));

        mockMvc.perform(mockReq).andExpect(status().isCreated());
    }

    @Test
    public void doUpdateCurrentSalaryEmployee_success() throws Exception {

        SalaryModel salaryModel = SalaryModel.builder()
                .salary(10000000)
                .toDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-07-28"))
                .build();

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.patch("/employee/1/salary")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(salaryModel));

        mockMvc.perform(mockReq).andExpect(status().isOk());

    }

}
