package com.cursojava.proyecto.controllers;

import com.cursojava.proyecto.model.ErrorDTO;
import com.cursojava.proyecto.model.Pokeapi.PokeApiPokemon;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.ResponseDTO;
import com.cursojava.proyecto.services.PokemonService;
import com.cursojava.proyecto.utils.Utils;
import com.cursojava.proyecto.utils.funcional.Batalla;
import com.cursojava.proyecto.utils.threads.EntrenarPokemons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @GetMapping(value = "getAllPokemons")
    List<PokemonDTO> getAllPokemons(){
        return this.pokemonService.getAllPokemons();
    }
    @PostMapping(value = "entrenar3Pokemons")
    public ResponseEntity<ResponseDTO> entrenarTresPokemon(@RequestBody PokemonDTO[] pokemons) {

        ResponseDTO response = new ResponseDTO();
        EntrenarPokemons thread1;
        EntrenarPokemons thread2;
        EntrenarPokemons thread3;
        try {
            thread1 = new EntrenarPokemons(pokemons[0]);
            thread2 = new EntrenarPokemons(pokemons[1]);
            thread3 = new EntrenarPokemons(pokemons[2]);
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


        LOGGER.info(thread1.getPokemon().getNombre() + " tiene el status de "+thread1.getPokemon().getStatus());
        LOGGER.info(thread2.getPokemon().getNombre() + " tiene el status de "+thread2.getPokemon().getStatus());
        LOGGER.info(thread3.getPokemon().getNombre() + " tiene el status de "+thread3.getPokemon().getStatus());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "batalla")
    public PokemonDTO batalla(@RequestBody PokemonDTO[] twopokemons) {
        Random random = new Random();
        Utils<String> util = new Utils();
        Batalla<PokemonDTO, PokemonDTO> gimnacio = (x, y) -> {
            if (random.nextBoolean()) {
                if (random.nextBoolean() && util.isNotNullValue(Optional.ofNullable(x.getTipo2().getNombre()))) {
                    LOGGER.info(x.getMovimientos()[1].accion(x.getNombre()));
                } else {
                    LOGGER.info(x.getMovimientos()[0].accion(x.getNombre()));
                }
                return x;
            } else {
                if (random.nextBoolean() && util.isNotNullValue(Optional.ofNullable(y.getTipo2().getNombre()))) {
                    LOGGER.info(y.getMovimientos()[1].accion(y.getNombre()));
                } else {
                    LOGGER.info(y.getMovimientos()[0].accion(y.getNombre()));
                }
                return y;
            }
        };
        return gimnacio.simular(twopokemons[0], twopokemons[1]);
    }

    @DeleteMapping(value = "borrarTodos")
    public ResponseDTO borrarTodos(){
        this.pokemonService.deleteAll();
        return new ResponseDTO();
    }

    @GetMapping(value = "externa/api", produces = "application/json")
    public PokeApiPokemon json_apiexterna(@RequestParam(required = true) String url) {
        return this.pokemonService.jsonApiExterna(url);
    }

    @GetMapping(value = "total-by-type", produces = "application/json")
    public ResponseDTO getTotalByType(@RequestParam String type){
        return this.pokemonService.getTotalByType(type);
    }


}
