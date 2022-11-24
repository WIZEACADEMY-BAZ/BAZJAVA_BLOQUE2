package com.Wizeline.maven.learningjavamaven.service;

import com.Wizeline.maven.learningjavamaven.model.*;
import com.Wizeline.maven.learningjavamaven.repository.UserDAOImpl;
import com.Wizeline.maven.learningjavamaven.repository.UsersDAO;
import com.Wizeline.maven.learningjavamaven.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
    private UsersDAO usersDAO;

    @Override
    public ResponseDTO createUser(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "fail";
        if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
            UserDAOImpl userDAO = new UserDAOImpl();
            result = userDAO.createUser(user, password);
            response.setCode("OK000");
            response.setStatus(result);
        }else {
            response.setCode("OK000");
            response.setStatus(result);
            response.setErrors(new ErrorDTO("ER001", "Error al crear usuario"));
        }
        return response;
    }

    @Override
    public ResponseDTO login(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "";
        if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
            UserDAOImpl userDao = new UserDAOImpl();
            result  = userDao.login(user,password);
        }
        if ("success".equals(result)) {
            response.setCode("OK000");
            response.setStatus(result);
        } else {
            response.setCode("ER001");
            response.setErrors(new ErrorDTO("ER001",result));
            response.setStatus("fail");
        }
        return response;
    }


    @Override
    public ResponseEntity<ResponseDTO> crearUsuarioService(UserDTO usuario) {
        ResponseDTO respuesta = new ResponseDTO();

        Usuario usuarioEntidad = new Usuario();

        BeanUtils.copyProperties(usuario, usuarioEntidad);

        usuarioEntidad = usersDAO.crearUsuario(usuarioEntidad);

        if(usuarioEntidad == null){
            LOGGER.info("No se insertó el usuario");
            ErrorDTO errorDTO = new ErrorDTO("ER001", "No se pudo crear el usuario");

            respuesta.setCode("ER001");
            respuesta.setStatus("No se pudo crear el usuario");
            respuesta.setErrors(errorDTO);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        respuesta.setCode("OK000");
        respuesta.setStatus("Usuario creado correctamente");

        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<ResponseDTO> loginService(String usuario, String contrasenia) {
        ResponseDTO respuesta = new ResponseDTO();

        Usuario usuarioEncontrado = usersDAO.login(usuario, contrasenia);

        if(usuarioEncontrado == null){
            LOGGER.info("No se encontró el usuario");
            ErrorDTO errorDTO = new ErrorDTO("ER001", "Usuario no encontrado");

            respuesta.setCode("ER001");
            respuesta.setStatus("Usuario no encontrado");
            respuesta.setErrors(errorDTO);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        respuesta.setCode("OK000");
        respuesta.setStatus("Login exitoso");
        respuesta.setResultado(usuarioEncontrado);

        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<ResponseDTO> cambiarContrasenia(String idUsuario, String nuevaContrasenia) {
        ResponseDTO respuesta = new ResponseDTO();

        long actualizado = usersDAO.cambiarContrasenia(idUsuario, nuevaContrasenia);

        if(actualizado <= 0){
            LOGGER.info("No se pudo actualizar el usuario");
            ErrorDTO errorDTO = new ErrorDTO("ER001", "No se pudo actualizar el usuarios con id: "+idUsuario);

            respuesta.setCode("ER001");
            respuesta.setStatus("No se pudo actualizar la contraseña");
            respuesta.setErrors(errorDTO);

            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        respuesta.setCode("OK000");
        respuesta.setStatus("Contrasenia de usuario actualizado");
        respuesta.setResultado("Total modificado: "+actualizado);

        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<ResponseDTO> obtenerUsuarios() {
        ResponseDTO respuesta = new ResponseDTO();

        List<Usuario> usuarios = usersDAO.obtenerUsuarios();

        if(usuarios.size() <= 0){
            LOGGER.info("No se encontraron usuarios");
            ErrorDTO errorDTO = new ErrorDTO("ER001", "No se encontraron usuarios");

            respuesta.setCode("ER001");
            respuesta.setStatus("No se encontraron usuarios");
            respuesta.setErrors(errorDTO);

            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        respuesta.setCode("OK000");
        respuesta.setStatus("Usuarios encontrados");
        respuesta.setResultado(usuarios);

        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<ResponseDTO> obtenerUsuarioPoId(String idUsuario) {
        ResponseDTO respuesta = new ResponseDTO();

        Usuario usuario = usersDAO.obtenerUsuario(idUsuario);


        if(usuario == null) {
            LOGGER.info("No se encontró el usuario");
            ErrorDTO errorDTO = new ErrorDTO("ER001", "Usuario no encontrado");

            respuesta.setCode("ER001");
            respuesta.setStatus("Usuario no encontrado");
            respuesta.setErrors(errorDTO);

            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
        respuesta.setCode("OK000");
        respuesta.setStatus("Usuario encontrado");
        respuesta.setResultado(usuario);

        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<ResponseDTO> borrarUsuarioId(String idUsuario) {
        ResponseDTO respuesta = new ResponseDTO();

        long cantidadBorrada = usersDAO.borrarUsuario(idUsuario);

        if(cantidadBorrada <= 0){
            LOGGER.info("No se borro el usuario");
            ErrorDTO errorDTO = new ErrorDTO("ER001", "No se borro el usuarios con id: "+idUsuario);

            respuesta.setCode("ER001");
            respuesta.setStatus("Usuario no borrado");
            respuesta.setErrors(errorDTO);

            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        respuesta.setCode("OK000");
        respuesta.setStatus("Usuario borrado!");
        respuesta.setResultado("Total eliminado: "+cantidadBorrada);

        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<ResponseDTO> consumirApiPublica(String url) {
        ResponseDTO respuesta = new ResponseDTO();

        /* Consumo de API pública */
        RestTemplate restTemplate = new RestTemplate();

        JsonplaceHolderDTO object = restTemplate.getForObject(url, JsonplaceHolderDTO.class);

        if(object == null){
            LOGGER.info("No se pudo consumir la api publica");
            ErrorDTO errorDTO = new ErrorDTO("ER001", "Fallo al consumir la api publica");

            respuesta.setCode("ER001");
            respuesta.setStatus("Error al consumir api");
            respuesta.setErrors(errorDTO);

            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        respuesta.setCode("OK000");
        respuesta.setStatus("Api consumida");
        respuesta.setResultado(object);

        return ResponseEntity.ok(respuesta);
    }
}
