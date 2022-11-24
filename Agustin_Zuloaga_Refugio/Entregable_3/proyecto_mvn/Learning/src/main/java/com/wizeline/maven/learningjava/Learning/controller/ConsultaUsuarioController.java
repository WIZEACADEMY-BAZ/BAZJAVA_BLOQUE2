package com.wizeline.maven.learningjava.Learning.controller;

import com.wizeline.maven.learningjava.Learning.model.detalle.UserDescription;
import com.wizeline.maven.learningjava.Learning.service.ConsultaUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class ConsultaUsuarioController {

    @Autowired
    private ConsultaUsuarioService consultaUsuarioService;

    @GetMapping("/users-success")
    public List<UserDescription> consultaSuccess() {
        return consultaUsuarioService.consultaSuccess();
    }

    @GetMapping("/users-error")
    public List<UserDescription> consultaError() {
        return consultaUsuarioService.consultaError();
    }

    @GetMapping("/users-by-code")
    public List<UserDescription> filtrado() {
        return consultaUsuarioService.filtrado();
    }
}