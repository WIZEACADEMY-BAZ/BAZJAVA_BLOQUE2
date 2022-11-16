package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.utils.disingpatterns.prototype.CuentasBaz;
import com.wizeline.maven.learningjavamaven.utils.disingpatterns.prototype.CuentasMolde;
import com.wizeline.maven.learningjavamaven.utils.disingpatterns.singleton.InstanseSingleton;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.wizeline.maven.learningjavamaven.constants.Constants.CONTENT_TYPE_JSON;
import static com.wizeline.maven.learningjavamaven.constants.MessageConstants.PROCESSING_GET_METHOD;

@RestController
@RequestMapping("/dising_patterns/api")
public class DesignPatternsController {
    private static final Logger LOGGER = Logger.getLogger(DesignPatternsController.class.getName());

    @GetMapping(value = "/prototype", produces = CONTENT_TYPE_JSON)
    public ResponseEntity<List<CuentasMolde>> prototype(@RequestParam int numeroCuentas) throws CloneNotSupportedException {
        LOGGER.info(PROCESSING_GET_METHOD);

        List<CuentasMolde> cuentas = new ArrayList<>();
        CuentasMolde cuentasMolde = new CuentasBaz();

        cuentasMolde.setSaldoInicial(20000.00);
        cuentasMolde.setSaldoMaximo(100000.00);

        for(int i = 0; i < numeroCuentas; i++){
            cuentas.add(cuentasMolde.generateCuentas());
        }

        InstanseSingleton instanseSingleton = InstanseSingleton.getInstance();
        instanseSingleton.randomNumber();

        return new ResponseEntity<List<CuentasMolde>>(cuentas, HttpStatus.OK);
    }

    @GetMapping(value = "/singleton", produces = CONTENT_TYPE_JSON)
    public ResponseEntity<String> singleton() {
        LOGGER.info(PROCESSING_GET_METHOD);

        InstanseSingleton instanseSingleton = InstanseSingleton.getInstance();
        instanseSingleton.randomNumber();

        return new ResponseEntity<>("Clase singleton", HttpStatus.OK);
    }

}
