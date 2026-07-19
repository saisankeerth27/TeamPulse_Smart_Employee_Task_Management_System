package com.teampulse.backend.service.impl;

import com.teampulse.backend.dto.EmployeeRequest;
import com.teampulse.backend.dto.EmployeeResponse;
import com.teampulse.backend.entity.Employee;
import com.teampulse.backend.exception.ResourceNotFoundException;
import com.teampulse.backend.repository.EmployeeRepository;
import com.teampulse.backend.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
            throw new ResourceNotFoundException("Employee email already exists.");
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

        Employee employee = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee Not Found"));

        return mapToResponse(employee);

    }

    @Override
    public EmployeeResponse updateEmployee(Long id,
                                           EmployeeRequest request) {

        Employee employee = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee Not Found"));

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDesignation(request.getDesignation());
        employee.setSalary(request.getSalary());
        employee.setJoiningDate(request.getJoiningDate());

        Employee updatedEmployee = repository.save(employee);

        return mapToResponse(updatedEmployee);

    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee Not Found"));

        employee.setStatus(false);

        repository.save(employee);

    }

    @Override
    public List<EmployeeResponse> searchEmployee(String name) {

        return repository
                .findByFirstNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    public Page<Employee> getEmployeesWithPagination(int page, int size) {

        return repository.findAll(PageRequest.of(page, size));

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
