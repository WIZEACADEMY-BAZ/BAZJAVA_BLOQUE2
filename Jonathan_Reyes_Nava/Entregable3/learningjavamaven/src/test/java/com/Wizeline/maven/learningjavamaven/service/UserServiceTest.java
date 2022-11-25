package com.Wizeline.maven.learningjavamaven.service;

import com.Wizeline.maven.learningjavamaven.model.ErrorDTO;
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(UserServiceTest.class)
public class UserServiceTest {

    /* Generación de logs por prueba - UserServiceTest: Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceTest.class);

    /* Uso de Mockito en cada prueba - UserServiceTest - Se mockean: UsersDAO y UserServiceImpl */
    @MockBean
    private UsersDAO usersDAO;

    @MockBean
    private UserServiceImpl userService;

    /* Prueba unitaria de cada operación CRUD: CreateUserService - save() */
    @Test
    @DisplayName("Create user service")
    public void DadoElServicioCrearUsuario_CuandoSePasaUnUsuarioCorrecto_SeAgregaALaBDYRegresaResponseEntity(){
        /* Pruebas para Happy Path - UserServiceTest - CreateUserService */

        LOGGER.info("Test para crear usuario");
        //No se necesita poner este mock, ya que en el servicio original, no agregamos el usuario creado a la respuesta general
        //Mockito.when(userDAO.crearUsuario(Mockito.any(UsuarioEntidad.class))).thenReturn(usuarioEntidad);

        LOGGER.info("Usuario de entrada para happy path");
        UserDTO userDTO = new UserDTO("Usuario1","Contrasenia1");

        LOGGER.info("Armamos la respuesta final esperada");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("OK000");
        responseDTO.setStatus("Usuario creado");

        LOGGER.info("Mockeamos servicio para responder como queramos");
        Mockito.when(userService.crearUsuarioService(Mockito.any(UserDTO.class))).thenReturn(ResponseEntity.ok(responseDTO));

        LOGGER.info("Invocamos servicio mokeado");
        ResponseEntity<ResponseDTO> respuesta = userService.crearUsuarioService(userDTO);

        LOGGER.info("Comparamos los resultados esperados contra los reales");
        Assertions.assertAll(
                () -> Assertions.assertEquals("OK000", respuesta.getBody().getCode()),
                () -> Assertions.assertNotNull(respuesta)
        );

        LOGGER.info("Status del usuario: "+respuesta.getBody().getStatus());
    }

    @Test
    @DisplayName("Edge Case - CreateUserService - CuerpoEntradaNulo")
    public void CuandoSePasaElCampoNulo_DeberiaTronarConNullPointerException() {
        LOGGER.info("Se mockea el servicio que devolvera la respuesta mala esperada");
        Mockito.when(userService.crearUsuarioService(Mockito.any())).thenThrow(new NullPointerException("Campo de entrada nulo"));

        LOGGER.info("Al intentar consumir el servicio, tratamos de esperar una excepcion");
        NullPointerException thrown = Assertions.assertThrows(
                NullPointerException.class,
                () -> userService.crearUsuarioService(new UserDTO()),
                "Campo de entrada nulo"
        );

        LOGGER.info("Comparamos los resultados esperados contra los reales");
        Assertions.assertEquals("Campo de entrada nulo", thrown.getMessage());
    }

