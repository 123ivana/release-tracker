package com.neon.releasetracker.repository;

import com.neon.releasetracker.model.ReleaseFilterRequest;
import com.neon.releasetracker.model.Release;

import java.util.List;

public interface ReleaseFilterRepository {
    List<Release> findAllByFilter(ReleaseFilterRequest filterReleaseRequestDto);

}
