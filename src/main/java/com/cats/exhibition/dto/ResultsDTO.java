package com.cats.exhibition.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResultsDTO {
    private final List<RankBreedDTO> bestInBreeds = new ArrayList<>();
    private final List<RankCatDTO> bestFromPublic = new ArrayList<>();
}