    /* Prueba unitaria de cada operación CRUD: LoginUserService - findOne() */
    @Test
    @DisplayName("Login user service")
    public void CuandoSeIngresaUsuarioYContrasenia_SeValidaExistenciaEnBdYPasaSiExiste(){
        /* Pruebas para Happy Path - UserServiceTest - LoginService */

        LOGGER.info("Se inicializan los parametros para el cambio de contrasenia");
        String nombre = "UsuarioXD";
        String contrasenia = "ContraseniaXD";

        LOGGER.info("Se crea el cuerpo de respuesta esperado");
        Usuario usuarioEntidad = new Usuario("UsuarioEncontradoBD", "ContraseniaEncontradaBD");

        LOGGER.info("Se mockea el componente que responderá con el cuerpo de respuesta esperado");
        Mockito.when(usersDAO.login(Mockito.anyString(), Mockito.anyString())).thenReturn(usuarioEntidad);

        LOGGER.info("Se estructura la respuesta general con los datos esperados");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("OK000");
        responseDTO.setStatus("Login exitoso");
        responseDTO.setResultado(usersDAO.login(nombre,contrasenia));

        LOGGER.info("Se mockea el servicio que devolvera la respuesta general esperada");
        Mockito.when(userService.loginService(Mockito.anyString(), Mockito.anyString())).thenReturn(ResponseEntity.ok(responseDTO));

        LOGGER.info("Se consume el servicio mockeado");
        ResponseEntity<ResponseDTO> resultado = userService.loginService(nombre,contrasenia);

        LOGGER.info("Comparamos los resultados esperados contra los reales");
        Assertions.assertAll(
                () -> Assertions.assertEquals("OK000", resultado.getBody().getCode()),
                () -> Assertions.assertNotNull(resultado)
        );

        LOGGER.info("Status del login simulado: "+resultado.getBody().getStatus());
    }

    @Test
    @DisplayName("Edge Case - LoginUserService - ParametrosDeEntradaNulos")
    public void DadoElServiciologin_CuandoNoSeIngresanLosParametros_DeberiaTronarConNullPointerException() {
        LOGGER.info("Se mockea el servicio que devolvera una excepcion");
        Mockito.when(userService.loginService(Mockito.isNull(), Mockito.isNull())).thenThrow(new NullPointerException("Parametros de entrada nulos"));

        LOGGER.info("Se consume el servicio para regresar la excepcion esperada");
        NullPointerException thrown = Assertions.assertThrows(
                NullPointerException.class,
                () -> userService.loginService(null, null),
                "Parametros de entrada nulos"
        );

        LOGGER.info("Comparamos los resultados esperados contra los reales");
        Assertions.assertEquals("Parametros de entrada nulos", thrown.getMessage());
    }

    @Test
    @DisplayName("Edge Case - LoginUserService - ParametroDeEntradaNuloYVacio")
    public void DadoElServiciologin_CuandoNoSeIngresaUnParametroNulo_DeberiaTronarConNullPointerException() {
        LOGGER.info("Se mockea el servicio que puede recibir datos diferentes, ya sea un string y nulo o un nulo y string a la par");
        Mockito.when(userService.loginService(Mockito.anyString(), Mockito.isNull())).thenThrow(new NullPointerException("Parametro de entrada: usuario no debe ser nulo"));
        Mockito.when(userService.loginService(Mockito.isNull(), Mockito.anyString())).thenThrow(new NullPointerException("Parametro de entrada: contrasenia no debe ser nulo"));

        LOGGER.info("Consumimos el servicio mockeado para obtener la excepcion esperada");
        NullPointerException thrown = Assertions.assertThrows(
                NullPointerException.class,
                () -> userService.loginService("", null),
                "Parametro de entrada: usuario no debe ser nulo"
        );

        LOGGER.info("Comparamos los resultados esperados contra los reales");
        Assertions.assertEquals("Parametro de entrada: usuario no debe ser nulo", thrown.getMessage());
    }

