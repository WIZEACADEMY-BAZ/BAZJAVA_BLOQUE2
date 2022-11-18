package com.cursojava.proyecto.repository;

import com.cursojava.proyecto.model.EntrenadorDTO;
import com.cursojava.proyecto.model.TipoDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.stream.Collectors;

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TipoRepositoryTest {

    @Autowired
    private TipoRepository tipoRepository;

    @BeforeAll
    private void init_test(){
        tipoRepository.save(new TipoDTO("Fuego"));
        tipoRepository.save(new TipoDTO("Agua"));
        tipoRepository.save(new TipoDTO("Tierra"));
        tipoRepository.save(new TipoDTO("Planta"));

    }

    @AfterAll
    public void limpiar() {
        tipoRepository.deleteAll();
    }

    @Test
    public void getAll(){
        List<TipoDTO> tipos = tipoRepository.findAll();
        // Verificar
        Assertions.assertAll(
                () -> Assertions.assertEquals(4, tipos.size()),
                () -> Assertions.assertTrue(
                        tipos.stream()
                                .map(TipoDTO::getNombre)
                                .collect(Collectors.toList())
                                .containsAll(List.of("Fuego", "Agua", "Tierra", "Planta")))
        );
    }
}
