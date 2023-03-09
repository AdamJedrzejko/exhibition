package com.cats.exhibition.controller;

import com.cats.exhibition.dto.ResultsDTO;
import com.cats.exhibition.model.JudgeVote;
import com.cats.exhibition.model.PublicVote;
import com.cats.exhibition.service.JudgeVoteService;
import com.cats.exhibition.service.PublicVoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/cat")
public class VoteController {
    private final PublicVoteService publicVoteService;
    private final JudgeVoteService judgeVoteService;

    public VoteController(PublicVoteService publicVoteService,
                          JudgeVoteService judgeVoteService) {
        this.publicVoteService = publicVoteService;
        this.judgeVoteService = judgeVoteService;
    }

    @PatchMapping("/{catId}/{judgeId}/vote/{points}")
    public ResponseEntity<JudgeVote> voteJudge(@PathVariable Integer catId,
                                               @PathVariable Integer judgeId,
                                               @PathVariable Integer points) {
        List<JudgeVote> judgeVoteList = judgeVoteService.findByJudgeId(judgeId);
        if (points > 10 || points < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean uniqueVote = true;
        for (JudgeVote judgeVote : judgeVoteList) {
            if (judgeVote.getCatId().equals(catId)) {
                uniqueVote = false;
                break;
            }
        }
        if (uniqueVote) {
            return new ResponseEntity<>(judgeVoteService
                    .save(new JudgeVote(judgeId, catId, points)), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{catId}/{ticketId}/vote")
    public ResponseEntity<PublicVote> votePublic(@PathVariable Integer catId,
                                 @PathVariable Integer ticketId) {
        Optional<PublicVote> publicVoteOptional = publicVoteService.findByTicketId(ticketId);
        if (publicVoteOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(publicVoteService
                    .save(new PublicVote(ticketId, catId)), HttpStatus.CREATED);
        }
    }

    @GetMapping("/results")
    public ResponseEntity<ResultsDTO> showResults() {
        return new ResponseEntity<>(judgeVoteService.returnResults(), HttpStatus.OK);
    }
}
