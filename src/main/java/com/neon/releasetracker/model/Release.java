package com.neon.releasetracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "rt_release")
@Getter
@Setter
public class Release {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ReleaseStatus status;
    private LocalDate releaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdateAt;
    @Version
    private Integer version;

}
