package com.neon.releasetracker.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReleaseFilterRequest(
        String name,
        String description,
        ReleaseStatus status,
        LocalDate releaseDate,
        LocalDateTime createdAt,
        LocalDateTime lastUpdateAt) {
}
