package com.cursojava.proyecto.services;

import com.cursojava.proyecto.model.TipoDTO;
import com.cursojava.proyecto.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TipoServiceImpl implements TipoService{

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    TipoRepository tipoRepository;
    @Override
    public void initTypes() {
        mongoTemplate.save(new TipoDTO("Planta"));
        mongoTemplate.save(new TipoDTO("Veneno"));
        mongoTemplate.save(new TipoDTO("Fuego"));
        mongoTemplate.save(new TipoDTO("Volador"));
        mongoTemplate.save(new TipoDTO("Agua"));
        mongoTemplate.save(new TipoDTO("Bicho"));
        mongoTemplate.save(new TipoDTO("Normal"));
        mongoTemplate.save(new TipoDTO("Electrico"));
        mongoTemplate.save(new TipoDTO("Tierra"));
        mongoTemplate.save(new TipoDTO("Hada"));
        mongoTemplate.save(new TipoDTO("Lucha"));
        mongoTemplate.save(new TipoDTO("Psiquico"));
        mongoTemplate.save(new TipoDTO("Roca"));
        mongoTemplate.save(new TipoDTO("Acero"));
        mongoTemplate.save(new TipoDTO("Hielo"));
        mongoTemplate.save(new TipoDTO("Fantasma"));
        mongoTemplate.save(new TipoDTO("Dragon"));
    }

    @Override
    public void deleteAll(){
        tipoRepository.deleteAll();
    }

    @Override
    public Collection<TipoDTO> getAll() {
        return tipoRepository.findAll();
    }
}
