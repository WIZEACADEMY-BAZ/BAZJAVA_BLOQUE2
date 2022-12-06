package com.cursojava.proyecto.controllers;

import com.cursojava.proyecto.model.Pokeapi.PokeApiPokemon;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.TipoDTO;
import com.cursojava.proyecto.repository.PokemonRepository;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("pokemons")
public class PokemonController {
    private static final Logger LOGGER = Logger.getLogger(PokemonController.class.getName());
    @Autowired
    PokemonService pokemonService;

    @Autowired
    PokemonRepository pokemonRepository;

    static String movimiento="";

    @PostMapping(value = "createOne")
    public ResponseEntity<String> createOne(@RequestBody PokemonDTO pokemonDTO) {
        this.pokemonRepository.save(pokemonDTO);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping(value = "getAllPokemons")
    List<PokemonDTO> getAllPokemons() {
        return this.pokemonRepository.findAll();
    }

    @PostMapping(value = "entrenar3Pokemons")
    public ResponseEntity<?> entrenarTresPokemon(@RequestBody PokemonDTO[] pokemons) throws InterruptedException {
        EntrenarPokemons thread1 = new EntrenarPokemons(pokemons[0]);
        EntrenarPokemons thread2 = new EntrenarPokemons(pokemons[1]);
        EntrenarPokemons thread3 = new EntrenarPokemons(pokemons[2]);
        // Iniciamos threads
        thread1.start();
        thread2.start();
        thread3.start();

        // Esperamos a que terminen los threads

        thread1.join();
        thread2.join();
        thread3.join();

        LOGGER.info(thread1.getPokemon().getNombre() + " tiene el status de " + thread1.getPokemon().getStatus());
        LOGGER.info(thread2.getPokemon().getNombre() + " tiene el status de " + thread2.getPokemon().getStatus());
        LOGGER.info(thread3.getPokemon().getNombre() + " tiene el status de " + thread3.getPokemon().getStatus());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping(value = "batalla")
    public PokemonDTO batalla(@RequestBody PokemonDTO[] twopokemons, Boolean random) {
        Utils<TipoDTO> util = new Utils();
        Batalla<PokemonDTO, PokemonDTO> gimnacio = (x, y) -> {
            if (random) {
                if (util.isNotNullValue(Optional.ofNullable(x.getTipo2()))) {
                    movimiento=movimiento+x.getMovimientos().get(1).action();
                    x.setUltimoMovimiento(movimiento);
                    LOGGER.info(x.getNombre()+" attack with an "+movimiento);
                } else {
                    movimiento=movimiento+x.getMovimientos().get(0).action();
                    x.setUltimoMovimiento(movimiento);
                    LOGGER.info(x.getNombre()+" attack with an "+movimiento);
                }
                return x;
            } else {
                if (util.isNotNullValue(Optional.ofNullable(y.getTipo2()))) {
                    movimiento=movimiento+y.getMovimientos().get(1).action();
                    y.setUltimoMovimiento(movimiento);
                    LOGGER.info(y.getNombre()+" attack with an "+movimiento);
                } else {
                    movimiento=movimiento+y.getMovimientos().get(0).action();
                    y.setUltimoMovimiento(movimiento);
                    LOGGER.info(y.getNombre()+" attack with an "+movimiento);
                }
                return y;
            }
        };
        return gimnacio.simular(twopokemons[0], twopokemons[1]);
    }

    @DeleteMapping(value = "borrarTodos")
    public ResponseEntity<?> borrarTodos() {
        this.pokemonRepository.deleteAll();
        return new ResponseEntity<>("Sucess", HttpStatus.OK);
    }

    @GetMapping(value = "externa/api", produces = "application/json")
    public PokeApiPokemon json_apiexterna(@RequestParam(required = true) String url) {
        return this.pokemonService.jsonApiExterna(url);
    }

    @GetMapping(value = "total-by-type", produces = "application/json")
    public ResponseEntity<?> getTotalByType(@RequestParam String type) {
        List<PokemonDTO> pokemons = pokemonRepository.findAll();
        Long totalTypes = pokemons.stream().filter(pokemonDTO -> pokemonDTO.getTipo1().getNombre().equals(type))
                .distinct()
                .collect(Collectors.toList()).stream().collect(Collectors.counting());
        return new ResponseEntity<>(totalTypes + " en total de pokemons tipo " + type, HttpStatus.OK);
    }


}
