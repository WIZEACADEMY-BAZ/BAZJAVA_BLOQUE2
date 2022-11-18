package com.cursojava.proyecto.controllers;

import com.cursojava.proyecto.model.TipoDTO;
import com.cursojava.proyecto.repository.TipoRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("pokemons/types")
@Tag(name = "Tipo", description = "Administra los tipos de pokemons")
public class TipoController {

    @Autowired
    private TipoRepository tipoRepository;

    @PostMapping(value = "init")
    public ResponseEntity<?> init(@RequestBody TipoDTO tipo){
        tipoRepository.save(tipo);
        return new ResponseEntity<>("Initialized", HttpStatus.OK);
    }

    @GetMapping(value = "getAllTypes")
    public Collection<TipoDTO> getAll(){
        return tipoRepository.findAll();
    }

    @DeleteMapping(value = "deleteAllTypes")
    public ResponseEntity<?> deleteAll(){
        tipoRepository.deleteAll();
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
