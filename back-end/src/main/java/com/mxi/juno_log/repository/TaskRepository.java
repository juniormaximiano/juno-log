package com.mxi.juno_log.repository;

import com.mxi.juno_log.domain.task.Task;
import com.mxi.juno_log.domain.task.TaskStatus;
import com.mxi.juno_log.dto.TaskResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<TaskResponseDTO> findAllByStatus(TaskStatus Status);

    List<TaskResponseDTO> findAllByOrderByCreatedAtDesc();

    long countByStatus(TaskStatus status);

    long count();
}
