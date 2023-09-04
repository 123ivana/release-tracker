package com.neon.releasetracker.dto;

import com.neon.releasetracker.model.ReleaseStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UpdateReleaseRequestDto(
        String name,
        String description,
        ReleaseStatus status,
        LocalDate releaseDate,
        LocalDateTime lastUpdateAt) {
}

