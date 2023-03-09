package com.cats.exhibition.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JudgeVote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "judgevotegenerator")
    private Integer id;
    private Integer judgeId;
    private Integer catId;
    private Integer points;

    public JudgeVote(Integer judgeId, Integer catId, Integer points) {
        this.judgeId = judgeId;
        this.catId = catId;
        this.points = points;
    }
}
