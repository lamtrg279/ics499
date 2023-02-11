package ics499.GalaxyGenerator.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ics499.GalaxyGenerator.model.StarSystem;
import ics499.GalaxyGenerator.repository.StarSystemRepository;

@RestController
public class StarSystemController {

  @Autowired
  private StarSystemRepository repo;

  @GetMapping("/starsystems")
  public List<StarSystem> getAllStarSystems() {
    return repo.findAll();
  }

  @GetMapping("/starsystem/{id}")
  public ResponseEntity<StarSystem> getStarSystemById(@PathVariable(value = "id") Integer starSystemId) {
    try {
      StarSystem newStarSystem = repo.findById(starSystemId).get();
      return new ResponseEntity<StarSystem>(newStarSystem, HttpStatus.OK);
    }
    catch (NoSuchElementException e)  {
      return new ResponseEntity<StarSystem>(HttpStatus.NOT_FOUND);
    }
  }
  
}