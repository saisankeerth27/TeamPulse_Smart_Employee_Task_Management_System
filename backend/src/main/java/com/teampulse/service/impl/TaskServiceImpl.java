package com.teampulse.backend.service.impl;

import com.teampulse.backend.dto.TaskRequest;
import com.teampulse.backend.dto.TaskResponse;
import com.teampulse.backend.entity.Department;
import com.teampulse.backend.entity.Employee;
import com.teampulse.backend.entity.Notification;
import com.teampulse.backend.entity.Task;
import com.teampulse.backend.enums.TaskStatus;
import com.teampulse.backend.exception.ResourceNotFoundException;
import com.teampulse.backend.repository.DepartmentRepository;
import com.teampulse.backend.repository.EmployeeRepository;
import com.teampulse.backend.repository.NotificationRepository;
import com.teampulse.backend.repository.TaskRepository;
import com.teampulse.backend.service.TaskService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public TaskServiceImpl(TaskRepository taskRepository,
                           EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository,
                           NotificationRepository notificationRepository,
                           SimpMessagingTemplate messagingTemplate) {

        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;

    }

    @Override
    public TaskResponse createTask(TaskRequest request) {

        Employee employee = employeeRepository.findById(
                        request.getAssignedEmployeeId())

                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee Not Found"));

        Department department = departmentRepository.findById(
                        request.getDepartmentId())

                .orElseThrow(() ->
                        new ResourceNotFoundException("Department Not Found"));

        if (!employee.getDepartment().getId().equals(department.getId())) {

            throw new IllegalArgumentException(
                    "Selected employee does not belong to the selected department."
            );

        }

        Task task = Task.builder()

                .title(request.getTitle())

                .description(request.getDescription())

                .priority(request.getPriority())

                .dueDate(request.getDueDate())

                .estimatedHours(request.getEstimatedHours())

                .assignedEmployee(employee)

                .department(department)

                .status(TaskStatus.TODO)

                .build();

        Task savedTask = taskRepository.save(task);

        Notification notification = Notification.builder()
                .title("New Task Assigned")
                .message("You have been assigned: " + task.getTitle())
                .employee(employee)
                .build();

        notificationRepository.save(notification);

        messagingTemplate.convertAndSendToUser(
                employee.getId().toString(),
                "/queue/notifications",
                notification
        );

        return mapToResponse(savedTask);

    }

    @Override
    public List<TaskResponse> getAllTasks() {

        return taskRepository.findAll()

                .stream()

                .map(this::mapToResponse)

                .toList();

    }

    @Override
    public TaskResponse getTaskById(Long id) {

        Task task = taskRepository.findById(id)

                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task Not Found"));

        return mapToResponse(task);

    }

    @Override
    public TaskResponse updateTask(Long id,
                                   TaskRequest request) {

        Task task = taskRepository.findById(id)

                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task Not Found"));

        Employee employee = employeeRepository.findById(
                        request.getAssignedEmployeeId())

                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee Not Found"));

        Department department = departmentRepository.findById(
                        request.getDepartmentId())

                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department Not Found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setEstimatedHours(request.getEstimatedHours());
        task.setAssignedEmployee(employee);
        task.setDepartment(department);

        return mapToResponse(taskRepository.save(task));

    }

    @Override
    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)

                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task Not Found"));

        taskRepository.delete(task);

    }

    @Override
    public TaskResponse updateTaskStatus(Long id,
                                         String status) {

        Task task = taskRepository.findById(id)

                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task Not Found"));

        task.setStatus(TaskStatus.valueOf(status));

        return mapToResponse(taskRepository.save(task));

    }

    private TaskResponse mapToResponse(Task task) {

        return TaskResponse.builder()

                .id(task.getId())

                .title(task.getTitle())

                .description(task.getDescription())

                .priority(task.getPriority())

                .status(task.getStatus())

                .dueDate(task.getDueDate())

                .estimatedHours(task.getEstimatedHours())

                .assignedEmployee(

                        task.getAssignedEmployee().getFirstName()

                )

                .department(

                        task.getDepartment().getName()

                )

                .build();

    }

}
