package com.Wizeline.maven.learningjavamaven.service;

import com.Wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.Wizeline.maven.learningjavamaven.model.UserDTO;
import com.Wizeline.maven.learningjavamaven.model.Usuario;
import com.Wizeline.maven.learningjavamaven.repository.UsersDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceImplTest {
    /* Pruebas de integracion - UserServiceTestIntegracion - HappyPaths */
    /* Generación de logs por prueba - UserServiceTestIntegracion: Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplTest.class);
    @MockBean
    private UsersDAO usersDAO;

    @Autowired
    private UserServiceImpl userService;

    @Test
    @DisplayName("CrearUsuarioTest")
    public void DadoElServicioCreateUser_CuandoEntraUnCuerpoBueno_SeCreaElUsuario(){
        Usuario usuarioEntidad = new Usuario("usuarioSimulado","sontraseniaSimulada");
        UserDTO userDTO =  new UserDTO ();

        BeanUtils.copyProperties(usuarioEntidad, userDTO);

        Mockito.when(usersDAO.crearUsuario(Mockito.any(Usuario.class))).thenReturn(usuarioEntidad);

        ResponseEntity<ResponseDTO> respuestaServicio = userService.crearUsuarioService(userDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals("OK000", respuestaServicio.getBody().getCode())
        );

        LOGGER.info("Resultado: "+respuestaServicio.getBody().getCode());
    }

    @Test
    @DisplayName("NoCreaUsuarioTest")
    public void DadoElServicioCreateUser_CuandoEntraUnCuerpoMalo_NoSeCreaElUsuario(){
        Usuario usuarioEntidad = new Usuario("usuarioSimulado","sontraseniaSimulada");
        UserDTO usuarioDTO =  new UserDTO();

        BeanUtils.copyProperties(usuarioEntidad, usuarioDTO);

        Mockito.when(usersDAO.crearUsuario(Mockito.any(Usuario.class))).thenReturn(null);

        ResponseEntity<ResponseDTO> respuestaServicio = userService.crearUsuarioService(usuarioDTO);

        Assertions.assertAll(
                () -> Assertions.assertEquals("ER001", respuestaServicio.getBody().getCode())
        );

        LOGGER.info("Resultado: "+respuestaServicio.getBody().getCode());
    }

    @Test
    @DisplayName("LoginTest")
    public void loginTest (){
        Usuario usuarioEntidad = new Usuario("usuarioSimulado","sontraseniaSimulada");
        Mockito.when(usersDAO.login("usuarioSimulado", "sontraseniaSimulada")).thenReturn(usuarioEntidad);

        ResponseEntity<ResponseDTO> respuestaServicio = userService.loginService("usuarioSimulado", "sontraseniaSimulada");

        Assertions.assertAll(
                () -> Assertions.assertEquals("OK000", respuestaServicio.getBody().getCode())
        );

        LOGGER.info("Resultado: "+respuestaServicio.getBody().getCode());
    }

    @Test
    @DisplayName("LoginTestFallo")
    public void loginTestFallo (){
        Mockito.when(usersDAO.login("usuarioSimulado", "sontraseniaSimulada")).thenReturn(null);

        ResponseEntity<ResponseDTO> respuestaServicio = userService.loginService("usuarioSimulado", "sontraseniaSimulada");

        Assertions.assertAll(
                () -> Assertions.assertEquals("ER001", respuestaServicio.getBody().getCode())
        );

        LOGGER.info("Resultado: "+respuestaServicio.getBody().getCode());
    }

    @Test
    @DisplayName("CambiarContraseniaTest")
    public void cambiarContrasenia (){
        String idUsuario = "1882886105";
        String nuevaContrasenia = "xd";

        Mockito.when(usersDAO.cambiarContrasenia("1882886105", "xd")).thenReturn(1L);

        ResponseEntity<ResponseDTO> respuestaServicio = userService.cambiarContrasenia(idUsuario, nuevaContrasenia);

        Assertions.assertAll(
                () -> Assertions.assertEquals("OK000", respuestaServicio.getBody().getCode())
        );

        LOGGER.info("Resultado: "+respuestaServicio.getBody().getCode());
    }

    @Test
    @DisplayName("Edge case - UserServiceIntegracion - CambiarContraseniaFallaTest")
    public void CambiarContraseniaFallaTest(){
        String idUsuario = "1882886105";
        String nuevaContrasenia = "xd";

        Mockito.when(usersDAO.cambiarContrasenia("1882886105", "xd")).thenReturn(0L);

        ResponseEntity<ResponseDTO> respuestaServicio = userService.cambiarContrasenia(idUsuario, nuevaContrasenia);

        Assertions.assertAll(
                () -> Assertions.assertEquals("ER001", respuestaServicio.getBody().getCode())
        );

        LOGGER.info("Resultado: "+respuestaServicio.getBody().getCode());
    }

    @Test
    @DisplayName("ObtenerUsuariosTest")
    public void ObtenerUsuarios (){
        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(new Usuario("usuario1", "contrasenia2"));
        listaUsuarios.add(new Usuario("usuario1", "contrasenia2"));

        Mockito.when(usersDAO.obtenerUsuarios()).thenReturn(listaUsuarios);

        ResponseEntity<ResponseDTO> respuestaServicio = userService.obtenerUsuarios();

        Assertions.assertAll(
                () -> Assertions.assertEquals("OK000", respuestaServicio.getBody().getCode())
        );

        LOGGER.info("Resultado: "+respuestaServicio.getBody().getCode());
    }

    @Test
    @DisplayName("Edge case - UserServiceIntegracion - ObtenerUsuariosFallaTest")
    public void ObtenerUsuariosFallaTest(){
        List<Usuario> listaUsuarios = new ArrayList<>();

        Mockito.when(usersDAO.obtenerUsuarios()).thenReturn(listaUsuarios);

        ResponseEntity<ResponseDTO> respuestaServicio = userService.obtenerUsuarios();

        Assertions.assertAll(
                () -> Assertions.assertEquals("ER001", respuestaServicio.getBody().getCode())
        );

        LOGGER.info("Resultado: "+respuestaServicio.getBody().getCode());
    }

    @Test
    @DisplayName("obtenerUsuarioPorIdTest")
    public void obtenerUsuarioPoId (){
        Usuario usuarioEntidad = new Usuario("usuario1", "contrasenia2");

        Mockito.when(usersDAO.obtenerUsuario(Mockito.anyString())).thenReturn(usuarioEntidad);

        String idUsuario = ""+usuarioEntidad.getId();

        ResponseEntity<ResponseDTO> respuestaServicio = userService.obtenerUsuarioPoId(idUsuario);

        Assertions.assertAll(
                () -> Assertions.assertEquals("OK000", respuestaServicio.getBody().getCode())
        );

        LOGGER.info("Resultado: "+respuestaServicio.getBody().getCode());
    }

    @Test
    @DisplayName("Edge case - UserServiceIntegracion - obtenerUsuarioPorIdFallaTest")
    public void obtenerUsuarioPorIdFallaTest(){
        Usuario usuarioEntidad = new Usuario("usuario1", "contrasenia2");

        Mockito.when(usersDAO.obtenerUsuario(Mockito.anyString())).thenReturn(null);

        String idUsuario = ""+usuarioEntidad.getId();

        ResponseEntity<ResponseDTO> respuestaServicio = userService.obtenerUsuarioPoId(idUsuario);

        Assertions.assertAll(
                () -> Assertions.assertEquals("ER001", respuestaServicio.getBody().getCode())
        );

        LOGGER.info("Resultado: "+respuestaServicio.getBody().getCode());
    }

    @Test
    @DisplayName("borrarUsuarioPorIdTest")
    public void borrarUsuarioId (){
        String idUsuarioABorrar = "123";

        Mockito.when(usersDAO.borrarUsuario(Mockito.anyString())).thenReturn(1L);

        ResponseEntity<ResponseDTO> respuestaServicio = userService.borrarUsuarioId(idUsuarioABorrar);

        Assertions.assertAll(
                () -> Assertions.assertEquals("OK000", respuestaServicio.getBody().getCode())
        );

        LOGGER.info("Resultado: "+respuestaServicio.getBody().getCode());
    }

    @Test
    @DisplayName("Edge case - UserServiceIntegracion - borrarUsuarioPorIdFallaTest")
    public void borrarUsuarioPorIdFallaTest(){
        String idUsuarioABorrar = "123";

        Mockito.when(usersDAO.borrarUsuario(Mockito.anyString())).thenReturn(0L);

        ResponseEntity<ResponseDTO> respuestaServicio = userService.borrarUsuarioId(idUsuarioABorrar);

        Assertions.assertAll(
                () -> Assertions.assertEquals("ER001", respuestaServicio.getBody().getCode())
        );

        LOGGER.info("Resultado: "+respuestaServicio.getBody().getCode());
    }

    @Test
    @DisplayName("consumirApiPublicaTest")
    public void consumirApiPublica (){
        /* Debe ser una api publica existente/real */
        String url = "https://jsonplaceholder.typicode.com/todos/1";

        ResponseEntity<ResponseDTO> respuestaServicio = userService.consumirApiPublica(url);

        Assertions.assertAll(
                () -> Assertions.assertEquals("OK000", respuestaServicio.getBody().getCode())
        );

        LOGGER.info("Resultado: "+respuestaServicio.getBody().getCode());
    }
}