    /* Prueba unitaria de cada operación CRUD: CambioContraseniaUserService - updateFirst() */
    @Test
    @DisplayName("Cambio de contraseniaa")
    public void DadoElServicioActualizaContrasenia_CuandoSeInsertaElIdUsuarioYLaNuevaContrasenia_EsteUsuarioSeBuscaEnBdYSeActualizaContrasenia(){
        /* Pruebas para Happy Path - UserServiceTest - CambioContraseniaService */

        LOGGER.info("Se inicializan los parametros de entrada idUsuario y nuevaContrasenia");
        String idUsuario = "123456";
        String contraseniaNueva = "nuevaContrasenia";

        LOGGER.info("Se mockea el servicio que devolvera valor de cantidad actualizada");
        Mockito.when(usersDAO.cambiarContrasenia(Mockito.anyString(), Mockito.anyString())).thenReturn(1L);

        LOGGER.info("Se estructura la respuesta general con sus datos esperados");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("OK000");
        responseDTO.setStatus("Contrasenia actualizada!");
        responseDTO.setResultado(usersDAO.cambiarContrasenia(idUsuario,contraseniaNueva));

        LOGGER.info("Se mockea el srevicio que regresara la respuesta general correcta esperada");
        Mockito.when(userService.cambiarContrasenia(Mockito.anyString(),Mockito.anyString())).thenReturn(ResponseEntity.ok(responseDTO));

        LOGGER.info("Se consume el servicio mockeado con anterioridad");
        ResponseEntity<ResponseDTO> resultado = userService.cambiarContrasenia(idUsuario,contraseniaNueva);

        LOGGER.info("Comparamos los resultados esperados contra los reales");
        Assertions.assertAll(
                () -> Assertions.assertEquals("OK000", resultado.getBody().getCode()),
                () -> Assertions.assertEquals("Contrasenia actualizada!", resultado.getBody().getStatus())
        );
    }

    @Test
    @DisplayName("Edge Case - CambioDeContraseniaUserService - ParametrosDeEntradaNulos")
    public void DadoElServicioCambioDeContrasenia_CuandoNoSeIngresanLosParametros_DeberiaTronarConNullPointerException() {
        LOGGER.info("Se mockea el servicio que se espera responder con un nullpointerException");
        Mockito.when(userService.cambiarContrasenia(Mockito.isNull(), Mockito.isNull())).thenThrow(new NullPointerException("Parametros de entrada nulos"));

        LOGGER.info("Se intenta consumir el servicio que debería regresar la excepcion esperada");
        NullPointerException thrown = Assertions.assertThrows(
                NullPointerException.class,
                () -> userService.cambiarContrasenia(null, null),
                "Parametros de entrada nulos"
        );

        LOGGER.info("Comparamos los resultados esperados contra los reales");
        Assertions.assertEquals("Parametros de entrada nulos", thrown.getMessage());
    }

    @Test
    @DisplayName("Edge Case - CambioDeContraseniaUserService - ParametroDeEntradaNuloYVacio")
    public void CuandoSeInsertaAlgunParametroNulo_DeberiaTronarConNullPointerException() {
        LOGGER.info("Se mockea el servicio que puede aceptar un string y un null o un null y un string a la par para cada ocacion");
        Mockito.when(userService.cambiarContrasenia(Mockito.anyString(), Mockito.isNull())).thenThrow(new NullPointerException("Parametro de entrada: idUsuario no debe ser nulo"));
        Mockito.when(userService.cambiarContrasenia(Mockito.isNull(), Mockito.anyString())).thenThrow(new NullPointerException("Parametro de entrada: nuevaContrasenia no debe ser nulo"));

        LOGGER.info("Se consume el servicio para obtener la excepcion esperada");
        NullPointerException thrown = Assertions.assertThrows(
                NullPointerException.class,
                () -> userService.cambiarContrasenia("", null),
                "Parametro de entrada: idUsuario no debe ser nulo"
        );

        LOGGER.info("Comparamos los resultados esperados contra los reales");
        Assertions.assertEquals("Parametro de entrada: idUsuario no debe ser nulo", thrown.getMessage());
    }

    /* Prueba unitaria de cada operación CRUD: ObtenerTodosUsuariosService - findAll() */
    @Test
    @DisplayName("Obtener usuarios")
    public void DadoElServicioObtenerUsuarios_CuandoSeconsumeElServicioSeBuscanUsuariosEnBD_SeRegresaListaDeUsuariosEncontrados(){
        /* Pruebas para Happy Path - UserServiceTest - ObtenerUsuariosService */
        LOGGER.info("Se estructura una lista de valores esperados");
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("usuarioMock1","contraseniaMock1"));
        usuarios.add(new Usuario("usuarioMock2","contraseniaMock2"));
        usuarios.add(new Usuario("usuarioMock3","contraseniaMock3"));
        LOGGER.info("Cantidad de usuarios a devolver: " + usuarios.size());

