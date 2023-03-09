package com.cats.exhibition.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PublicVoteDTO {
    private Integer catId;
    private Long votesNumber;
}
