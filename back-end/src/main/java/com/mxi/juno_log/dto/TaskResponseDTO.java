package com.mxi.juno_log.dto;

import com.mxi.juno_log.domain.task.TaskStatus;

import java.time.LocalDateTime;

    public record TaskResponseDTO(
        long id,
        String taskName,
        String     description,
        TaskStatus status,
       LocalDateTime createdAt,
       LocalDateTime finishedAt
        ) {
}
