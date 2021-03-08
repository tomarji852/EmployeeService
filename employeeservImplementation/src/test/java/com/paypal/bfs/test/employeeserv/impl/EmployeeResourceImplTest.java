package com.paypal.bfs.test.employeeserv.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.annotations.Fixture;
import com.paypal.bfs.test.employeeserv.api.EmployeeResourceValidation;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.api.utils.ErrorMessage;
import com.paypal.bfs.test.employeeserv.api.utils.ErrorResponseModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.flextrade.jfixture.FixtureAnnotations.initFixtures;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class EmployeeResourceImplTest {

    private static final String URL_TO_HIT = "/v1/bfs/employees/{id}";

    @Mock
    private EmployeeResourceValidation employeeResourceValidation;

    @InjectMocks
    private EmployeeResourceImpl sut;

    @Fixture
    private Employee employee;

    private JFixture jFixture = new JFixture();
    private MockMvc mockMvc;
    private ObjectMapper mapper;


    @Before
    public void setup() {
        initMocks(this);
        initFixtures(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void employeeGetById_shouldReturnBadRequest_WhenRequestComesWithInvalidEmployeeId()
            throws Exception {

        String empId = "ad";
        String url = (URL_TO_HIT).replace("{id}", empId);

        mockMvc.perform(get(url))
                .andDo(print()).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void employeeGetById_shouldReturnNotFound_WhenRequestComesWithvalidEmployeeIdAndNoResourceAvailable()
            throws Exception {

        String empId = "1";
        String url = (URL_TO_HIT).replace("{id}", empId);

        mockMvc.perform(get(url))
                .andDo(print()).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    public void employeeGetById_shouldReturnOk_WhenRequestComesWithvalidEmployeeIdAndResourceAvailable()
            throws Exception {

        String empId = "1";
        HashMap<Integer, Employee> dataMap = new HashMap<>();
        dataMap.put(1, employee);
        sut.setEmployeeData(dataMap);
        String url = (URL_TO_HIT).replace("{id}", empId);

        MvcResult response = mockMvc.perform(get(url))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        assertNotNull(response);
    }

    @Test
    public void createEmployee_shouldReturnBadRequest_WhenRequestInNotValid()
            throws Exception {

        String empId = "ad";
        employee = new Employee();
        employee.setFirstName("Sunil");
        employee.setLastName("Tomar");
        List<ErrorResponseModel> errorList = new ArrayList<>();
        errorList.add(new ErrorResponseModel("Id", ErrorMessage.REQUIRED_FIELD));

        when(employeeResourceValidation.validateEmployeeResource(anyString(), any(Employee.class))).thenReturn(errorList);

        String url = (URL_TO_HIT).replace("{id}", empId);
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee)))
                .andDo(print()).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void createEmployee_shouldReturnConflict_WhenResourceIsAlreadyCreated()
            throws Exception {

        String empId = "1";
        employee = new Employee();
        employee.setFirstName("Sunil");
        employee.setLastName("Tomar");
        HashMap<Integer, Employee> dataMap = new HashMap<>();
        dataMap.put(1, employee);
        sut.setEmployeeData(dataMap);
        List<ErrorResponseModel> errorList = new ArrayList<>();

        when(employeeResourceValidation.validateEmployeeResource(anyString(), any(Employee.class))).thenReturn(errorList);

        String url = (URL_TO_HIT).replace("{id}", empId);
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee)))
                .andDo(print()).andExpect(status().isConflict()).andReturn();
    }

    @Test
    public void createEmployee_shouldReturnCreated_WhenValidResourceRequesetAndIsNotAlreadyCreated()
            throws Exception {

        String empId = "2";
        employee = new Employee();
        employee.setId(2);
        employee.setFirstName("Sunil");
        employee.setLastName("Tomar");
        employee.setDateOfBirth(new Date());
        employee.setAddress(new Address());
        HashMap<Integer, Employee> dataMap = new HashMap<>();
        dataMap.put(1, employee);
        sut.setEmployeeData(dataMap);
        List<ErrorResponseModel> errorList = new ArrayList<>();

        when(employeeResourceValidation.validateEmployeeResource(anyString(), any(Employee.class))).thenReturn(errorList);

        String url = (URL_TO_HIT).replace("{id}", empId);
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee)))
                .andDo(print()).andExpect(status().isCreated()).andReturn();
    }

}
