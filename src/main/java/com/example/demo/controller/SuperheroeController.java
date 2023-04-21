package com.example.demo.controller;

import com.example.demo.model.Superheroe;
import com.example.demo.repository.SuperheroeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SuperheroeController {
    @Autowired
    SuperheroeRepository superheroeRepository;

    @GetMapping("/superheroes")
    public ResponseEntity<List<Superheroe>> getSuperheroes(@RequestParam(required = false) String searchString) {
        List<Superheroe> superheroes = new ArrayList<Superheroe>();
        if (searchString == null) superheroes.addAll(superheroeRepository.findAll());
        else superheroes.addAll(superheroeRepository.findByNameContaining(searchString));
        if (superheroes.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(superheroes, HttpStatus.OK);
    }
    @GetMapping("/superheroes/{id}")
    public ResponseEntity<Superheroe> getSuperheroeById(@PathVariable("id") long id) {
        Optional<Superheroe> superheroeData = superheroeRepository.findById(id);
        return superheroeData.map(superheroe -> new ResponseEntity<>(superheroe, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/superheroes")
    public ResponseEntity<Superheroe> createTutorial(@RequestBody Superheroe superheroe) {
        return new ResponseEntity<>(superheroeRepository.save(new Superheroe(superheroe.getName())), HttpStatus.CREATED);
    }
}