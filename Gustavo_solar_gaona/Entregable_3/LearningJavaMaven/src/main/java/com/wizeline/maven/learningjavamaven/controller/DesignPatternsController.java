package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.utils.disingpatterns.prototype.CuentasBaz;
import com.wizeline.maven.learningjavamaven.utils.disingpatterns.prototype.CuentasMolde;
import com.wizeline.maven.learningjavamaven.utils.disingpatterns.singleton.InstanseSingleton;
import com.wizeline.maven.learningjavamaven.utils.patroncomportamiento.Channel;
import com.wizeline.maven.learningjavamaven.utils.patroncomportamiento.ChannelCollection;
import com.wizeline.maven.learningjavamaven.utils.patroncomportamiento.ChannelCollectionImpl;
import com.wizeline.maven.learningjavamaven.utils.patroncomportamiento.Iterator;
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

    @GetMapping(value = "/iterator", produces = CONTENT_TYPE_JSON)
    public ResponseEntity<List<Channel>> iterator(@RequestParam String type) {
        LOGGER.info(PROCESSING_GET_METHOD);

        ChannelCollection channels = populateChannels();
        List<Channel> channelList = new ArrayList<>();

        if(type.equals("ALL")){
            Iterator baseIterator = channels.iterator("ALL");
            while (baseIterator.hasNext()) {
                Channel c = baseIterator.next();
                channelList.add(c);
                System.out.println(c.toString());
            }
        }

        if(type.equals("SPANISH")){
            Iterator englishIterator = channels.iterator("SPANISH");
            while (englishIterator.hasNext()) {
                Channel c = englishIterator.next();
                channelList.add(c);
                System.out.println(c.toString());
            }
        }

        return new ResponseEntity<>(channelList, HttpStatus.OK);
    }

    private static ChannelCollection populateChannels() {
        ChannelCollection channels = new ChannelCollectionImpl();
        channels.addChannel(new Channel(98.5, "ENGLISH"));
        channels.addChannel(new Channel(99.5, "SPANISH"));
        channels.addChannel(new Channel(100.5, "FRENCH"));
        channels.addChannel(new Channel(101.5, "ENGLISH"));
        channels.addChannel(new Channel(102.5, "SPANISH"));
        channels.addChannel(new Channel(103.5, "FRENCH"));
        channels.addChannel(new Channel(104.5, "ENGLISH"));
        channels.addChannel(new Channel(105.5, "SPANISH"));
        channels.addChannel(new Channel(106.5, "FRENCH"));
        return channels;
    }

}
