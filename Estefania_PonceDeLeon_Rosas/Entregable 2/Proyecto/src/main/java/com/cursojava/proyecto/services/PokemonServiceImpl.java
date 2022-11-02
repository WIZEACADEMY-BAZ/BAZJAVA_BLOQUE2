package com.cursojava.proyecto.services;

import com.cursojava.proyecto.Enum.Tipo;
import com.cursojava.proyecto.model.ErrorDTO;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.ResponseDTO;
import com.cursojava.proyecto.repository.PokemonDAO;
import com.cursojava.proyecto.repository.PokemonDAOImpl;
import com.cursojava.proyecto.utils.Utils;
import com.cursojava.proyecto.utils.exceptions.InvalidFormatDatePersonalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PokemonServiceImpl implements PokemonService {

    private static final Logger LOGGER = Logger.getLogger(PokemonServiceImpl.class.getName());
    private static final String STATUS_OK = "OK000";

    @Autowired
    PokemonDAO pokemonDao;
    @Override
    public ResponseDTO createPokemon(String nombre, String sonido, Tipo tipo1, Tipo tipo2, PokemonDTO evolucion, String lastTranning) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String resp;
        if (Utils.validateNullValue(nombre) && Utils.validateNullValue(tipo1.toString()) && Utils.isSoundValid(sonido)) {
            LocalDate tranning = null;
            LocalDateTime tranningDateTime = null;
            if (Utils.validateNullValue(lastTranning)){
                if (Utils.isDateTimeValid(lastTranning)) {
                    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    tranning = LocalDate.parse(lastTranning, dateformatter);
                    tranningDateTime = tranning.atStartOfDay();
                }else {
                    String excepcion = "El formato de fecha es incorrecto";
                    LOGGER.severe(excepcion);
                    throw new InvalidFormatDatePersonalException(excepcion);
                }
            }
            resp = pokemonDao.createPokemon(nombre, sonido, tipo1, tipo2, evolucion, tranningDateTime);
            response.setCode(STATUS_OK);
            response.setStatus(resp);
        } else {
            response.setCode(STATUS_OK);
            response.setStatus(response.getStatus());
            response.setErrors(new ErrorDTO("ER001", "Error al crear el pokemon"));
        }
        return response;
    }

    @Override
    public PokemonDTO getPokemonDetail(String nombre, String sonido, String tipo1, String tipo2, LocalDateTime lastTraning) {
        return buildPokemon( nombre, sonido, Tipo.valueOf(tipo1), Tipo.valueOf(tipo2), null, lastTraning);
    }

    @Override
    public Map<Integer, PokemonDTO> getTeam() {
        // Definicion de lista con la informacion de las cuentas existentes.
        Map<Integer, PokemonDTO> equipo = new HashMap<>();
        equipo.put(1, buildPokemon( "Ivysaur", "Saur", Tipo.Planta, Tipo.Veneno,
                buildPokemon( "Venusaur", "Venusaur", Tipo.Planta, Tipo.Veneno, null, null), null));
        equipo.put(2, buildPokemon( "Charizard", "Char", Tipo.Fuego, Tipo.Volador, null, null));
        equipo.put(3, buildPokemon( "Raichu", "Raichu", Tipo.Electrico, null, null, null));
        equipo.put(4, buildPokemon( "Nidoqueen", "Nidoqueen", Tipo.Veneno, Tipo.Tierra, null, null));
        equipo.put(5, buildPokemon( "Cloyster", "Cloyster", Tipo.Agua, Tipo.Hielo, null, null));
        equipo.put(6, buildPokemon( "Alakazam", "Alakazam", Tipo.Psiquico, Tipo.Veneno, null, null));
        return equipo;
    }

    public PokemonDTO buildPokemon(String nombre, String sonido, Tipo tipo1, Tipo tipo2, PokemonDTO evolucion, LocalDateTime lastTraning) {
        PokemonDTO pokemon = new PokemonDTO();
        pokemon.setNombre(nombre);
        pokemon.setSonido(sonido);
        pokemon.setTipo1(tipo1);
        pokemon.setTipo2(Optional.ofNullable(tipo2));
        pokemon.setEvolucion(Optional.ofNullable(evolucion));
        pokemon.setLastTraning(Optional.ofNullable(lastTraning));
        return pokemon;
    }


}
