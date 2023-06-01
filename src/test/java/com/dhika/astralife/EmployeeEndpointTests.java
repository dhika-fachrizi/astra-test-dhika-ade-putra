package com.dhika.astralife;

import com.dhika.astralife.entity.EmployeeEntity;
import com.dhika.astralife.model.*;
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

    private DepartmentModel department;

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

        DepartmentModel department = DepartmentModel.builder()
                .departmentNo("A006")
                .departmentName("Infra")
                .build();
        this.department = department;
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
                .fromDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-07-28"))
                .toDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-08-28"))
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

    @Test
    public void doGetTitleEmployee_success() throws Exception {
        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.get("/employee/1/title")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockReq).andExpect(status().isOk());
    }

    @Test
    public void doGetAllTitleEmployee_success() throws Exception {
        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.get("/employee/1/title/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockReq).andExpect(status().isOk());
    }

    @Test
    public void doAddTitleEmployee() throws Exception {

        TitleModel titleModel = TitleModel.builder()
                .title("SV Programmer")
                .fromDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-07-28"))
                .toDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-08-28"))
                .build();

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.post("/employee/1/title")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(titleModel));

        mockMvc.perform(mockReq).andExpect(status().isCreated());
    }

    @Test
    public void doUpdateCurrentTitleEmployee_success() throws Exception {

        TitleModel titleModel = TitleModel.builder()
                .toDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-28"))
                .build();

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.patch("/employee/1/title")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(titleModel));

        mockMvc.perform(mockReq).andExpect(status().isOk());

    }

    @Test
    public void doGetManager_success() throws Exception {
        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.get("/employee/1/manager/A001")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockReq).andExpect(status().isOk());
    }

    @Test
    public void doGetAllManager_success() throws Exception {
        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.get("/employee/1/manager/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockReq).andExpect(status().isOk());
    }

    @Test
    public void doCreateManager_success() throws Exception {

        DepartmentManagerModel departmentManagerModel = DepartmentManagerModel.builder()
                .fromDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-07-28"))
                .toDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-08-28"))
                .build();

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.post("/employee/1/manager/A002")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentManagerModel));

        mockMvc.perform(mockReq).andExpect(status().isCreated());
    }

    @Test
    public void doUpdateManager_success() throws Exception {

        DepartmentManagerModel departmentManagerModel = DepartmentManagerModel.builder()
                .toDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-28"))
                .build();

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.patch("/employee/1/manager/A001")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentManagerModel));

        mockMvc.perform(mockReq).andExpect(status().isOk());

    }

    @Test
    public void doGetDepartmentEmployee_success() throws Exception {
        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.get("/employee/1/department/A001")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockReq).andExpect(status().isOk());
    }

    @Test
    public void doGetAllDepartmentEmployee_success() throws Exception {
        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.get("/employee/1/department/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockReq).andExpect(status().isOk());
    }

    @Test
    public void doCreateDepartmentEmployee_success() throws Exception {

        DepartmentEmployeeModel departmentEmployeeModel = DepartmentEmployeeModel.builder()
                .fromDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-03-28"))
                .toDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-28"))
                .build();

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.post("/employee/1/department/A002")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentEmployeeModel));

        mockMvc.perform(mockReq).andExpect(status().isCreated());
    }

    @Test
    public void doUpdateDepartmentEmployee_success() throws Exception {

        DepartmentEmployeeModel departmentEmployeeModel = DepartmentEmployeeModel.builder()
                .toDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-28"))
                .build();

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.patch("/employee/1/department/A001")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentEmployeeModel));

        mockMvc.perform(mockReq).andExpect(status().isOk());
    }

    @Test
    public void doGetAllDepartment_success() throws Exception {
        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.get("/department/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockReq).andExpect(status().isOk());

    }

    @Test
    public void doGetDepartment_success() throws Exception {
        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.get("/department/A001")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockReq).andExpect(status().isOk());

    }

    @Test
    public void doAddDepartment_success() throws Exception {

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.post("/department")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.department));

        mockMvc.perform(mockReq).andExpect(status().isCreated());

    }

    @Test
    public void doUpdateDepartment_success() throws Exception {

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.patch("/department/A001")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.employee));

        mockMvc.perform(mockReq).andExpect(status().isOk());

    }

}
