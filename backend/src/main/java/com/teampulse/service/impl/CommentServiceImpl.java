package com.teampulse.backend.service.impl;

import com.teampulse.backend.dto.CommentRequest;
import com.teampulse.backend.dto.CommentResponse;
import com.teampulse.backend.entity.Comment;
import com.teampulse.backend.entity.Employee;
import com.teampulse.backend.entity.Notification;
import com.teampulse.backend.entity.Task;
import com.teampulse.backend.exception.ResourceNotFoundException;
import com.teampulse.backend.repository.CommentRepository;
import com.teampulse.backend.repository.EmployeeRepository;
import com.teampulse.backend.repository.NotificationRepository;
import com.teampulse.backend.repository.TaskRepository;
import com.teampulse.backend.service.CommentService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl
        implements CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public CommentServiceImpl(
            CommentRepository commentRepository,
            TaskRepository taskRepository,
            EmployeeRepository employeeRepository,
            NotificationRepository notificationRepository,
            SimpMessagingTemplate messagingTemplate) {

        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;

    }

    @Override
    public CommentResponse createComment(CommentRequest request) {

        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task Not Found"));

        Employee employee =
                employeeRepository.findById(request.getEmployeeId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee Not Found"));

        Comment comment = Comment.builder()
                .message(request.getMessage())
                .task(task)
                .employee(employee)
                .build();

        Comment saved = commentRepository.save(comment);

        Notification notification = Notification.builder()
                .title("New Comment")
                .message(employee.getFirstName()
                        + " commented on: " + task.getTitle())
                .employee(task.getAssignedEmployee())
                .build();

        notificationRepository.save(notification);

        messagingTemplate.convertAndSendToUser(
                task.getAssignedEmployee().getId().toString(),
                "/queue/notifications",
                notification
        );

        return mapToResponse(saved);

    }

    @Override
    public List<CommentResponse> getCommentsByTask(Long taskId) {

        return commentRepository
                .findByTaskIdOrderByCreatedAtAsc(taskId)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    @Override
    public void deleteComment(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Comment Not Found"));

        commentRepository.delete(comment);

    }

    private CommentResponse mapToResponse(Comment comment) {

        return CommentResponse.builder()
                .id(comment.getId())
                .message(comment.getMessage())
                .employeeName(
                        comment.getEmployee().getFirstName())
                .taskId(comment.getTask().getId())
                .createdAt(comment.getCreatedAt())
                .build();

    }

}
