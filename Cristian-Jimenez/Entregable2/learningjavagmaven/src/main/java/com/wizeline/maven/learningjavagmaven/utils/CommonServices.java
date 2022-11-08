package com.wizeline.maven.learningjavagmaven.utils;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Component;

        import com.wizeline.maven.learningjavagmaven.model.ResponseModel;
        import com.wizeline.maven.learningjavagmaven.service.UserService;

/**
 * Class Description goes here.
 * Created by enrique.gutierrez on 26/09/22
 */

@Component
public class CommonServices {

    @Autowired
    UserService userService;

    public ResponseModel login(String user, String password) {
        return userService.login(user, password);
    }

}