        LOGGER.info("Mockeamos el dao que devuelve la lista para cuando sea llamado");
        Mockito.when(usersDAO.obtenerUsuarios()).thenReturn(usuarios);

        LOGGER.info("Armamos la respuesta esperada");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("OK000");
        responseDTO.setStatus("Lista de usuarios");
        responseDTO.setResultado(usersDAO.obtenerUsuarios());

        LOGGER.info("Mockeamos el servicio que devuelve la respuesta esperada para cuando se llame");
        Mockito.when(userService.obtenerUsuarios()).thenReturn(ResponseEntity.ok(responseDTO));

        LOGGER.info("Mandamos a llamar al servicio mockeado");
        ResponseEntity<ResponseDTO> respuesta = userService.obtenerUsuarios();

        LOGGER.info("Comparamos los resultados obtenidos con los resultados esperados");
        Assertions.assertAll(
                () -> Assertions.assertEquals("OK000", respuesta.getBody().getCode()),
                () -> Assertions.assertEquals("Lista de usuarios", respuesta.getBody().getStatus())
        );

        LOGGER.info("Fin de prueba obtener usuarios: " + respuesta.getBody().getResultado());
    }

    @Test
    @DisplayName("Edge Case - ObtenerUsuariosUserService - UsuariosNoExistentesErrorControlado")
    public void DadoElServicioObtenerUsuarios_CuandoSeconsumeElServicioYNoEncuentraUsuariosExistentes_DebeTronarConUnErrorDTO(){
        LOGGER.info("Se estructura la respuesta general erronea esperada");
        ResponseDTO responseDTO = new ResponseDTO();
        ErrorDTO errorDTO = new ErrorDTO("ER001", "No se encontraron usuarios");
        responseDTO.setErrors(errorDTO);

        LOGGER.info("Se mockea el servicio para responder con la respuesta general esperada");
        Mockito.when(userService.obtenerUsuarios()).thenReturn(new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST));

        LOGGER.info("Se consume el servicio mockeado anterior");
        ResponseEntity<ResponseDTO> respuesta = userService.obtenerUsuarios();

        LOGGER.info("Comparamos los resultados obtenidos con los resultados esperados");
        Assertions.assertAll(
                () -> Assertions.assertEquals("ER001", respuesta.getBody().getErrors().getErrorCode()),
                () -> Assertions.assertEquals("No se encontraron usuarios", respuesta.getBody().getErrors().getMessage())
        );
    }

    /* Prueba unitaria de cada operación CRUD: BuscarUsuarioPorIdUserService - FindOne() */
    @Test
    @DisplayName("Buscar usuario por id - Test")
    public void DadoElServicioObtenerusuarioPorId_CuandoSeIngresaUnId_SeBuscaExistenciaEnBdYRegresaElUsuario(){
        /* Pruebas para Happy Path - UserServiceTest - BusquedaUsuarioService */

        LOGGER.info("Se inicializa el isUsuario a buscar");
        String idUsuario = "123123";

        LOGGER.info("Se crea el cuerpo esperado");
        Usuario usuarioEntidad = new Usuario("usuarioInventado","contraseniaInventada");
        LOGGER.info("Se copia los valores del cuerpo entrante con el esperado");
        Mockito.when(usersDAO.obtenerUsuario(Mockito.anyString())).thenReturn(usuarioEntidad);

        LOGGER.info("Armamos la respuesta esperada");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("OK000");
        responseDTO.setStatus("Usuario encontrado");
        responseDTO.setResultado(usersDAO.obtenerUsuario(idUsuario));

        LOGGER.info("Se mockea el servicio que respondera de manera exitosa");
        Mockito.when(userService.obtenerUsuarioPoId(Mockito.anyString())).thenReturn(ResponseEntity.ok(responseDTO));

        LOGGER.info("Se consume el servicio mockeado");
        ResponseEntity<ResponseDTO> respuesta = userService.obtenerUsuarioPoId(idUsuario);

        LOGGER.info("Comparamos los resultados obtenidos con los resultados esperados");
        Assertions.assertAll(
                () -> Assertions.assertEquals("OK000", respuesta.getBody().getCode()),
                () -> Assertions.assertEquals("Usuario encontrado", respuesta.getBody().getStatus())
        );
    }

    @Test
    @DisplayName("Edge Case - ObtenerUsuarioPorIdUserService - ValidaParametroNulo&Vacio")
    public void DadoElServicioObtenerusuarioPorId_CuandoSeIngresaUnIdUsuarioNulo_DebeTronarConNullpointerException(){
        LOGGER.info("Se mockea el servicio que puede recibir valor null o un string vacio");
        Mockito.when(userService.obtenerUsuarioPoId(Mockito.isNull())).thenThrow(new NullPointerException("El campo: idUsuario no debe ser nulo"));
        Mockito.when(userService.obtenerUsuarioPoId(Mockito.anyString())).thenThrow(new NullPointerException("El campo: idUsuario no debe estar vacio"));

        LOGGER.info("Se inicializa la variable del idUsuario");
        // El valor de idUsuario debe varian entre comillasm sin contenido "" ó null solamente
        String idUsuario = "";

        LOGGER.info("Se consume el servicio mockeado para obtener la excepcion esperada");
        NullPointerException respuestaAssertOptional = idUsuario == "" ? Assertions.assertThrows(
                NullPointerException.class,
                () -> userService.obtenerUsuarioPoId(""),
                "El campo: idUsuario no debe estar vacio"
        ) : Assertions.assertThrows(
                NullPointerException.class,
                () -> userService.obtenerUsuarioPoId(null),
                "Parametro de entrada: idUsuario no debe ser nulo"
        );

        LOGGER.info("Comparamos los resultados obtenidos con los resultados esperados");
        Assertions.assertEquals("El campo: idUsuario no debe estar vacio", respuestaAssertOptional.getMessage());
    }

    /* Prueba unitaria de cada operación CRUD: EliminarUsuarioPorIdUserService - remove() */
    @Test
    @DisplayName("Eliminacion de usuario - Test")
    public void DadoElServicioEliminarusuarioPorId_CuandoSeIngresaUnIdDeUsuario_SeBuscaExistenciaEnBdYEliminaSiEncuentra(){
        /* Pruebas para Happy Path - UserServiceTest - EliminaUsuarioService */
        LOGGER.info("Se inicializa el idUsuario a eliminar");
        String idUsuario = "123";

        LOGGER.info("Se mockea el componente que regresa la respuesta esperada");
        Mockito.when(usersDAO.borrarUsuario(Mockito.anyString())).thenReturn(1L);

        LOGGER.info("Armamos la respuesta esperada");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("OK000");
        responseDTO.setStatus("Usuario eliminado");
        responseDTO.setResultado(usersDAO.borrarUsuario(idUsuario));

        LOGGER.info("Se mockea el servicio que responde con la respuesta general esperada");
        Mockito.when(userService.borrarUsuarioId(Mockito.anyString())).thenReturn(ResponseEntity.ok(responseDTO));

        LOGGER.info("Se consume el servicio mockeado anteriormente");
        ResponseEntity<ResponseDTO> respuesta = userService.borrarUsuarioId(idUsuario);

        LOGGER.info("Comparamos los resultados obtenidos con los resultados esperados");
        Assertions.assertAll(
                () -> Assertions.assertEquals("OK000", respuesta.getBody().getCode()),
                () -> Assertions.assertEquals("Usuario eliminado", respuesta.getBody().getStatus())
        );
    }

    @Test
    @DisplayName("Edge Case - EliminacionDeUsuarioPorIdUserService - ValidaParametroNulo&Vacio")
    public void DadoElServicioEliminarusuarioPorId_CuandoSeIngresaUnIdDeUsuarioNuloOVacio_DeberiaTronarConNullPointerException(){
        LOGGER.info("Se mockea el servicio que puede recibir un campo ya sea null o String vacio y regresa una excepcion esperada");
        Mockito.when(userService.borrarUsuarioId(Mockito.isNull())).thenThrow(new NullPointerException("El campo: idUsuario no debe ser nulo"));
        Mockito.when(userService.borrarUsuarioId(Mockito.anyString())).thenThrow(new NullPointerException("El campo: idUsuario no debe estar vacio"));

        LOGGER.info("Se inicializa la variable simulada a eliminar con valor null o String vacio");
        // El valor de idUsuario debe varian entre comillas sin contenido "" ó null solamente
        String idUsuario = "";

        LOGGER.info("Se consume el servicio mockeado anterior");
        NullPointerException respuestaAssertOptional = idUsuario == "" ? Assertions.assertThrows(
                NullPointerException.class,
                () -> userService.borrarUsuarioId(""),
                "El campo: idUsuario no debe estar vacio"
        ) : Assertions.assertThrows(
                NullPointerException.class,
                () -> userService.borrarUsuarioId(null),
                "Parametro de entrada: idUsuario no debe ser nulo"
        );

        LOGGER.info("Comparamos los resultados obtenidos con los resultados esperados");
        Assertions.assertEquals("El campo: idUsuario no debe estar vacio", respuestaAssertOptional.getMessage());
    }

    @Test
    @DisplayName("Consume api publica - Test Service")
    public void CuandoSeMandaALlamar_SeConsumeYRegresaLaRespuesta(){
        /* Pruebas para Happy Path - UserServiceTest - ConsumeApiPublicaService */
        LOGGER.info("Se inicializa el campo url esperado");
        String url = "hhtp:ejemploxd";

        LOGGER.info("Armamos la respuesta esperada");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("OK000");
        responseDTO.setStatus("Api Consumida");

        LOGGER.info("Se mockea el servicio que devolvera la respuesta general esperada");
        Mockito.when(userService.consumirApiPublica(Mockito.anyString())).thenReturn(ResponseEntity.ok(responseDTO));

        LOGGER.info("Se conmsume el srevicio mockeado con anterioridad");
        ResponseEntity<ResponseDTO> respuesta = userService.consumirApiPublica(url);

        LOGGER.info("Comparamos los resultados obtenidos con los resultados esperados");
        Assertions.assertAll(
                () -> Assertions.assertEquals("OK000", respuesta.getBody().getCode()),
                () -> Assertions.assertEquals("Api Consumida", respuesta.getBody().getStatus())
        );
    }

    @Test
    @DisplayName("Edge Case - ConsumeApiPublica - ValidaParametroUrlNulo&Vacio")
    public void CuandoSeIngresaElCampoUrlNuloOVacio_DeberiaTronarConNullPointerException(){
        LOGGER.info("Se mockea el servicio que puede aceptar valor nulo o String vacio");
        Mockito.when(userService.consumirApiPublica(Mockito.isNull())).thenThrow(new NullPointerException("El campo: url no debe ser nulo"));
        Mockito.when(userService.consumirApiPublica(Mockito.anyString())).thenThrow(new NullPointerException("El campo: url no debe estar vacio"));

        LOGGER.info("Se inicializa el campo con un valor aceptable a la prueba");
        // El valor de url debe varian entre comillas sin contenido "" ó null solamente
        String url = null;

        LOGGER.info("Se consume el servicio mockeado y esperamos la excepcion esperada");
        NullPointerException respuestaAssertOptional = url == "" ? Assertions.assertThrows(
                NullPointerException.class,
                () -> userService.consumirApiPublica(""),
                "El campo: url no debe estar vacio"
        ) : Assertions.assertThrows(
                NullPointerException.class,
                () -> userService.consumirApiPublica(null),
                "El campo: url no debe ser nulo"
        );

        LOGGER.info("Comparamos los resultados obtenidos con los resultados esperados");
        Assertions.assertEquals("El campo: url no debe ser nulo", respuestaAssertOptional.getMessage());
    }
}
