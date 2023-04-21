package com.example.demo.controller;

import com.example.demo.model.Superheroe;
import com.example.demo.repository.SuperheroeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.*;

import static org.mockito.Mockito.when;

@WebMvcTest(SuperheroeController.class)
class SuperheroeControllerTest {

    @MockBean
    SuperheroeRepository superheroeRepository;

    @Autowired
    private MockMvc mockMvc;
    @Test
    void shouldReturnSuperheroe() throws Exception {
        long id = 1L;
        Superheroe superheroe = new Superheroe( id,"NombreDeEste");
        when(superheroeRepository.findById(id)).thenReturn(Optional.of(superheroe));
        mockMvc.perform(get("/api/superheroes/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(superheroe.getName()))
                .andDo(print());
    }

    @Test
    void shouldNotReturnSuperheroe() throws Exception {
        long id = 1L;
        mockMvc.perform(get("/api/superheroes/{id}", id)).andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldReturnSuperheroes() throws Exception {
        List<Superheroe> superheroes = new ArrayList<>(
                Arrays.asList(
                        new Superheroe(1L, "uno"),
                        new Superheroe(2L, "dos"),
                        new Superheroe(3L, "tres")
                )
        );
        when(superheroeRepository.findAll()).thenReturn(superheroes);
        mockMvc.perform(get("/api/superheroes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(superheroes.size()))
                .andDo(print());
    }

    @Test
    void shouldNotReturnSuperheroes() throws Exception {
        mockMvc.perform(get("/api/superheroes"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void shouldReturnFilteredSuperheroes() throws Exception {
        List<Superheroe> superheroes = new ArrayList<>(
                Arrays.asList(
                        new Superheroe(1L, "Spiderman"),
                        new Superheroe(3L, "Ironman")
                )
        );
        String searchString = "man";
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("searchString", searchString);

        when(superheroeRepository.findByNameContaining(searchString)).thenReturn(superheroes);
        mockMvc.perform(get("/api/superheroes").params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(superheroes.size()))
                .andDo(print());

        superheroes = Collections.emptyList();

        when(superheroeRepository.findByNameContaining(searchString)).thenReturn(superheroes);
        mockMvc.perform(get("/api/superheroes").params(paramsMap))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

}