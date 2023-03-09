package com.cats.exhibition.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class RankBreedDTO {
    private String breed;
    private final List<RankCatDTO> first = new ArrayList<>();
    private final List<RankCatDTO> second = new ArrayList<>();
    private final List<RankCatDTO> third = new ArrayList<>();

    public RankBreedDTO(String breed) {
        this.breed = breed;
    }
}