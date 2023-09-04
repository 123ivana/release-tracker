package com.neon.releasetracker;

import com.neon.releasetracker.dto.CreateReleaseRequestDto;
import com.neon.releasetracker.dto.ReleaseDto;
import com.neon.releasetracker.dto.UpdateReleaseRequestDto;
import com.neon.releasetracker.exception.ReleaseNotFoundException;
import com.neon.releasetracker.model.Release;
import com.neon.releasetracker.model.ReleaseFilterRequest;
import com.neon.releasetracker.model.ReleaseStatus;
import com.neon.releasetracker.repository.ReleaseJpaRepository;
import com.neon.releasetracker.service.ReleaseService;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ReleaseServiceTest extends BaseIT {

    @Autowired
    ReleaseService releaseService;

    @Autowired
    ReleaseJpaRepository releaseJpaRepository;

    @Test
    public void testFindById() {
        //given
        var release = saveRelease();

        //when
        var releaseDto = releaseService.findById(release.getId());

        //then
        Assertions.assertNotNull(releaseDto);
    }

    @Test
    public void testCreate() {
        //given
        var createReleaseRequestDto = mockCreateReleaseRequestDto();

        //when
        var release = releaseService.create(createReleaseRequestDto);

        //then
        Assertions.assertNotNull(release.id());
    }

    @Test
    public void testUpdate() {
        //given
        var release = saveRelease();

        //when
        var updateReleaseRequest = new UpdateReleaseRequestDto("updatedName", "updatedDescription",
                ReleaseStatus.ON_DEV, LocalDate.now(), LocalDateTime.now());
        var releaseDto = releaseService.updateById(release.getId(), updateReleaseRequest);

        //then
        Assertions.assertEquals(updateReleaseRequest.name(), releaseDto.name());
        Assertions.assertEquals(updateReleaseRequest.description(), releaseDto.description());
        Assertions.assertEquals(updateReleaseRequest.status(), releaseDto.status());
        Assertions.assertEquals(updateReleaseRequest.releaseDate(), releaseDto.releaseDate());
        Assertions.assertEquals(updateReleaseRequest.lastUpdateAt(), releaseDto.lastUpdateAt());
    }

    @Test
    public void testDelete() {
        //given
        var release = saveRelease();

        //when
        releaseService.deleteById(release.getId());

        //then
        assertThrows(ReleaseNotFoundException.class, () -> {
            releaseService.findById(release.getId());
        });
    }

    @Test
    public void testFindWithFilter() {
        //given
        var listSize = 10;
        var releaseName = "nameForReleaseList";
        saveReleaseList(releaseName, listSize);

        //when
        List<ReleaseDto> releaseDtos = releaseService.findAll(new ReleaseFilterRequest(releaseName, null, null, null, null, null));

        //then
        Assertions.assertEquals(releaseDtos.size(), listSize);
    }

    private Release saveRelease() {
        var release = mockRelease();
        return releaseJpaRepository.save(release);
    }

    private List<Release> saveReleaseList(String releaseName, int size) {
        var releaseList = mockReleaseList(releaseName, size);
        return releaseJpaRepository.saveAll(releaseList);
    }

    private CreateReleaseRequestDto mockCreateReleaseRequestDto() {
        return Instancio.create(CreateReleaseRequestDto.class);
    }

    private Release mockRelease() {
        return Instancio.create(Release.class);
    }

    private List<Release> mockReleaseList(String releaseName, int size) {
        return Instancio.ofList(Release.class).size(size).set(Select.field(Release::getName), releaseName).create();
    }
}
