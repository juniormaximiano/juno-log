package com.mxi.juno_log.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        int statusCode,
        String error,
        String message,
        String path,
        LocalDateTime timestamp
) {
}
