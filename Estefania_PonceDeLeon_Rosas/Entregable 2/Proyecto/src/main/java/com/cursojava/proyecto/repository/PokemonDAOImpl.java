package com.cursojava.proyecto.repository;

import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.ResponseDTO;
import org.springframework.stereotype.Repository;
import com.cursojava.proyecto.utils.files.FileManipulation;
import java.util.logging.Logger;

@Repository
public class PokemonDAOImpl implements PokemonDAO{

    private static final Logger LOGGER = Logger.getLogger(PokemonDAOImpl.class.getName());


    @Override
    public ResponseDTO createPokemon(PokemonDTO pokemon) {
        ResponseDTO response= FileManipulation.createFile();
        LOGGER.info("Inicia procesamiento en capa de acceso de datos");
        LOGGER.info("Inicia proceso de alta de usuaro en BD...");

        if(!response.getErrors().getErrorCode().isEmpty()){
            return response;
        }

        String msgOptionals= " su(s) tipo(s) es(son): "+pokemon.getTipo1();
        if(pokemon.getTipo2().isPresent()){
            msgOptionals=msgOptionals+" y "+ pokemon.getTipo2().get();
        }

        if(pokemon.getLastTraning().isPresent()){
            msgOptionals=msgOptionals+", su último entrenamiento fue: "+pokemon.getLastTraning().get();
        }
        response=FileManipulation.writeFile(pokemon.getNombre(), pokemon.getSonido() , msgOptionals );

        if(!response.getErrors().getErrorCode().isEmpty()){
            LOGGER.info("Alta exitosa");
        }
        return response;
    }

}
