package com.Wizeline.maven.learningjavamaven.service;

import com.Wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.Wizeline.maven.learningjavamaven.model.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseDTO createUser(String user, String password);

    ResponseDTO login(String user, String password);

    ResponseEntity<ResponseDTO> crearUsuarioService(UserDTO usuario);
    ResponseEntity<ResponseDTO> loginService(String usuario, String contrasenia);
    ResponseEntity<ResponseDTO> cambiarContrasenia(String idUsuario, String nuevaContrasenia);
    ResponseEntity<ResponseDTO> obtenerUsuarios();
    ResponseEntity<ResponseDTO> obtenerUsuarioPoId(String idUsuario);
    ResponseEntity<ResponseDTO> borrarUsuarioId(String idUsuario);
    ResponseEntity<ResponseDTO> consumirApiPublica(String url);


}
