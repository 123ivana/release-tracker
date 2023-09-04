package com.neon.releasetracker.service;

import com.neon.releasetracker.dto.CreateReleaseRequestDto;
import com.neon.releasetracker.model.ReleaseFilterRequest;
import com.neon.releasetracker.dto.ReleaseDto;
import com.neon.releasetracker.dto.UpdateReleaseRequestDto;
import com.neon.releasetracker.exception.ReleaseNotFoundException;
import com.neon.releasetracker.model.Release;
import com.neon.releasetracker.repository.ReleaseJpaRepository;
import com.neon.releasetracker.repository.ReleaseFilterJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReleaseService {

    @Autowired
    ReleaseJpaRepository releaseJpaRepository;
    @Autowired
    ReleaseTrackerMapper releaseTrackerMapper;
    @Autowired
    ReleaseFilterJpaRepository releaseFilterJpaRepository;

    public List<ReleaseDto> findAll(ReleaseFilterRequest releaseFilterRequest) {
        List<Release> releases = releaseFilterJpaRepository.findAllByFilter(releaseFilterRequest);
        return releases.stream().map(release ->
                releaseTrackerMapper.mapReleaseDto(release)).collect(Collectors.toList());
    }

    public ReleaseDto findById(Long id) {
        Release release = getRelease(id);
        return releaseTrackerMapper.mapReleaseDto(release);
    }

    public ReleaseDto updateById(Long id, UpdateReleaseRequestDto releaseDto) {
        Release release = getRelease(id);
        releaseTrackerMapper.updateReleaseFromDto(releaseDto, release);
        release = releaseJpaRepository.save(release);
        return releaseTrackerMapper.mapReleaseDto(release);
    }

    public void deleteById(Long id) {
        releaseJpaRepository.deleteById(id);
    }

    public ReleaseDto create(CreateReleaseRequestDto createReleaseRequestDto) {
        Release release = releaseTrackerMapper.mapRelease(createReleaseRequestDto);
        release = releaseJpaRepository.save(release);
        return releaseTrackerMapper.mapReleaseDto(release);

    }

    private Release getRelease(Long id) {
        return releaseJpaRepository.findById(id).orElseThrow(() ->
                new ReleaseNotFoundException("Release with ID " + id + " not found"));
    }
}
