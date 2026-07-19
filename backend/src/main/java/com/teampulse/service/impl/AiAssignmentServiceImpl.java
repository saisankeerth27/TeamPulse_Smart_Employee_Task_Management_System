package com.teampulse.backend.service.impl;

import com.teampulse.backend.dto.TaskRecommendationResponse;
import com.teampulse.backend.entity.Employee;
import com.teampulse.backend.entity.Task;
import com.teampulse.backend.enums.TaskStatus;
import com.teampulse.backend.repository.EmployeeRepository;
import com.teampulse.backend.repository.TaskRepository;
import com.teampulse.backend.service.AiAssignmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiAssignmentServiceImpl
        implements AiAssignmentService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    public AiAssignmentServiceImpl(
            EmployeeRepository employeeRepository,
            TaskRepository taskRepository) {

        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;

    }

    @Override
    public TaskRecommendationResponse recommendEmployee(
            Long departmentId) {

        List<Employee> employees =
                employeeRepository
                        .findByDepartmentIdAndStatusTrue(
                                departmentId);

        if (employees.isEmpty()) {

            return TaskRecommendationResponse.builder()
                    .reason("No active employees in this department")
                    .score(-1.0)
                    .build();

        }

        Employee bestEmployee = null;
        double bestScore = -1;
        int bestActiveTasks = 0;
        int bestWorkloadHours = 0;

        for (Employee employee : employees) {

            List<Task> tasks =
                    taskRepository.findByAssignedEmployeeId(
                            employee.getId());

            int activeTasks = 0;
            int totalHours = 0;

            for (Task task : tasks) {

                if (task.getStatus() != TaskStatus.COMPLETED) {

                    activeTasks++;

                    totalHours +=
                            task.getEstimatedHours() != null
                                    ? task.getEstimatedHours()
                                    : 0;

                }

            }

            double score = 100
                    - (activeTasks * 10)
                    - (totalHours * 2);

            if (score > bestScore) {

                bestScore = score;
                bestEmployee = employee;
                bestActiveTasks = activeTasks;
                bestWorkloadHours = totalHours;

            }

        }

        return TaskRecommendationResponse.builder()
                .employeeId(bestEmployee.getId())
                .employeeName(bestEmployee.getFirstName())
                .department(
                        bestEmployee.getDepartment().getName())
                .activeTasks(bestActiveTasks)
                .workloadHours(bestWorkloadHours)
                .score(bestScore)
                .reason(
                        "Lowest workload and task count"
                )
                .build();

    }

}
