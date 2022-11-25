package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.utils.disingpatterns.prototype.CuentasMolde;
import com.wizeline.maven.learningjavamaven.utils.patroncomportamiento.Channel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DesignPatternsControllerTest {

    @Mock
    private CuentasMolde cuentasMolde;

    @Mock
    private Channel chanel;

    @InjectMocks
    private DesignPatternsController designPatternsController;

    @BeforeEach
    void init(){
        System.out.println("@BeforeEach => iniciando");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void prototypeTest(){
        System.out.println("@Test => prototypeTest()");
        List<CuentasMolde> cuentasMoldeList = new ArrayList<CuentasMolde>();
        cuentasMoldeList.add(cuentasMolde);
        assertAll(
                () -> assertNotNull(designPatternsController.prototype(3)),
                () -> assertEquals(designPatternsController.prototype(3).getStatusCode().value(), HttpStatus.OK.value())
        );
    }

    @Test
    void singletonTest(){
        System.out.println("@Test => singletonTest()");
        assertAll(
                () -> assertEquals(designPatternsController.singleton().getBody(), "Clase singleton"),
                () -> assertEquals(designPatternsController.singleton().getStatusCode().value(), HttpStatus.OK.value())
        );
    }

    @Test
    void iteratorAllTest(){
        System.out.println("@Test => iteratorTest()");
        List<Channel> channelList = new ArrayList<Channel>();
        channelList.add(chanel);
        assertAll(
                () -> assertNotNull(designPatternsController.iterator("ALL")),
                () -> assertEquals(designPatternsController.iterator("ALL").getStatusCode().value(), HttpStatus.OK.value())
        );
    }

    @Test
    void iteratorOneTest(){
        System.out.println("@Test => iteratorTest()");
        List<Channel> channelList = new ArrayList<Channel>();
        channelList.add(chanel);
        assertAll(
                () -> assertNotNull(designPatternsController.iterator("SPANISH")),
                () -> assertEquals(designPatternsController.iterator("SPANISH").getStatusCode().value(), HttpStatus.OK.value())
        );
    }

}