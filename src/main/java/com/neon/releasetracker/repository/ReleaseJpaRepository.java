package com.neon.releasetracker.repository;

import com.neon.releasetracker.model.Release;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseJpaRepository extends JpaRepository<Release, Long> {

}
