package com.cats.exhibition.service;

import com.cats.exhibition.model.PublicVote;
import com.cats.exhibition.repository.PublicVoteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublicVoteService {
    private final PublicVoteRepository publicVoteRepository;

    public PublicVoteService(PublicVoteRepository publicVoteRepository) {
        this.publicVoteRepository = publicVoteRepository;
    }

    public Optional<PublicVote> findByTicketId(Integer ticketId) {
        return publicVoteRepository.findByTicketId(ticketId);
    }

    public PublicVote save(PublicVote publicVote) {
        return publicVoteRepository.save(publicVote);
    }
}
