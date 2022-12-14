package com.cursojava.proyecto.controllers;

import com.cursojava.proyecto.model.Pokeapi.PokeApiPokemon;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.TipoDTO;
import com.cursojava.proyecto.repository.PokemonRepository;
import com.cursojava.proyecto.services.PokemonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PokemonControllerTest {

    private static final Logger LOGGER = Logger.getLogger(PokemonControllerTest.class.getName());
    @MockBean
    PokemonService pokemonService;
    @MockBean
    PokemonRepository pokemonRepository;

    @Autowired
    PokemonController pokemonController;

    private List<PokemonDTO> pokemonDTOS;

    @BeforeAll
    private void init(){
        pokemonDTOS = new ArrayList<>();
        pokemonDTOS.add(new PokemonDTO("Pikachu","Electrico"));
        pokemonDTOS.add(new PokemonDTO("Charmander","Fuego"));
        pokemonDTOS.add(new PokemonDTO("Blastoise","Agua"));
    }
    @Test
    public void createOne() {
        PokemonDTO pokemonDTO=new PokemonDTO("Pikachu","Electrico");
        pokemonController.createOne(pokemonDTO);
        verify(pokemonRepository,times(1)).save(pokemonDTO);
    }

    @Test
    public void getAllPokemons(){
        Mockito.when(pokemonRepository.findAll()).thenReturn(pokemonDTOS);
        List<PokemonDTO> pokemonDTOList=pokemonController.getAllPokemons();
        Assertions.assertEquals(3,pokemonDTOList.size());
    }

    @Test
    public void entrenarTresPokemon() throws InterruptedException {
        PokemonDTO[] pokemons =new PokemonDTO[3];
        pokemons[0]=pokemonDTOS.get(0);
        pokemons[1]=pokemonDTOS.get(1);
        pokemons[2]=pokemonDTOS.get(2);

        ResponseEntity<?> result=pokemonController.entrenarTresPokemon(pokemons);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Success",result.getBody()),
                () -> Assertions.assertEquals(HttpStatus.OK,result.getStatusCode())
        );

    }

    @Test
    public void batallaTrueTipo1() {
        PokemonDTO[] pokemons=new PokemonDTO[2];
        pokemons[0]=pokemonDTOS.get(0);
        pokemons[1]=pokemonDTOS.get(1);
        PokemonDTO result=pokemonController.batalla(pokemons,true);
        Assertions.assertEquals(pokemonDTOS.get(0).getNombre(), result.getNombre());
    }

    @Test
    public void batallaTrueTipo2() {
        PokemonDTO[] pokemons=new PokemonDTO[2];
        pokemons[0]=pokemonDTOS.get(0);
        pokemons[1]=pokemonDTOS.get(1);

        pokemons[0].setTipo2(new TipoDTO("Fuego"));
        pokemons[1].setTipo2(new TipoDTO("Normal"));

        PokemonDTO result=pokemonController.batalla(pokemons,true);

        Assertions.assertEquals(pokemonDTOS.get(0).getNombre(), result.getNombre());
    }

    @Test
    public void batallaFalseTipo1() {
        PokemonDTO[] pokemons=new PokemonDTO[2];
        pokemons[0]=pokemonDTOS.get(0);
        pokemons[1]=pokemonDTOS.get(1);
        PokemonDTO result=pokemonController.batalla(pokemons,false);
        System.out.println(result.getNombre());
        System.out.println(result.getUltimoMovimiento());
        Assertions.assertEquals(pokemonDTOS.get(1).getNombre(), result.getNombre());
    }

    @Test
    public void batallaFalseTipo2() {
        PokemonDTO[] pokemons=new PokemonDTO[2];
        pokemons[0]=pokemonDTOS.get(0);
        pokemons[1]=pokemonDTOS.get(1);

        pokemons[0].setTipo2(new TipoDTO("Fuego"));
        pokemons[1].setTipo2(new TipoDTO("Normal"));

        PokemonDTO result=pokemonController.batalla(pokemons,false);
        System.out.println(result.getNombre());
        System.out.println(result.getUltimoMovimiento());
        Assertions.assertEquals(pokemonDTOS.get(1).getNombre(), result.getNombre());
    }



    @Test
    public void borrarTodos(){
        pokemonController.borrarTodos();
        Mockito.verify(pokemonRepository,times(1)).deleteAll();
    }

    @Test
    public void json_apiexterna() {
        String url="https://pokeapi.co/api/v2/pokemon/ditto";

        PokeApiPokemon pokeApiPokemon = new PokeApiPokemon();
        pokeApiPokemon.setName("ditto");

        Mockito.when(pokemonService.jsonApiExterna(url)).thenReturn(pokeApiPokemon);

        PokeApiPokemon result= pokemonController.json_apiexterna(url);

        Assertions.assertEquals("ditto",result.getName());
    }

    @Test
    public void getTotalByType(){

        Mockito.when(pokemonRepository.findAll()).thenReturn(pokemonDTOS);
        ResponseEntity<?> response=pokemonController.getTotalByType("Fuego");
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());

    }


}
