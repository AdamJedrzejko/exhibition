package com.cats.exhibition.dto;

import com.cats.exhibition.model.CatBreedType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopCatsByBreedDTO {
    private String catName;
    private String catOwner;
    private CatBreedType breedName;
    private Long points;
}
