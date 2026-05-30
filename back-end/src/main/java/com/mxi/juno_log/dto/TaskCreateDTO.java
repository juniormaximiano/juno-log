package com.mxi.juno_log.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskCreateDTO(
        @NotBlank
        String title,
        @NotBlank
        String description
) {
}
