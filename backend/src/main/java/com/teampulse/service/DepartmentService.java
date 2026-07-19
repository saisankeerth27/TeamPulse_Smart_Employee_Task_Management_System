package com.teampulse.backend.service;

import com.teampulse.backend.dto.DepartmentRequest;
import com.teampulse.backend.dto.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest request);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse getDepartmentById(Long id);

    DepartmentResponse updateDepartment(Long id,
                                        DepartmentRequest request);

    void deleteDepartment(Long id);

}
