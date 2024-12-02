package edu.bowiestateuni.groupproj.foodpantry.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @RequestMapping(value = "/")
    public ResponseEntity<WelcomeData> index() {
        return ResponseEntity.ok(new WelcomeData("Welcome to our food Pantry!!!"));
    }

   public record WelcomeData(String message) {}
}
