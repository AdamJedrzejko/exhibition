package com.cats.exhibition.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VoteControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createVoteJudge() throws Exception {
        Integer catId = 1001;
        Integer judgeId = 1002;
        Integer points = 5;

        mockMvc.perform(patch("/api/cat/{catId}/{judgeId}/vote/{points}", catId, judgeId, points))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.judgeId", is(judgeId)))
                .andExpect(jsonPath("$.catId", is(catId)))
                .andExpect(jsonPath("$.points", is(5)));
    }

    @Test
    public void createVoteJudge_tooMuchPoints() throws Exception {
        Integer catId = 1003;
        Integer judgeId = 1004;
        Integer points = 15;

        mockMvc.perform(patch("/api/cat/{catId}/{judgeId}/vote/{points}", catId, judgeId, points))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createVoteJudge_alreadyExists() throws Exception {
        Integer catId = 1005;
        Integer judgeId = 1006;
        Integer points = 5;

        mockMvc.perform(patch("/api/cat/{catId}/{judgeId}/vote/{points}", catId, judgeId, points));
        mockMvc.perform(patch("/api/cat/{catId}/{judgeId}/vote/{points}", catId, judgeId, points))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createVotePublic() throws Exception {
        Integer catId = 1011;
        Integer ticketId = 1012;

        mockMvc.perform(patch("/api/cat/{catId}/{ticketId}/vote", catId, ticketId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticketId", is(ticketId)))
                .andExpect(jsonPath("$.catId", is(catId)));
    }

    @Test
    public void createVotePublic_alreadyExists() throws Exception {
        Integer catId = 1013;
        Integer ticketId = 1014;

        mockMvc.perform(patch("/api/cat/{catId}/{ticketId}/vote", catId, ticketId));
        mockMvc.perform(patch("/api/cat/{catId}/{ticketId}/vote", catId, ticketId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testShowResults() throws Exception {

        mockMvc.perform(get("/api/cat/results"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bestFromPublic[*].cat", hasItem("Filemon * MaxiZoo")))
                .andExpect(jsonPath("$.bestFromPublic[*].cat", hasItem("Kiki * MójPupil")))
                .andExpect(jsonPath("$.bestInBreeds[*].first[0].cat", hasItem("Kiki * MójPupil")))
                .andExpect(jsonPath("$.bestInBreeds[*].second[0].cat", hasItem("Mruczek * Zoopolis")))
                .andExpect(jsonPath("$.bestInBreeds[*].third[0].cat", hasItem("Lili * SklepZKotami")))
                .andExpect(jsonPath("$.bestInBreeds[*].second[*].cat", hasItem("Klara * KotyPl")))
                .andExpect(jsonPath("$.bestInBreeds[*].second[*].cat", hasItem("Puszek * Kotopedia")));
    }
}
