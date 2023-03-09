package com.cats.exhibition.controller;

import com.cats.exhibition.dto.CatDTO;
import com.cats.exhibition.dto.ExhibitionDisplayDTO;
import com.cats.exhibition.model.Cat;
import com.cats.exhibition.model.CatBreedType;
import com.cats.exhibition.service.CatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CatControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CatService catService;

    @Test
    public void addCat() throws Exception {
        Cat cat = new Cat(null, "John Doe", "Fluffy", CatBreedType.BRYTYJSKI);
        when(catService.save(cat)).thenReturn(cat);

        mockMvc.perform(post("/api/cat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cat)))
                .andExpect(status().isCreated());
    }

    @Test
    public void addCat_noBreed() throws Exception {
        Cat cat = new Cat(null, "John Doe", "Fluffy", null);

        mockMvc.perform(post("/api/cat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cat)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void cats() throws Exception {
        Cat cat1 = new Cat(null, "John Doe", "Fluffy", CatBreedType.BRYTYJSKI);
        Cat cat2 = new Cat(null, "Jane Smith", "Whiskers", CatBreedType.BRYTYJSKI);
        List<Cat> catList = Arrays.asList(cat1, cat2);
        List<CatDTO> catDTOList = Arrays.asList(new CatDTO(cat1.getOwner(), cat1.getName()),
                new CatDTO(cat2.getOwner(), cat2.getName()));
        ExhibitionDisplayDTO exhibitionDisplayDTO = new ExhibitionDisplayDTO(
                CatBreedType.BRYTYJSKI.toString(), catDTOList);
        when(catService.allCats()).thenReturn(Collections.singletonList(exhibitionDisplayDTO));

        mockMvc.perform(get("/api/cat/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].breed", is(CatBreedType.BRYTYJSKI.toString())))
                .andExpect(jsonPath("$[0].cats", hasSize(2)))
                .andExpect(jsonPath("$[0].cats[0].owner", is(cat1.getOwner())))
                .andExpect(jsonPath("$[0].cats[0].name", is(cat1.getName())))
                .andExpect(jsonPath("$[0].cats[1].owner", is(cat2.getOwner())))
                .andExpect(jsonPath("$[0].cats[1].name", is(cat2.getName())));
    }
}