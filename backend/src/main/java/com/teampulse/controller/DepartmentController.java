package com.teampulse.backend.controller;

import com.teampulse.backend.dto.DepartmentRequest;
import com.teampulse.backend.dto.DepartmentResponse;
import com.teampulse.backend.service.DepartmentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(
            DepartmentService service) {

        this.service = service;

    }

    @PostMapping
    public DepartmentResponse createDepartment(

            @Valid
            @RequestBody
            DepartmentRequest request) {

        return service.createDepartment(request);

    }

    @GetMapping
    public List<DepartmentResponse>
    getAllDepartments() {

        return service.getAllDepartments();

    }

    @GetMapping("/{id}")
    public DepartmentResponse
    getDepartmentById(

            @PathVariable Long id) {

        return service.getDepartmentById(id);

    }

    @PutMapping("/{id}")
    public DepartmentResponse
    updateDepartment(

            @PathVariable Long id,

            @RequestBody
            DepartmentRequest request) {

        return service.updateDepartment(id, request);

    }

    @DeleteMapping("/{id}")
    public String deleteDepartment(

            @PathVariable Long id) {

        service.deleteDepartment(id);

        return "Department Deleted Successfully";

    }

}
