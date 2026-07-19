package com.teampulse.backend.service.impl;

import com.teampulse.backend.dto.EmployeeRequest;
import com.teampulse.backend.dto.EmployeeResponse;
import com.teampulse.backend.entity.Employee;
import com.teampulse.backend.repository.EmployeeRepository;
import com.teampulse.backend.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Employee email already exists.");
        }

        long count = repository.count() + 1;
        String employeeCode = String.format("EMP%03d", count);

        Employee employee = Employee.builder()
                .employeeCode(employeeCode)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .designation(request.getDesignation())
                .salary(request.getSalary())
                .joiningDate(request.getJoiningDate())
                .status(true)
                .build();

        Employee savedEmployee = repository.save(employee);

        return mapToResponse(savedEmployee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        return null;
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        return null;
    }

    @Override
    public void deleteEmployee(Long id) {

    }

    private EmployeeResponse mapToResponse(Employee employee) {

        return EmployeeResponse.builder()
                .id(employee.getId())
                .employeeCode(employee.getEmployeeCode())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .designation(employee.getDesignation())
                .salary(employee.getSalary())
                .joiningDate(employee.getJoiningDate())
                .status(employee.getStatus())
                .build();

    }
}
