package com.neon.releasetracker.dto;

import com.neon.releasetracker.model.ReleaseStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreateReleaseRequestDto(
        String name,
        String description,
        ReleaseStatus status,
        LocalDate releaseDate,
        LocalDateTime createdAt,
        LocalDateTime lastUpdateAt) {
}
