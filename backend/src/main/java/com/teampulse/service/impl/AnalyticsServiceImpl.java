package com.teampulse.backend.service.impl;

import com.teampulse.backend.dto.AnalyticsResponse;
import com.teampulse.backend.dto.EmployeePerformanceDTO;
import com.teampulse.backend.entity.Employee;
import com.teampulse.backend.enums.TaskStatus;
import com.teampulse.backend.repository.DepartmentRepository;
import com.teampulse.backend.repository.EmployeeRepository;
import com.teampulse.backend.repository.TaskRepository;
import com.teampulse.backend.service.AnalyticsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyticsServiceImpl
        implements AnalyticsService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final TaskRepository taskRepository;

    public AnalyticsServiceImpl(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            TaskRepository taskRepository) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.taskRepository = taskRepository;

    }

    @Override
    public AnalyticsResponse getAnalytics() {

        long totalEmployees =
                employeeRepository.countByStatusTrue();

        long totalDepartments =
                departmentRepository.count();

        long totalTasks =
                taskRepository.count();

        long completedTasks =
                taskRepository.countByStatus(
                        TaskStatus.COMPLETED);

        double completionRate = totalTasks > 0
                ? (double) completedTasks / totalTasks * 100
                : 0.0;

        double averageCompletionHours =
                calculateAverageCompletionHours();

        List<EmployeePerformanceDTO> topEmployees =
                getTopEmployees();

        return AnalyticsResponse.builder()
                .totalEmployees(totalEmployees)
                .totalDepartments(totalDepartments)
                .totalTasks(totalTasks)
                .completionRate(completionRate)
                .averageCompletionHours(averageCompletionHours)
                .topEmployees(topEmployees)
                .build();

    }

    private double calculateAverageCompletionHours() {

        List<com.teampulse.backend.entity.Task> completedTasks =
                taskRepository.findByStatus(TaskStatus.COMPLETED);

        if (completedTasks.isEmpty()) {
            return 0.0;
        }

        long totalHours = 0;

        for (com.teampulse.backend.entity.Task task :
                completedTasks) {

            totalHours +=
                    task.getEstimatedHours() != null
                            ? task.getEstimatedHours()
                            : 0;

        }

        return (double) totalHours / completedTasks.size();

    }

    private List<EmployeePerformanceDTO> getTopEmployees() {

        List<Object[]> top = taskRepository.getTopPerformers();

        List<EmployeePerformanceDTO> result = new ArrayList<>();

        int rank = 0;

        for (Object[] row : top) {

            if (rank >= 5) break;

            Long employeeId = (Long) row[0];
            Long completedCount = (Long) row[1];

            Employee employee = employeeRepository
                    .findById(employeeId)
                    .orElse(null);

            if (employee != null) {

                result.add(EmployeePerformanceDTO.builder()
                        .employeeId(employeeId)
                        .employeeName(employee.getFirstName())
                        .completedTasks(completedCount)
                        .workloadHours(0)
                        .build());

            }

            rank++;

        }

        return result;

    }

}
