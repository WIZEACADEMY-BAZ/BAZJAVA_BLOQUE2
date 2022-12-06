package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.LearningjavamavenApplication;
import com.wizeline.maven.learningjavamaven.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ThreadService {
    private static final Logger LOGGER = Logger.getLogger(LearningjavamavenApplication.class.getName());

    @Autowired
    UserService userService;

    @Async
    public ResponseModel createUserThread(String user, String password) throws InterruptedException  {
        ResponseModel response = new ResponseModel();
        LOGGER.info("Iniciando creación de usuario " + user + " en el hilo " + Thread.currentThread().getName());
        response = userService.createUser(user, password);
        Thread.sleep(500);
        LOGGER.info("Usuario creado exitosamente");
        return response;
    }
}
