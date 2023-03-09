package com.cats.exhibition.controller;

import com.cats.exhibition.dto.ExhibitionDisplayDTO;
import com.cats.exhibition.model.Cat;
import com.cats.exhibition.service.CatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/cat")
public class CatController {
    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @PostMapping
    public ResponseEntity<Cat> addCat(@RequestBody Cat cat) {
        return cat.getBreed() != null ?
                new ResponseEntity<>(catService.save(cat), HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExhibitionDisplayDTO>> cats() {
        return new ResponseEntity<>(catService.allCats(), HttpStatus.OK);
    }
}
