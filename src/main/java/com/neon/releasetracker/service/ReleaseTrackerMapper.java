package com.neon.releasetracker.service;


import com.neon.releasetracker.dto.CreateReleaseRequestDto;
import com.neon.releasetracker.dto.ReleaseDto;
import com.neon.releasetracker.dto.UpdateReleaseRequestDto;
import com.neon.releasetracker.model.Release;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReleaseTrackerMapper {

    ReleaseDto mapReleaseDto(Release release);

    Release mapRelease(CreateReleaseRequestDto createReleaseRequestDto);

    void updateReleaseFromDto(UpdateReleaseRequestDto releaseDto, @MappingTarget Release release);
}
