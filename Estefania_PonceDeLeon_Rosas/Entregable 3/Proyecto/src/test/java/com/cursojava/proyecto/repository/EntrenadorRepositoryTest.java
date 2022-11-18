package com.cursojava.proyecto.repository;

import com.cursojava.proyecto.model.EntrenadorDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EntrenadorRepositoryTest {

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @BeforeAll
    private void init_test(){
        entrenadorRepository.save(new EntrenadorDTO("Fred", "password", "090"));
        entrenadorRepository.save(new EntrenadorDTO("Antonio", "password2", "091"));
        entrenadorRepository.save(new EntrenadorDTO("Gerardo", "password3", "092"));
        entrenadorRepository.save(new EntrenadorDTO("Manuel", "password4", "093"));
    }


    @AfterAll
    public void clean() {
        entrenadorRepository.deleteAll();
    }

    @Test
    public void findFirstByNombreAndPassword(){
        EntrenadorDTO result = entrenadorRepository.findFirstByNombreAndPassword("Fred", "password");
        // Verificar
        Assertions.assertAll(
                () -> Assertions.assertEquals("Fred", result.getNombre()),
                () -> Assertions.assertEquals("password", result.getPassword()),
                () -> Assertions.assertEquals("090", result.getClaveDeSeguridad())
        );
    }

    @Test
    public void findFirstByNombre (){
        EntrenadorDTO result = entrenadorRepository.findFirstByNombre("Antonio");
        //Verificar
        Assertions.assertAll(
                () -> Assertions.assertEquals("Antonio", result.getNombre()),
                () -> Assertions.assertEquals("password2", result.getPassword()),
                () -> Assertions.assertEquals("091", result.getClaveDeSeguridad())
        );
    }

    @Test
    public void deleteEntrenadorDTOByNombreAndClaveDeSeguridad(){
        entrenadorRepository.deleteEntrenadorDTOByNombreAndClaveDeSeguridad("Gerardo","092");
        List<EntrenadorDTO> result = entrenadorRepository.findAll();
        Assertions.assertEquals(3,result.size());
    }


}
