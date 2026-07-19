package com.teampulse.backend.service.impl;

import com.teampulse.backend.dto.DepartmentRequest;
import com.teampulse.backend.dto.DepartmentResponse;
import com.teampulse.backend.entity.Department;
import com.teampulse.backend.exception.ResourceNotFoundException;
import com.teampulse.backend.repository.DepartmentRepository;
import com.teampulse.backend.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl
        implements DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentServiceImpl(
            DepartmentRepository repository) {

        this.repository = repository;

    }

    @Override
    public DepartmentResponse createDepartment(
            DepartmentRequest request) {

        if (repository.existsByName(request.getName())) {

            throw new RuntimeException(
                    "Department already exists");

        }

        Department department = Department.builder()

                .name(request.getName())

                .description(request.getDescription())

                .build();

        Department savedDepartment =
                repository.save(department);

        return mapToResponse(savedDepartment);

    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {

        return repository.findAll()

                .stream()

                .map(this::mapToResponse)

                .toList();

    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = repository.findById(id)

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Department Not Found"));

        return mapToResponse(department);

    }

    @Override
    public DepartmentResponse updateDepartment(

            Long id,

            DepartmentRequest request) {

        Department department = repository.findById(id)

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Department Not Found"));

        department.setName(request.getName());

        department.setDescription(request.getDescription());

        repository.save(department);

        return mapToResponse(department);

    }

    @Override
    public void deleteDepartment(Long id) {

        Department department = repository.findById(id)

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Department Not Found"));

        repository.delete(department);

    }

    private DepartmentResponse mapToResponse(
            Department department) {

        return DepartmentResponse.builder()

                .id(department.getId())

                .name(department.getName())

                .description(department.getDescription())

                .employeeCount(
                        department.getEmployees() == null ?
                                0 :
                                department.getEmployees().size())

                .build();

    }

}
