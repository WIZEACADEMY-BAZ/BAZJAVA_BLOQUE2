package com.Wizeline.maven.learningjavamaven.controller;

import com.Wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.Wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.Wizeline.maven.learningjavamaven.model.UserDTO;
import com.Wizeline.maven.learningjavamaven.model.Usuario;
import com.Wizeline.maven.learningjavamaven.repository.UsersDAO;
import com.Wizeline.maven.learningjavamaven.service.BankAccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BankingAccountControllerTest {
    /* Se generan logs por cada prueba*/
    private static final Logger LOGGER = LoggerFactory.getLogger(BankingAccountControllerTest.class);
    /* Uso de Mockito en cada prueba*/
    @MockBean
    private BankAccountServiceImpl userService;

    @MockBean
    private UsersDAO usersDAO;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void DadoElServicioCrearUsuario_CuandoTieneValoresAceptables_RegresaStatus200() throws Exception {
        /* Pruebas para Happy Path */
        LOGGER.info("Se crea el cuerpo que viajará por la petición de prueba, con nombreDeUsuario y password");
        UserDTO usuarioDTO2 = new UserDTO("k10", "1234567890");

        LOGGER.info("Se crea un objeto que regresa UsuarioEntidad");
        Usuario usuarioEntidad = new Usuario();

        LOGGER.info("Se copian los valores que contiene el cuerpo original al cuerpo esperado");
        BeanUtils.copyProperties(usuarioDTO2, usuarioEntidad);

        LOGGER.info("Se mockea la capa dao para cuando se mande a llamar este con un cuerpo de cierto tipo, responda con la respuesta esperada");
        Mockito.when(usersDAO.crearUsuario(Mockito.any(Usuario.class))).thenReturn(usuarioEntidad);

        LOGGER.info("Se arma la respuesta esperada del servicio cargandole todos los parametros esperados");
        ResponseDTO dto = new ResponseDTO();
        dto.setCode("OK000");
        dto.setStatus("Usuario creado");
        dto.setResultado(usersDAO.crearUsuario(usuarioEntidad));

        LOGGER.info("Se mockea el servicio para devolver la respuesta esperada, en este caso un exito");
        Mockito.when(userService.getAccounts()).thenReturn((List<BankAccountDTO>) ResponseEntity.ok(dto));

        LOGGER.info("Se arma la peticion como si fuera un postman con la ruta y el cuerpo de entrada");
        MvcResult result = mockMvc.perform(post("/api/getAccounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO2))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK000"))
                .andExpect(jsonPath("$.status").value("Usuario creado"))
                .andExpect(content().json(objectMapper.writeValueAsString(dto)))
                .andReturn();

        LOGGER.info("MvcResult cuerpo de respuesta: " + result.getResponse().getContentAsString());
    }
}
