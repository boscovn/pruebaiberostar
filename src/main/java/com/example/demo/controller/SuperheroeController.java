package com.example.demo.controller;

import com.example.demo.annotations.LogExecutionTime;
import com.example.demo.model.Superheroe;
import com.example.demo.repository.SuperheroeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Operation(summary = "Get a list of all superheroes or some superheroes that contain a substring in its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of superheroes",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Superheroe.class)))}),
            @ApiResponse(responseCode = "204", description = "No superheroes match criteria",
                    content = @Content)
    })
    @GetMapping("/superheroes")
    @Cacheable("myCache")
    @LogExecutionTime
    public ResponseEntity<List<Superheroe>> getSuperheroes(@RequestParam(required = false) String searchString) {
        List<Superheroe> superheroes = new ArrayList<Superheroe>();
        if (searchString == null) superheroes.addAll(superheroeRepository.findAll());
        else superheroes.addAll(superheroeRepository.findByNameContaining(searchString));
        if (superheroes.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(superheroes, HttpStatus.OK);
    }

    @Operation(summary = "Get a superhero given a specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Superhero",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Superheroe.class))}),
            @ApiResponse(responseCode = "404", description = "No superhero with that ID",
                    content = @Content)
    })
    @GetMapping("/superheroes/{id}")
    @Cacheable("myCache")
    @LogExecutionTime
    public ResponseEntity<Superheroe> getSuperheroeById(@PathVariable("id") long id) {
        Optional<Superheroe> superheroeData = superheroeRepository.findById(id);
        return superheroeData.map(superheroe -> new ResponseEntity<>(superheroe, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/superheroes")
    @ApiResponse(responseCode = "201", description = "Superhero we just created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Superheroe.class))})
    @Operation(summary = "Create a superheroe")
    @LogExecutionTime
    public ResponseEntity<Superheroe> createSuperheroe(@RequestBody Superheroe superheroe) {
        return new ResponseEntity<>(superheroeRepository.save(new Superheroe(superheroe.getName())), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a superhero given a specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Superhero",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Superheroe.class))}),
            @ApiResponse(responseCode = "404", description = "No superhero with that ID",
                    content = @Content)
    })
    @PutMapping("/superheroes/{id}")
    @LogExecutionTime
    public ResponseEntity<Superheroe> updateSuperheroe(@PathVariable("id") long id, @RequestBody Superheroe superheroe) {
        Optional<Superheroe> superheroeData = superheroeRepository.findById(id);

        if (superheroeData.isPresent()) {
            Superheroe _superheroe = superheroeData.get();
            _superheroe.setName(superheroe.getName());
            return new ResponseEntity<>(superheroeRepository.save(_superheroe), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a superhero given a specified id")
    @ApiResponse(responseCode = "204", description = "Either it was deleted or it didn't exist to begin with",
            content = @Content)
    @DeleteMapping("/superheroes/{id}")
    @LogExecutionTime
    public ResponseEntity<HttpStatus> deleteSuperheroe(@PathVariable("id") long id) {
        superheroeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}