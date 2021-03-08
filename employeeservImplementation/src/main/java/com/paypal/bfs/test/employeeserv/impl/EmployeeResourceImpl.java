package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.EmployeeResourceValidation;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.api.utils.ErrorResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {
    /**
     * in memory database, indexed on employee id
     * map<empId, employeeResource>
     */
    private HashMap<Integer, Employee> employeeData = new HashMap<>();

    /**
     * setting up employee data for mock testing from unit test classes.
     */
    public void setEmployeeData(HashMap<Integer, Employee> employeeData) {
        this.employeeData = employeeData;
    }

    @Autowired
    private EmployeeResourceValidation employeeResourceValidation;

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {
        Integer employeeId = null;
        try {
            employeeId = Integer.valueOf(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!employeeData.containsKey(employeeId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Employee employee = employeeData.get(employeeId);

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createEmployee(String id, Employee employee) {

        List<ErrorResponseModel> errorMessages = employeeResourceValidation.validateEmployeeResource(id, employee);

        if(errorMessages.size()>0)
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);

        Integer employeeId = Integer.valueOf(id);
        if (employeeData.containsKey(employeeId)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        employeeData.put(employeeId, employee);

        return new ResponseEntity<>(employee, HttpStatus.CREATED);

    }
}
