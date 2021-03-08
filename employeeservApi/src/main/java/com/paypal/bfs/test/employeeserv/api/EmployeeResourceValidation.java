package com.paypal.bfs.test.employeeserv.api;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.api.utils.ErrorResponseModel;

import java.util.List;

public interface EmployeeResourceValidation {
    /**
     * Retrieves the {@link Employee} resource by id.
     *
     * @param id employee id.
     * @param employee employee resource.
     * @return {@link List<ErrorResponseModel>} validation errors.
     */
    List<ErrorResponseModel> validateEmployeeResource(String id, Employee employee);
}
