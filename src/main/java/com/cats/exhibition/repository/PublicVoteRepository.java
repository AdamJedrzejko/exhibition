package com.cats.exhibition.repository;

import com.cats.exhibition.dto.PublicVoteDTO;
import com.cats.exhibition.model.PublicVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicVoteRepository extends JpaRepository<PublicVote, Integer> {
    Optional<PublicVote> findByTicketId(Integer ticketId);
    @Query("SELECT new com.cats.exhibition.dto.PublicVoteDTO(a.catId, COUNT(a.catId)) " +
            "FROM PublicVote a " +
            "GROUP BY a.catId " +
            "HAVING COUNT(a.catId) = (SELECT MAX(cnt) FROM (SELECT COUNT(catId) AS cnt FROM PublicVote GROUP BY catId)) " +
            "ORDER BY COUNT(a.catId) DESC")
    List<PublicVoteDTO> countTotalPublicVoteByCatId();
}
