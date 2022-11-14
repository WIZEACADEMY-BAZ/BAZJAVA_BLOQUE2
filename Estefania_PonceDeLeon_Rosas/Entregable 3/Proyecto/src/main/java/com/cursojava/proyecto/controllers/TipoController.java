package com.cursojava.proyecto.controllers;

import com.cursojava.proyecto.model.ResponseDTO;
import com.cursojava.proyecto.model.TipoDTO;
import com.cursojava.proyecto.services.TipoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("pokemons/types")
@Tag(name = "Tipo", description = "Administra los tipos de pokemons")
public class TipoController {

    @Autowired
    private TipoService tipoService;

    @PostMapping(value = "init")
    public ResponseDTO init(){
        tipoService.initTypes();
        return new ResponseDTO();
    }

    @GetMapping(value = "getAllTypes")
    public Collection<TipoDTO> getAll(){
        return tipoService.getAll();
    }

    @DeleteMapping(value = "deleteAllTypes")
    public ResponseDTO deleteAll(){
        tipoService.deleteAll();
        return new ResponseDTO();
    }
}
