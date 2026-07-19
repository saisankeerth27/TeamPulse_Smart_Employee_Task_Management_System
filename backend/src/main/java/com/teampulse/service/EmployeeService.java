package com.teampulse.backend.service;

import com.teampulse.backend.dto.EmployeeRequest;
import com.teampulse.backend.dto.EmployeeResponse;
import com.teampulse.backend.entity.Employee;

import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse getEmployeeById(Long id);

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    void deleteEmployee(Long id);

    List<EmployeeResponse> searchEmployee(String name);

    Page<Employee> getEmployeesWithPagination(int page, int size);

    List<EmployeeResponse> getActiveEmployees();

    List<EmployeeResponse> getEmployeesByDepartment(Long departmentId);

}
