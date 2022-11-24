package com.cursojava.proyecto.services;

import com.cursojava.proyecto.model.Pokeapi.PokeApiPokemon;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class PokemonServiceImpl implements PokemonService {

    @Override
    public PokeApiPokemon jsonApiExterna(String url) {
        class PokeInner {
            PokeApiPokemon getContent(){
                ObjectMapper objectMapper = new ObjectMapper();
                RestTemplate restTemplates = new RestTemplate();
                PokeApiPokemon pokemon;
                try {
                    String value=restTemplates.getForObject(url, String.class);
                    pokemon=objectMapper.readValue(value, PokeApiPokemon.class);
                } catch (StreamReadException e) {
                    throw new RuntimeException(e);
                } catch (DatabindException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return pokemon;
            }
        }

        return new PokeInner().getContent();
    }

}
