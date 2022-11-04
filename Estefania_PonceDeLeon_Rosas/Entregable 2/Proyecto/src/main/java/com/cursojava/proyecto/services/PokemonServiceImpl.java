package com.cursojava.proyecto.services;

import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.ResponseDTO;
import com.cursojava.proyecto.model.TipoDTO;
import com.cursojava.proyecto.repository.PokemonRepository;
import com.cursojava.proyecto.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PokemonServiceImpl implements PokemonService {

    private static final Logger LOGGER = Logger.getLogger(PokemonServiceImpl.class.getName());

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    MongoTemplate mongoTemplate;
    @Override
    public ResponseDTO createPokemon(PokemonDTO pokemon) {
        PokemonDTO nuevoPokemon=new PokemonDTO();
        Utils<String> utils =new Utils<>();
        Utils<LocalDateTime> utilsDate =new Utils<>();

        nuevoPokemon.setNombre(pokemon.getNombre());
        nuevoPokemon.setSonido(pokemon.getSonido());
        nuevoPokemon.setTipo1(pokemon.getTipo1());

        if(utils.isNotNullValue(Optional.ofNullable(pokemon.getTipo2().getNombre()))){;
            nuevoPokemon.setTipo2(pokemon.getTipo2());
        }

        if(utilsDate.isNotNullValue(Optional.ofNullable(pokemon.getLastTraning()))){
            DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate temp = LocalDate.parse(pokemon.getDate(), dateformatter);
            LocalDateTime lastTranning = temp.atStartOfDay();
             nuevoPokemon.setLastTraning(lastTranning);
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
    public Collection<PokemonDTO> getAll() {
        return pokemonRepository.findAll();
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


}
