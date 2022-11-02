package com.cursojava.proyecto.controllers;

import com.cursojava.proyecto.model.ErrorDTO;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.ResponseDTO;
import com.cursojava.proyecto.services.PokemonService;
import com.cursojava.proyecto.utils.funcional.Batalla;
import com.cursojava.proyecto.utils.threads.CreatePokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

@RestController
@RequestMapping("pokemons")
public class PokemonController {
    private static final Logger LOGGER = Logger.getLogger(PokemonController.class.getName());
    @Autowired
    PokemonService pokemonService;

    @PostMapping(value = "createOne")
    public ResponseEntity<ResponseDTO> createOne(@RequestBody PokemonDTO pokemon) {
        ResponseDTO response = this.pokemonService.createPokemon(pokemon);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "getTeam", produces = "application/json")
    public Map<Integer, PokemonDTO> getTeam() {
        return this.pokemonService.getTeam();
    }

    @PostMapping(value = "createThreePokemons")
    public ResponseEntity<ResponseDTO> createMultiplePokemons(@RequestBody PokemonDTO[] pokemons) {

        ResponseDTO response = new ResponseDTO();
        try {
            CreatePokemon thread1 = new CreatePokemon(pokemons[0]);
            CreatePokemon thread2 = new CreatePokemon(pokemons[1]);
            CreatePokemon thread3 = new CreatePokemon(pokemons[2]);
            // Iniciamos threads
            thread1.start();
            thread2.start();
            thread3.start();

            // Esperamos a que terminen los threads

            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            response.setErrors(new ErrorDTO("ER001", "Error al esperar cierre de Threads"));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "batalla")
    public PokemonDTO batalla(@RequestBody PokemonDTO[] twopokemons){
        Random rd = new Random();
        Batalla<PokemonDTO, PokemonDTO, PokemonDTO> gimnacio = (x,y)-> {

            if(rd.nextBoolean()){
                return x;
            }else{
                return y;
            }};

        return gimnacio.simular(twopokemons[0],twopokemons[1]);
    }

}
