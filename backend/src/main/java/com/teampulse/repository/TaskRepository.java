package com.teampulse.backend.repository;

import com.teampulse.backend.entity.Task;
import com.teampulse.backend.enums.Priority;
import com.teampulse.backend.enums.TaskStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository
        extends JpaRepository<Task, Long> {

    List<Task> findByAssignedEmployeeId(Long employeeId);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(Priority priority);

    long countByStatus(TaskStatus status);

    long countByPriority(Priority priority);

    @Query("""
            SELECT
            t.assignedEmployee.id,
            COUNT(t)
            FROM Task t
            WHERE t.status = 'COMPLETED'
            GROUP BY t.assignedEmployee.id
            ORDER BY COUNT(t) DESC
            """)
    List<Object[]> getTopPerformers();

}
