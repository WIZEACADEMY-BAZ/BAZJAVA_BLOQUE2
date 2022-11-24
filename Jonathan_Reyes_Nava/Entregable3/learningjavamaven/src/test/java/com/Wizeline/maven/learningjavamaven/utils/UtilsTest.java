package com.Wizeline.maven.learningjavamaven.utils;

import com.Wizeline.maven.learningjavamaven.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


@WebMvcTest(UtilsTest.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest(Utils.class)
public class UtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UtilsTest.class);

    MockedStatic<Utils> utilsMockeado = Mockito.mockStatic(Utils.class);

    @Test
    @DisplayName("UtilsMethod - ObtenerNumeroDeCuentaRandom")
    public void CuandoSeMandaALlamar_DevuelveUnNumeroRamdomPositivo(){
        LOGGER.info("Se contesta con lo esperado cuando se mande a llamar el metodo estatico de la clase Utils");
        utilsMockeado.when(Utils::randomAccountNumber).thenReturn(1);

        LOGGER.info("Se crea cuerpo que manda a llamar internamente el metodo estatico");
        Usuario usuarioEntidad = new Usuario();

        LOGGER.info("Se compara el valor esperado con el real");
        Assertions.assertEquals(1, usuarioEntidad.getId());
    }
}

