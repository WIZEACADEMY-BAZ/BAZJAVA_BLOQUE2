package com.cursojava.proyecto.controllers;

import com.cursojava.proyecto.client.EntrenadorJSONClient;
import com.cursojava.proyecto.model.EntrenadorDTO;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.Post;
import com.cursojava.proyecto.model.ResponseDTO;
import com.cursojava.proyecto.services.EntrenadorService;
import com.cursojava.proyecto.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.logging.Logger;

@RestController
@RequestMapping("entrenador")
public class EntrenadorController {

    private static final Logger LOGGER = Logger.getLogger(EntrenadorController.class.getName());
    @Autowired
    private EntrenadorService entrenadorService;

    @Autowired
    EntrenadorJSONClient entrenadorJSONClient;

    @PreAuthorize("hasRole('GUEST')")
    @PostMapping(value = "registro")
    ResponseDTO registrarDatos(@RequestBody EntrenadorDTO entrenador){
        this.entrenadorService.registrarDatos(entrenador);
        return new ResponseDTO();
    }
    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(value = "consultar",produces = "application/json")
    EntrenadorDTO consultarInformacion(@RequestParam String name, String password){
        EntrenadorDTO entrenador =new EntrenadorDTO();
        entrenador.setNombre(name);
        entrenador.setPassword(password);
        return this.entrenadorService.consultarInformacion(entrenador);
    }

    @PutMapping(value = "crearEquipo")
    public ResponseDTO crearEquipo(@RequestBody PokemonDTO[] pokemons,
        @RequestParam String nombre, @RequestParam String password){
        EntrenadorDTO entrenador =new EntrenadorDTO();
        entrenador.setNombre(nombre);
        entrenador.setPassword(password);
        entrenador=this.entrenadorService.consultarInformacion(entrenador);
        this.entrenadorService.registrarEquipo(entrenador,pokemons);
        return new ResponseDTO();
    }

    @PreAuthorize("hasRole('LEADER')")
    @DeleteMapping(value = "retirarse")
    public ResponseDTO retirarse(@RequestParam String nombre, @RequestParam String claveDeSeguridad){
        this.entrenadorService.retirarse(nombre, claveDeSeguridad);
        return new ResponseDTO();
    }

    @GetMapping(value = "consultacifrada",produces = "application/json")
    Collection<EntrenadorDTO> consultaCifrada(){
        return this.entrenadorService.getEncryptedTrainers();
    }

    //The usage of FeignClient for demo purposes
    @GetMapping("/getExternalUser/{userId}")
    public ResponseEntity<Post> getExternalUser(@PathVariable Long userId) {

        Post postTest = entrenadorJSONClient.getPostById(userId);
        LOGGER.info("Getting post userId..." +postTest.getUserId());
        LOGGER.info("Getting post body..." +postTest.getBody());
        LOGGER.info("Getting post title..." +postTest.getTitle());
        postTest.setUserId("External user "+Utils.randomAcountNumber());
        postTest.setBody("No info in accountBalance since it is an external user");
        postTest.setTitle("No info in title since it is an external user");
        LOGGER.info("Setting post userId..." +postTest.getUserId());
        LOGGER.info("Setting post body..." +postTest.getBody());
        LOGGER.info("Setting post title...."+postTest.getTitle());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(postTest, responseHeaders, HttpStatus.OK);
    }
}
