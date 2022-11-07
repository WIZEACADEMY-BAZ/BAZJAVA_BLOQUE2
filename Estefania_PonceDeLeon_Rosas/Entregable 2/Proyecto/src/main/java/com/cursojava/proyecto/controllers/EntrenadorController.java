package com.cursojava.proyecto.controllers;

import com.cursojava.proyecto.model.EntrenadorDTO;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.ResponseDTO;
import com.cursojava.proyecto.services.EntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("entrenador")
public class EntrenadorController {

    @Autowired
    private EntrenadorService entrenadorService;

    @PostMapping(value = "registro")
    ResponseDTO registrarDatos(@RequestBody EntrenadorDTO entrenador){
        this.entrenadorService.registrarDatos(entrenador);
        return new ResponseDTO();
    }

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

    @DeleteMapping(value = "retirarse")
    public ResponseDTO retirarse(@RequestParam String nombre, @RequestParam String claveDeSeguridad){
        this.entrenadorService.retirarse(nombre, claveDeSeguridad);
        return new ResponseDTO();
    }

    @GetMapping(value = "consultacifrada",produces = "application/json")
    Collection<EntrenadorDTO> consultaCifrada(){
        return this.entrenadorService.getEncryptedTrainers();
    }

}
