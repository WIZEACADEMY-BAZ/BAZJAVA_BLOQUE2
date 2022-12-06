package com.cursojava.proyecto.controllers;

import com.cursojava.proyecto.model.TipoDTO;
import com.cursojava.proyecto.repository.TipoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TipoControllerTests {
    @MockBean
    private TipoRepository tipoRepository;
    @Autowired
    private TipoController tipoController;

    @Test
    public void init(){
        TipoDTO tipoDTO=new TipoDTO("Volador");
        tipoController.init(tipoDTO);
        verify(tipoRepository,times(1)).save(tipoDTO);
    }
    @Test
    public void getAll(){
        List<TipoDTO> tipoDTOList = new ArrayList<>();
        tipoDTOList.add(new TipoDTO("Normal"));
        tipoDTOList.add(new TipoDTO("Veneno"));

        when(tipoRepository.findAll()).thenReturn(tipoDTOList);

        Collection<TipoDTO> response=tipoController.getAll();
        // Verificar
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, response.size()),
                () -> Assertions.assertTrue(
                        response.stream()
                                .map(TipoDTO::getNombre)
                                .collect(Collectors.toList())
                                .containsAll(List.of("Veneno", "Normal")))
        );

    }

    @Test
    public void deleteAll(){
        tipoController.deleteAll();
        verify(tipoRepository,times(1)).deleteAll();
    }

}
