package com.neon.releasetracker.dto;

public record RestApiErrorResponseDto(
        String status,
        String developerMessage) {
}
