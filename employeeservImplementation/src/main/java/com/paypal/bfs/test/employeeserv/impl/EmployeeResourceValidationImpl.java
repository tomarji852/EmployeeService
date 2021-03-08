package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResourceValidation;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.api.utils.ErrorMessage;
import com.paypal.bfs.test.employeeserv.api.utils.ErrorResponseModel;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation class for employee resource validations.
 */
@Service
public class EmployeeResourceValidationImpl implements EmployeeResourceValidation {

    @Override
    public List<ErrorResponseModel> validateEmployeeResource(String id, Employee employee) {
        List<ErrorResponseModel> errors = new ArrayList<>();
        Integer employeeId = null;
        try {
            employeeId = Integer.valueOf(id);
        } catch (Exception e) {
            e.printStackTrace();
            errors.add(new ErrorResponseModel("EmployeeId", ErrorMessage.NON_INTEGER_ID));
        }

        if (employee == null) {
            errors.add(new ErrorResponseModel("EmployeeResource", ErrorMessage.REQUIRED_FIELD));
            return errors;
        }

        if (!employee.getId().equals(employeeId))
            errors.add(new ErrorResponseModel("EmployeeId", ErrorMessage.URL_ID_REQUEST_ID_NOT_MATCHING));


        if (Strings.isBlank(employee.getFirstName()) || employee.getFirstName() == null)
            errors.add(new ErrorResponseModel("FirstName", ErrorMessage.REQUIRED_FIELD));

        if (Strings.isBlank(employee.getLastName()) || employee.getLastName() == null)
            errors.add(new ErrorResponseModel("LastName", ErrorMessage.REQUIRED_FIELD));

        if (employee.getAddress() == null) {
            errors.add(new ErrorResponseModel("Address", ErrorMessage.REQUIRED_FIELD));
            return errors;
        }

        if (Strings.isBlank(employee.getAddress().getLine1()) || employee.getAddress().getLine1() == null)
            errors.add(new ErrorResponseModel("Address Line1", ErrorMessage.REQUIRED_FIELD));

        if (Strings.isBlank(employee.getAddress().getCity()) || employee.getAddress().getCity() == null)
            errors.add(new ErrorResponseModel("Address city", ErrorMessage.REQUIRED_FIELD));

        if (Strings.isBlank(employee.getAddress().getState()) || employee.getAddress().getState() == null)
            errors.add(new ErrorResponseModel("Address state", ErrorMessage.REQUIRED_FIELD));

        if (Strings.isBlank(employee.getAddress().getCountry()) || employee.getAddress().getCountry() == null)
            errors.add(new ErrorResponseModel("Address country", ErrorMessage.REQUIRED_FIELD));


        return errors;
    }
}
