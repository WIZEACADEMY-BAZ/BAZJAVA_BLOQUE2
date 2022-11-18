package com.cursojava.proyecto.controllers;

import com.cursojava.proyecto.client.EntrenadorJSONClient;
import com.cursojava.proyecto.model.EntrenadorDTO;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.Post;
import com.cursojava.proyecto.model.ResponseDTO;
import com.cursojava.proyecto.repository.EntrenadorRepository;
import com.cursojava.proyecto.utils.Utils;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.logging.Logger;

@RestController
@RequestMapping("entrenador")
public class EntrenadorController {

    private static final Logger LOGGER = Logger.getLogger(EntrenadorController.class.getName());
    @Autowired
    EntrenadorJSONClient entrenadorJSONClient;

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @Autowired
    private KafkaTemplate<Object, Object> template;
    private final Bucket bucket;

    public EntrenadorController() {
        Refill refill = Refill.intervally(5, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(5, refill);
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @PostMapping(value = "registro")
    ResponseEntity<?> registrarDatos(@RequestBody EntrenadorDTO entrenador) {
        if (bucket.tryConsume(1)) {
            entrenador = this.entrenadorRepository.save(entrenador);
            if (entrenador == null) {
                return new ResponseEntity<>("Fallo al instanciar", HttpStatus.FAILED_DEPENDENCY);
            }
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Demasiadas peticiones", HttpStatus.TOO_MANY_REQUESTS);
    }

    @GetMapping(value = "consultar", produces = "application/json")
    EntrenadorDTO consultarInformacion(@RequestParam String name, String password) {
        EntrenadorDTO entrenador = this.entrenadorRepository.findFirstByNombreAndPassword(name, password);
        return entrenador;
    }

    @PutMapping(value = "crearEquipo")
    public ResponseEntity<?> crearEquipo(@RequestBody PokemonDTO[] pokemons, @RequestParam String nombre, @RequestParam String password) {

        EntrenadorDTO entrenador = consultarInformacion(nombre, password);

        if (entrenador == null) {
            return new ResponseEntity<>("El entrenador no existe en nuestra BD", HttpStatus.FORBIDDEN);
        } else {
            entrenador.setEquipo(pokemons);
            EntrenadorDTO result = this.entrenadorRepository.save(entrenador);
            if (result == null) {
                return new ResponseEntity<>("Error al actualizar los datos", HttpStatus.FAILED_DEPENDENCY);
            }
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping(value = "retirarse")
    public ResponseDTO retirarse(@RequestParam String nombre, @RequestParam String claveDeSeguridad) {
        this.entrenadorRepository.deleteEntrenadorDTOByNombreAndClaveDeSeguridad(nombre, claveDeSeguridad);
        return new ResponseDTO();
    }

    //The usage of FeignClient for demo purposes
    @GetMapping("/getExternalUser/{userId}")
    public ResponseEntity<Post> getExternalUser(@PathVariable Long userId) {

        Post postTest = entrenadorJSONClient.getPostById(userId);
        LOGGER.info("Getting post userId..." + postTest.getUserId());
        LOGGER.info("Getting post body..." + postTest.getBody());
        LOGGER.info("Getting post title..." + postTest.getTitle());
        postTest.setUserId("External user " + Utils.randomAcountNumber());
        postTest.setBody("No info in accountBalance since it is an external user");
        postTest.setTitle("No info in title since it is an external user");
        LOGGER.info("Setting post userId..." + postTest.getUserId());
        LOGGER.info("Setting post body..." + postTest.getBody());
        LOGGER.info("Setting post title...." + postTest.getTitle());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(postTest, responseHeaders, HttpStatus.OK);
    }

    //The usage of Kafka for demo purposes
    @PostMapping(path = "/send/{name}")
    public void sendUserAccount(@PathVariable String name) {
        EntrenadorDTO trainer = entrenadorRepository.findFirstByNombre(name);
        System.out.println("TRAINER: " + trainer.getNombre());
        this.template.send("useraccount-topic", trainer);
    }
}
