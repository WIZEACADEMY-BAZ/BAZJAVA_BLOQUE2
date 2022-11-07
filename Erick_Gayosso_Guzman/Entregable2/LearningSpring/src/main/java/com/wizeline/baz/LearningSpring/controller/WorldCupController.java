package com.wizeline.baz.LearningSpring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class WorldCupController {

    @GetMapping("/getListTeams")
    public ResponseEntity<List> getListTeam(){
        return null;
    }
    @PostMapping("/addTeam")
    public ResponseEntity<String> changeTeam(){
        return null;
    }
    @DeleteMapping("/deleteAccounts")
    public ResponseEntity<String> deleteTeam(){
        return null;
    }
    @PutMapping("/addTeam")
    public ResponseEntity<String> addTeam(){
        return null;
    }

}
