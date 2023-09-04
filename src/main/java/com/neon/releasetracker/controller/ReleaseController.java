package com.neon.releasetracker.controller;

import com.neon.releasetracker.dto.CreateReleaseRequestDto;
import com.neon.releasetracker.dto.ReleaseDto;
import com.neon.releasetracker.model.ReleaseFilterRequest;
import com.neon.releasetracker.dto.UpdateReleaseRequestDto;
import com.neon.releasetracker.model.ReleaseStatus;
import com.neon.releasetracker.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/release")
public class ReleaseController {

    @Autowired
    ReleaseService releaseService;

    @GetMapping
    public List<ReleaseDto> findAll(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String description,
                                    @RequestParam(required = false) ReleaseStatus status,
                                    @RequestParam(required = false) LocalDate releaseDate,
                                    @RequestParam(required = false) LocalDateTime createdAt,
                                    @RequestParam(required = false) LocalDateTime lastUpdateAt) {
        return releaseService.findAll(new ReleaseFilterRequest(name, description, status, releaseDate,
                createdAt, lastUpdateAt));
    }

    @GetMapping("/{id}")
    public ReleaseDto findById(@PathVariable("id") Long id) {
        return releaseService.findById(id);
    }

    @PostMapping
    public ReleaseDto create(@RequestBody CreateReleaseRequestDto createReleaseRequestDto) {
        return releaseService.create(createReleaseRequestDto);
    }

    @PutMapping("/{id}")
    public ReleaseDto updateById(@PathVariable("id") Long id, @RequestBody UpdateReleaseRequestDto updateReleaseRequestDto) {
        return releaseService.updateById(id, updateReleaseRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        releaseService.deleteById(id);
    }

}
