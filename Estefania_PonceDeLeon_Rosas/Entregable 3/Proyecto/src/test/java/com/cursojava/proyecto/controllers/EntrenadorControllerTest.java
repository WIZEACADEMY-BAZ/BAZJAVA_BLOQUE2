package com.cursojava.proyecto.controllers;

import com.cursojava.proyecto.client.EntrenadorJSONClient;
import com.cursojava.proyecto.model.EntrenadorDTO;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.Post;
import com.cursojava.proyecto.repository.EntrenadorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.logging.Logger;

@SpringBootTest
public class EntrenadorControllerTest {

    private static final Logger LOGGER = Logger.getLogger(EntrenadorControllerTest.class.getName());
    @MockBean
    private EntrenadorRepository entrenadorRepository;
    @MockBean
    private EntrenadorJSONClient entrenadorJSONClient;
    @MockBean
    private KafkaTemplate<Object, Object> template;
    @Autowired
    private EntrenadorController entrenadorController;
    private static final String nombreEntrenador="Fred";
    private static final String passwordEntrenador="password";
    private static final String claveEntrenador="090";

    @Test
    public void registrarDatos() {
        EntrenadorDTO entrenadorDTO=new EntrenadorDTO(nombreEntrenador, passwordEntrenador, claveEntrenador);

        Mockito.when(entrenadorRepository.save(entrenadorDTO)).thenReturn(entrenadorDTO);

        ResponseEntity<?> result=this.entrenadorController.registrarDatos(entrenadorDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Success",result.getBody()),
                () -> Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode())
        );

    }

    @Test
    public void consultarInformacion() {
        EntrenadorDTO entrenadorDTO=new EntrenadorDTO(nombreEntrenador,passwordEntrenador);
        Mockito.when(entrenadorRepository.findFirstByNombreAndPassword(nombreEntrenador,passwordEntrenador)).thenReturn(entrenadorDTO);
        EntrenadorDTO result = entrenadorController.consultarInformacion(nombreEntrenador,passwordEntrenador);

        Assertions.assertAll(
                () -> Assertions.assertEquals(nombreEntrenador,result.getNombre()),
                () -> Assertions.assertEquals(passwordEntrenador,result.getPassword())
        );
    }

    @Test
    public void crearEquipo() {

        PokemonDTO[] equipo=new PokemonDTO[3];

        equipo[0]=new PokemonDTO("Pikachu", "Electrico");
        equipo[1]=new PokemonDTO("Charmelon", "Fuego");
        equipo[2]=new PokemonDTO("Blastoise", "Agua");

        EntrenadorDTO entrenadorDTO=new EntrenadorDTO(nombreEntrenador,passwordEntrenador);

        Mockito.when(entrenadorController.consultarInformacion(nombreEntrenador,passwordEntrenador)).thenReturn(entrenadorDTO);

        entrenadorDTO.setEquipo(equipo);

        Mockito.when(entrenadorRepository.save(entrenadorDTO)).thenReturn(entrenadorDTO);

        ResponseEntity<?> result=entrenadorController.crearEquipo(equipo,nombreEntrenador,passwordEntrenador);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Success",result.getBody()),
                () -> Assertions.assertEquals(HttpStatus.OK, result.getStatusCode())
        );

    }

    @Test
    public void retirarse() {
        entrenadorController.retirarse(nombreEntrenador,passwordEntrenador);
        Mockito.verify(entrenadorRepository,Mockito.times(1)).deleteEntrenadorDTOByNombreAndClaveDeSeguridad(nombreEntrenador,passwordEntrenador);
    }

    //The usage of FeignClient for demo purposes
    @Test
    public void getExternalUser() {

        Long id= Integer.toUnsignedLong(1);
        Post postTest=new Post(id);
        Mockito.when(entrenadorJSONClient.getPostById(id)).thenReturn(postTest);
        ResponseEntity<Post> result = entrenadorController.getExternalUser(id);
        Assertions.assertEquals(id,result.getBody().getId());

    }

    //The usage of Kafka for demo purposes
    @Test
    public void sendUserAccount() {

        EntrenadorDTO entrenadorDTO=new EntrenadorDTO(nombreEntrenador);
        Mockito.when(entrenadorRepository.findFirstByNombre(nombreEntrenador)).thenReturn(entrenadorDTO);
        entrenadorController.sendUserAccount(nombreEntrenador);
        Mockito.verify(template,Mockito.times(1)).send("useraccount-topic",entrenadorDTO);


    }
}
