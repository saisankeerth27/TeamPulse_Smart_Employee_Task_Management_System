package com.teampulse.backend.service;

import com.teampulse.backend.dto.TaskRequest;
import com.teampulse.backend.dto.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest request);

    List<TaskResponse> getAllTasks();

    TaskResponse getTaskById(Long id);

    TaskResponse updateTask(Long id, TaskRequest request);

    void deleteTask(Long id);

    TaskResponse updateTaskStatus(Long id, String status);

}
