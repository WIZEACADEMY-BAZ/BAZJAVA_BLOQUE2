package com.cursojava.proyecto.utils.threads;

import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.ResponseDTO;
import com.cursojava.proyecto.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import com.cursojava.proyecto.utils.files.FileManipulation;

import java.util.Optional;
import java.util.logging.Logger;

public class CreatePokemon extends Thread{

    private static final Logger LOGGER = Logger.getLogger(CreatePokemon.class.getName());
    private PokemonDTO pokemon;
    private ResponseDTO response;

    public CreatePokemon( PokemonDTO pokemon){
        this.pokemon=pokemon;
        this.response=new ResponseDTO();
    }

    public PokemonDTO getPokemon() {return pokemon;}

    public void setPokemon(PokemonDTO pokemon) { this.pokemon = pokemon;}

    public ResponseDTO getResponse() { return response; }

    public void setResponse(ResponseDTO response) { this.response = response; }

    @Override
    public void run(){
        response= FileManipulation.createFile();
        if(response.getErrors().getErrorCode().isEmpty()){
            String msgOptionals= " su(s) tipo(s) es(son): "+pokemon.getTipo1();
            if(pokemon.getTipo2()!=null){
                msgOptionals=msgOptionals+" y "+ pokemon.getTipo2().get();
            }
            if(pokemon.getLastTraning()!=null){
                msgOptionals=msgOptionals+", su último entrenamiento fue: "+pokemon.getLastTraning().get();
            }
            response=FileManipulation.writeFile(pokemon.getNombre(), pokemon.getSonido() , msgOptionals );
            if(!response.getErrors().getErrorCode().isEmpty()){
                LOGGER.info("Alta exitosa");
            }
        }
    }
}
