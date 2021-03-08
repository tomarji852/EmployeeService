package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.api.utils.ErrorMessage;
import com.paypal.bfs.test.employeeserv.api.utils.ErrorResponseModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.flextrade.jfixture.FixtureAnnotations.initFixtures;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.MockitoAnnotations.initMocks;

public class EmployeeResourceValidationImplTest {

    @InjectMocks
    private EmployeeResourceValidationImpl sut;


    @Before
    public void setup() {
        initMocks(this);
        initFixtures(this);
    }

    @Test
    public void validateEmployeeResource_shouldReturn2ErrorMessages_whenEmployeeResourceIsNullAndRequestIdIsNotInteger(){
        String empId = "ab";
        Employee employee = null;

        List<ErrorResponseModel> response = sut.validateEmployeeResource(empId, employee);

        assertNotNull(response);
        assertEquals(response.size(), 2);
        assertEquals(response.get(0).getErrorMessage(), ErrorMessage.NON_INTEGER_ID);
        assertEquals(response.get(1).getField(), "EmployeeResource");
        assertEquals(response.get(1).getErrorMessage(), ErrorMessage.REQUIRED_FIELD);
    }

    @Test
    public void validateEmployeeResource_shouldReturnErrorMessage_whenResourceIdAndRequestIdNotMatching() throws ParseException {
        String empId = "1";
        Employee employee = new Employee();
        employee = new Employee();
        employee.setId(2);
        employee.setFirstName("Sunil");
        employee.setLastName("Tomar");
        Date dob = new SimpleDateFormat("dd-MMM-yyyy").parse("05-March-2021");
        employee.setDateOfBirth(dob);
        Address address = new Address();
        address.setCity("Mathura");
        address.setState("UP");
        address.setCountry("India");
        address.setLine1("line1");
        employee.setAddress(address);

        List<ErrorResponseModel> response = sut.validateEmployeeResource(empId, employee);

        assertNotNull(response);
        assertEquals(response.size(), 1);
        assertEquals(response.get(0).getErrorMessage(), ErrorMessage.URL_ID_REQUEST_ID_NOT_MATCHING);
    }

    @Test
    public void validateEmployeeResource_shouldReturn2ErrorMessages_whenFirstNameAndLastNameAreNotPresent() throws ParseException {
        String empId = "2";
        Employee employee = new Employee();
        employee = new Employee();
        employee.setId(2);
        employee.setFirstName("");
        Date dob = new SimpleDateFormat("dd-MMM-yyyy").parse("05-March-2021");
        employee.setDateOfBirth(dob);
        Address address = new Address();
        address.setCity("Mathura");
        address.setState("UP");
        address.setCountry("India");
        address.setLine1("line1");
        employee.setAddress(address);

        List<ErrorResponseModel> response = sut.validateEmployeeResource(empId, employee);

        assertNotNull(response);
        assertEquals(response.size(), 2);
        assertEquals(response.get(0).getField(), "FirstName");
        assertEquals(response.get(1).getField(), "LastName");
    }

    @Test
    public void validateEmployeeResource_shouldReturnErrorMessage_whenAddressFieldIsNotPresent() throws ParseException {
        String empId = "2";
        Employee employee = new Employee();
        employee = new Employee();
        employee.setId(2);
        employee.setFirstName("Sunil");
        employee.setLastName("Tomar");
        Date dob = new SimpleDateFormat("dd-MMM-yyyy").parse("05-March-2021");
        employee.setDateOfBirth(dob);

        List<ErrorResponseModel> response = sut.validateEmployeeResource(empId, employee);

        assertNotNull(response);
        assertEquals(response.size(), 1);
        assertEquals(response.get(0).getField(), "Address");
    }

    @Test
    public void validateEmployeeResource_shouldReturn4ErrorMessages_whenAddressIsEmpty() throws ParseException {
        String empId = "2";
        Employee employee = new Employee();
        employee.setId(2);
        employee.setFirstName("Sunil");
        employee.setLastName("Tomar");
        Date dob = new SimpleDateFormat("dd-MMM-yyyy").parse("05-March-2021");
        employee.setDateOfBirth(dob);
        Address address = new Address();
        employee.setAddress(address);

        List<ErrorResponseModel> response = sut.validateEmployeeResource(empId, employee);

        assertNotNull(response);
        assertEquals(response.size(), 4);
        assertEquals(response.get(0).getField(), "Address Line1");
        assertEquals(response.get(1).getField(), "Address city");
        assertEquals(response.get(2).getField(), "Address state");
        assertEquals(response.get(3).getField(), "Address country");
        assertEquals(response.get(3).getErrorMessage(), ErrorMessage.REQUIRED_FIELD);
    }

}
