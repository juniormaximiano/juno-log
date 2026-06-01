package com.mxi.juno_log.dto;

public record TaskSummaryDTO(
        long Total,
        long Pending,
        long Done
) {
}
