package com.cats.exhibition.service;

import com.cats.exhibition.dto.CatDTO;
import com.cats.exhibition.dto.ExhibitionDisplayDTO;
import com.cats.exhibition.model.Cat;
import com.cats.exhibition.model.CatBreedType;
import com.cats.exhibition.repository.CatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class CatService {
    private final CatRepository catRepository;

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Cat save(Cat cat) {
        return catRepository.save(cat);
    }

    public List<ExhibitionDisplayDTO> allCats() {
        List<Cat> catList = catRepository.findAll();
        Map<CatBreedType, List<Cat>> catsPerType = catList.stream()
                .collect(groupingBy(Cat::getBreed));
        List<ExhibitionDisplayDTO> exhibitionDisplayDTOList = new ArrayList<>();
        catsPerType.forEach((key, value) -> {
            List<CatDTO> catDTOList = new ArrayList<>();
            for (Cat cat : value) {
                catDTOList.add(new CatDTO(cat.getOwner(), cat.getName()));
            }
            exhibitionDisplayDTOList.add(
                    new ExhibitionDisplayDTO(key.toString(), catDTOList)
            );
        });
        return exhibitionDisplayDTOList;
    }
}
