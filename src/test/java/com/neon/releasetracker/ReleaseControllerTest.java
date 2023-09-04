package com.neon.releasetracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neon.releasetracker.controller.ReleaseController;
import com.neon.releasetracker.dto.CreateReleaseRequestDto;
import com.neon.releasetracker.dto.ReleaseDto;
import com.neon.releasetracker.dto.UpdateReleaseRequestDto;
import com.neon.releasetracker.service.ReleaseService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReleaseController.class)
public class ReleaseControllerTest {

    private static final String BASE_URL = "/api/v1/release";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    ReleaseService releaseService;

    @Test
    public void testFindById() throws Exception {
        //given
        var releaseDto = mockReleaseDto();
        when(releaseService.findById(releaseDto.id())).thenReturn(releaseDto);

        //when
        var resultActions = mockMvc.perform(get(BASE_URL + "/" + releaseDto.id()));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(releaseDto.id()))
                .andExpect(jsonPath("$.name").value(releaseDto.name()))
                .andExpect(jsonPath("$.description").value(releaseDto.description()))
                .andExpect(jsonPath("$.status").value(releaseDto.status().name()))
                .andExpect(jsonPath("$.releaseDate").value(objectMapper.convertValue(releaseDto.releaseDate(), String.class)))
                .andExpect(jsonPath("$.createdAt").value(objectMapper.convertValue(releaseDto.createdAt(), String.class)))
                .andExpect(jsonPath("$.lastUpdateAt").value(objectMapper.convertValue(releaseDto.lastUpdateAt(), String.class)));
    }

    @Test
    public void testFindAll() throws Exception {
        //given
        int listSize = 10;
        var releaseDtoList = mockReleaseDtoList(listSize);
        when(releaseService.findAll(any())).thenReturn(releaseDtoList);

        //when
        var resultActions = mockMvc.perform(get(BASE_URL));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*]", hasSize(listSize)));
    }

    @Test
    public void testCreate() throws Exception {
        //given
        var releaseDto = mockReleaseDto();
        when(releaseService.create(any())).thenReturn(releaseDto);

        //when
        var requestBody = objectMapper.writeValueAsString(mockCreateReleaseRequestDto());

        var resultActions = mockMvc.perform(post(BASE_URL)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(releaseDto.id()))
                .andExpect(jsonPath("$.name").value(releaseDto.name()))
                .andExpect(jsonPath("$.description").value(releaseDto.description()))
                .andExpect(jsonPath("$.status").value(releaseDto.status().name()))
                .andExpect(jsonPath("$.releaseDate").value(objectMapper.convertValue(releaseDto.releaseDate(), String.class)))
                .andExpect(jsonPath("$.createdAt").value(objectMapper.convertValue(releaseDto.createdAt(), String.class)))
                .andExpect(jsonPath("$.lastUpdateAt").value(objectMapper.convertValue(releaseDto.lastUpdateAt(), String.class)));
    }

    @Test
    public void testDeleteById() throws Exception {
        //given
        var releaseId = 1;

        //when
        var resultActions = mockMvc.perform(delete(BASE_URL + "/" + releaseId));

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void testUpdateById() throws Exception {
        //given
        var releaseDto = mockReleaseDto();
        when(releaseService.updateById(any(), any())).thenReturn(releaseDto);

        //when
        var requestBody = objectMapper.writeValueAsString(mockUpdateReleaseRequestDto());

        var resultActions = mockMvc.perform(put(BASE_URL + "/" + releaseDto.id())
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(releaseDto.id()))
                .andExpect(jsonPath("$.name").value(releaseDto.name()))
                .andExpect(jsonPath("$.description").value(releaseDto.description()))
                .andExpect(jsonPath("$.status").value(releaseDto.status().name()))
                .andExpect(jsonPath("$.releaseDate").value(objectMapper.convertValue(releaseDto.releaseDate(), String.class)))
                .andExpect(jsonPath("$.createdAt").value(objectMapper.convertValue(releaseDto.createdAt(), String.class)))
                .andExpect(jsonPath("$.lastUpdateAt").value(objectMapper.convertValue(releaseDto.lastUpdateAt(), String.class)));
    }

    private ReleaseDto mockReleaseDto() {
        return Instancio.of(ReleaseDto.class).create();
    }

    private CreateReleaseRequestDto mockCreateReleaseRequestDto() {
        return Instancio.of(CreateReleaseRequestDto.class).create();
    }

    private UpdateReleaseRequestDto mockUpdateReleaseRequestDto() {
        return Instancio.of(UpdateReleaseRequestDto.class).create();
    }

    private List<ReleaseDto> mockReleaseDtoList(int size) {
        return Instancio.ofList(ReleaseDto.class).size(size).create();
    }
}