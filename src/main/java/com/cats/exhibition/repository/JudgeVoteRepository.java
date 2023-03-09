package com.cats.exhibition.repository;

import com.cats.exhibition.dto.TopCatsByBreedDTO;
import com.cats.exhibition.model.JudgeVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JudgeVoteRepository extends JpaRepository<JudgeVote, Integer> {
    @Query("SELECT NEW com.cats.exhibition.dto.TopCatsByBreedDTO(c.name, c.owner, c.breed, COALESCE(SUM(jv.points), 0L)) " +
            "FROM Cat c " +
            "LEFT JOIN JudgeVote jv ON jv.catId = c.id " +
            "GROUP BY c.id, c.breed " +
            "ORDER BY c.breed, COALESCE(SUM(jv.points), 0) DESC, c.name")
    List<TopCatsByBreedDTO> findTopCatsByBreed();

    List<JudgeVote> findByJudgeId(Integer judgeId);
}
