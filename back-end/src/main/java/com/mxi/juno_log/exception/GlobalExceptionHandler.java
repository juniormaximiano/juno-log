package com.mxi.juno_log.exception;

import com.mxi.juno_log.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleResponseStatusException(ResponseStatusException e, HttpServletRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                e.getStatusCode().value(),
                e.getStatusCode().toString(),
                e.getReason(),
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(e.getStatusCode()).body(error);
    }

}
