package com.lambdaschool.zoos.controllers;

import com.lambdaschool.zoos.logging.Loggable;
import com.lambdaschool.zoos.services.AnimalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Loggable
@RestController
@RequestMapping(value = "/animals")
public class AnimalController {

    private static final Logger logger = LoggerFactory.getLogger(AnimalController.class);

    @Autowired
    AnimalService animalService;

    @GetMapping(value = "/count",
                produces = {"application/json"})
    public ResponseEntity<?> getAnimalCounts(HttpServletRequest request) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        return new ResponseEntity<>(animalService.getCountAnimalZoos(), HttpStatus.OK);
    }
}
