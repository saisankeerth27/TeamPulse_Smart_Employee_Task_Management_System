package com.teampulse.backend.service.impl;

import com.teampulse.backend.dto.DashboardResponse;
import com.teampulse.backend.enums.TaskStatus;
import com.teampulse.backend.repository.DepartmentRepository;
import com.teampulse.backend.repository.EmployeeRepository;
import com.teampulse.backend.repository.TaskRepository;
import com.teampulse.backend.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl
        implements DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final TaskRepository taskRepository;

    public DashboardServiceImpl(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            TaskRepository taskRepository) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.taskRepository = taskRepository;

    }

    @Override
    public DashboardResponse getDashboard() {

        return DashboardResponse.builder()

                .totalEmployees(
                        employeeRepository.countByStatusTrue()
                )

                .totalDepartments(
                        departmentRepository.count()
                )

                .totalTasks(
                        taskRepository.count()
                )

                .completedTasks(
                        taskRepository.countByStatus(
                                TaskStatus.COMPLETED)
                )

                .inProgressTasks(
                        taskRepository.countByStatus(
                                TaskStatus.IN_PROGRESS)
                )

                .todoTasks(
                        taskRepository.countByStatus(
                                TaskStatus.TODO)
                )

                .blockedTasks(
                        taskRepository.countByStatus(
                                TaskStatus.BLOCKED)
                )

                .build();

    }

}
