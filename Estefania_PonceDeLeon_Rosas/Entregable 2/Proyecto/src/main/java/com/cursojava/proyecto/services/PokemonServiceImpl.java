package com.cursojava.proyecto.services;

import com.cursojava.proyecto.model.ErrorDTO;
import com.cursojava.proyecto.model.Pokeapi.PokeApiPokemon;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.ResponseDTO;
import com.cursojava.proyecto.model.TipoDTO;
import com.cursojava.proyecto.repository.PokemonRepository;
import com.cursojava.proyecto.utils.Utils;
import com.cursojava.proyecto.utils.exceptions.InvalidFormatDatePersonalException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    MongoTemplate mongoTemplate;
    @Override
    public ResponseDTO createPokemon(PokemonDTO pokemon) {
        PokemonDTO nuevoPokemon=new PokemonDTO();
        Utils<String> utilsDate =new Utils<>();
        Utils<TipoDTO> utilsTipo =new Utils<>();
        nuevoPokemon.setNombre(pokemon.getNombre());
        nuevoPokemon.setSonido(pokemon.getSonido());
        nuevoPokemon.setTipo1(pokemon.getTipo1());
        if(utilsTipo.isNotNullValue(Optional.ofNullable(pokemon.getTipo2())))
                nuevoPokemon.setTipo2(pokemon.getTipo2());


        if(utilsDate.isNotNullValue(Optional.ofNullable(pokemon.getDate()))){
            try{
                DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate temp = LocalDate.parse(pokemon.getDate(), dateformatter);
                LocalDateTime lastTranning = temp.atStartOfDay();
                nuevoPokemon.setLastTraning(lastTranning);

            }catch (Exception e) {
                throw new InvalidFormatDatePersonalException("La fecha viene en formato incorrecto..."+e);
            }
        }

        this.pokemonRepository.save(nuevoPokemon);
        return new ResponseDTO();
    }

    @Override
    public Map<Integer, PokemonDTO> getDefaultTeam() {
        // Definicion de lista con la informacion de las cuentas existentes.
        Map<Integer, PokemonDTO> equipo = new HashMap<>();
        equipo.put(1, buildPokemon( "Ivysaur", "Saur", "Planta", "Veneno",
                buildPokemon( "Venusaur", "Venusaur", "Planta", "Veneno", null, null), null));
        equipo.put(2, buildPokemon( "Charizard", "Char","Fuego", "Volador", null, null));
        equipo.put(3, buildPokemon( "Raichu", "Raichu", "Electrico", null, null, null));
        equipo.put(4, buildPokemon( "Nidoqueen", "Nidoqueen", "Veneno", "Tierra", null, null));
        equipo.put(5, buildPokemon( "Cloyster", "Cloyster", "Agua", "Hielo", null, null));
        equipo.put(6, buildPokemon( "Alakazam", "Alakazam", "Psiquico", "Veneno", null, null));
        return equipo;
    }

    @Override
    public void deleteAll() {
        pokemonRepository.deleteAll();
    }

    @Override
    public List<PokemonDTO> getAllPokemons() {
        return pokemonRepository.findAll();
    }

    @Override
    public ResponseDTO getTotalByType(String type) {
        List<PokemonDTO> pokemons=pokemonRepository.findAll();
        Long totalTypes=pokemons.stream().filter(pokemonDTO -> pokemonDTO.getTipo1().getNombre().equals(type))
                .distinct()
                .collect(Collectors.toList()).stream().collect(Collectors.counting());
        return new ResponseDTO("Hay "+totalTypes + " pokemones de tipo "+type + " en total");
    }

    private PokemonDTO buildPokemon(String nombre, String sonido, String tipo1, String tipo2, PokemonDTO evolucion, LocalDateTime lastTraning) {
        PokemonDTO pokemon = new PokemonDTO();
        pokemon.setNombre(nombre);
        pokemon.setSonido(sonido);
        pokemon.setTipo1(new TipoDTO(tipo1));
        pokemon.setTipo2(new TipoDTO(tipo2));
        pokemon.setLastTraning(lastTraning);
        return pokemon;
    }

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
