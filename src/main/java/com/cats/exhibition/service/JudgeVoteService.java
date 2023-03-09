package com.cats.exhibition.service;

import com.cats.exhibition.dto.PublicVoteDTO;
import com.cats.exhibition.dto.RankBreedDTO;
import com.cats.exhibition.dto.RankCatDTO;
import com.cats.exhibition.dto.ResultsDTO;
import com.cats.exhibition.dto.TopCatsByBreedDTO;
import com.cats.exhibition.model.Cat;
import com.cats.exhibition.model.CatBreedType;
import com.cats.exhibition.model.JudgeVote;
import com.cats.exhibition.repository.CatRepository;
import com.cats.exhibition.repository.JudgeVoteRepository;
import com.cats.exhibition.repository.PublicVoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class JudgeVoteService {
    private final PublicVoteRepository publicVoteRepository;
    private final JudgeVoteRepository judgeVoteRepository;
    private final CatRepository catRepository;

    public JudgeVoteService(PublicVoteRepository publicVoteRepository,
                            JudgeVoteRepository judgeVoteRepository,
                            CatRepository catRepository) {
        this.publicVoteRepository = publicVoteRepository;
        this.judgeVoteRepository = judgeVoteRepository;
        this.catRepository = catRepository;
    }

    public ResultsDTO returnResults() {
        ResultsDTO resultsDTO = new ResultsDTO();
        List<TopCatsByBreedDTO> topCatsByBreedDTOList = judgeVoteRepository.findTopCatsByBreed();
        for (PublicVoteDTO publicVote : publicVoteRepository.countTotalPublicVoteByCatId()) {
            Cat cat = catRepository.findById(publicVote.getCatId()).get();
            resultsDTO.getBestFromPublic().add(
                    new RankCatDTO(cat.getName() + " * " + cat.getOwner(), publicVote.getVotesNumber()));
        }
        Map<CatBreedType, List<TopCatsByBreedDTO>> topCatsPerBreed = topCatsByBreedDTOList.stream()
                .collect(groupingBy(TopCatsByBreedDTO::getBreedName));
        topCatsPerBreed.forEach((key, value) -> {
            RankBreedDTO rankBreedDTO = new RankBreedDTO(key.name());
            int currentRank = 1;
            Long tempPoints = value.get(0).getPoints();
            int counter = 0;
            for (TopCatsByBreedDTO topCatByBreedDTO : value) {
                counter += 1;
                if (counter > currentRank && tempPoints > topCatByBreedDTO.getPoints()) {
                    currentRank = counter;
                    tempPoints = topCatByBreedDTO.getPoints();
                }
                switch (currentRank) {
                    case 1 -> rankBreedDTO.getFirst().add(
                            new RankCatDTO(topCatByBreedDTO.getCatName() + " * " +
                                    topCatByBreedDTO.getCatOwner(), topCatByBreedDTO.getPoints()));
                    case 2 -> rankBreedDTO.getSecond().add(
                            new RankCatDTO(topCatByBreedDTO.getCatName() + " * " +
                                    topCatByBreedDTO.getCatOwner(), topCatByBreedDTO.getPoints()));
                    case 3 -> rankBreedDTO.getThird().add(
                            new RankCatDTO(topCatByBreedDTO.getCatName() + " * " +
                                    topCatByBreedDTO.getCatOwner(), topCatByBreedDTO.getPoints()));
                    default -> {
                        resultsDTO.getBestInBreeds().add(rankBreedDTO);
                        return;
                    }
                }
            }
            resultsDTO.getBestInBreeds().add(rankBreedDTO);
        });
        return resultsDTO;
    }

    public List<JudgeVote> findByJudgeId(Integer judgeId) {
        return judgeVoteRepository.findByJudgeId(judgeId);
    }

    public JudgeVote save(JudgeVote judgeVote) {
        return judgeVoteRepository.save(judgeVote);
    }
}
