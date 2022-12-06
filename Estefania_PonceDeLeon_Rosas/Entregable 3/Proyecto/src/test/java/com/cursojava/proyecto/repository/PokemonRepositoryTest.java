package com.cursojava.proyecto.repository;

import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.TipoDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @BeforeAll
    private void init_test(){
        pokemonRepository.save(new PokemonDTO("Pikachu","Electrico"));
        pokemonRepository.save(new PokemonDTO("Blastoise","Agua"));
        pokemonRepository.save(new PokemonDTO("Diglet","Tierra"));
        pokemonRepository.save(new PokemonDTO("Leafeon","Planta"));
        pokemonRepository.save(new PokemonDTO("Eevee","Normal"));
        pokemonRepository.save(new PokemonDTO("Arbok","Veneno"));
        pokemonRepository.save(new PokemonDTO("Flareon","Fuego"));
        pokemonRepository.save(new PokemonDTO("Pidgeot","Volador"));
    }

    @AfterAll
    public void limpiar() {
        pokemonRepository.deleteAll();
    }

    @Test
    public void getAll(){
        List<PokemonDTO> tipos = pokemonRepository.findAll();
        // Verificar
        Assertions.assertAll(
                () -> Assertions.assertEquals(8, tipos.size()),
                () -> Assertions.assertTrue(
                        tipos.stream()
                                .map(PokemonDTO::getTipo1)
                                .collect(Collectors.toList())
                                .stream().map(TipoDTO::getNombre)
                                .collect(Collectors.toList())
                                .containsAll(List.of("Electrico", "Agua","Tierra","Planta","Normal","Volador","Fuego","Veneno"))));
    }
}
