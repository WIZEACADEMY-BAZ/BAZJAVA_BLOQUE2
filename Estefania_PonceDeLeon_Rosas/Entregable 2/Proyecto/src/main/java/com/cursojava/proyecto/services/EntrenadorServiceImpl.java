package com.cursojava.proyecto.services;

import com.cursojava.proyecto.model.EntrenadorDTO;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.repository.EntrenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class EntrenadorServiceImpl implements EntrenadorService{
    @Autowired
    EntrenadorRepository entrenadorRepository;

    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public void registrarDatos(EntrenadorDTO entrenador) {
        mongoTemplate.save(entrenador);
    }

    @Override
    public EntrenadorDTO consultarInformacion(EntrenadorDTO entrenador) {
        return entrenadorRepository.findEntrenadorDTOByNombreAndPassword(entrenador.getNombre(),entrenador.getPassword());
    }
    @Override
    public void registrarEquipo(EntrenadorDTO entrenador, PokemonDTO[] equipo) {
        Query query= Query.query(Criteria.where("nombre").is(entrenador.getNombre()).and("claveDeSeguridad").is(entrenador.getClaveDeSeguridad()));
        mongoTemplate.updateFirst(query, Update.update("equipo", equipo),EntrenadorDTO.class);
    }

    @Override
    public void retirarse(String nombre, String claveDeSeguridad) {
        entrenadorRepository.deleteEntrenadorDTOByNombreAndClaveDeSeguridad(nombre,claveDeSeguridad);
    }


}
