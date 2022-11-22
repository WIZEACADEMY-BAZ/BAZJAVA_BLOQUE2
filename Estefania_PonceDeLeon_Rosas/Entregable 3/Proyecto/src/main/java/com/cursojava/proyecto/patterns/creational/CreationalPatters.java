package com.cursojava.proyecto.patterns.creational;

import com.cursojava.proyecto.patterns.creational.abstractFactory.*;
import com.cursojava.proyecto.patterns.creational.builder.Direccion;
import com.cursojava.proyecto.patterns.creational.builder.seleccion.BloqueA;
import com.cursojava.proyecto.patterns.creational.builder.seleccion.BloqueB;
import com.cursojava.proyecto.patterns.creational.factoryMethod.Asignacion;
import com.cursojava.proyecto.patterns.creational.factoryMethod.Contrincante;
import com.cursojava.proyecto.patterns.creational.prototype.Estrategia;
import com.cursojava.proyecto.patterns.creational.prototype.PrimerBatalla;
import com.cursojava.proyecto.patterns.creational.prototype.SegundaBatalla;
import com.cursojava.proyecto.patterns.creational.singleton.CampeonLiga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CreationalPatters {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreationalPatters.class);

    @Bean
    private void singleton(){
        CampeonLiga databaseConfig = CampeonLiga.getCampeon("Felipe");
        databaseConfig.retar();
        CampeonLiga databaseConfig2 = CampeonLiga.getCampeon("Juan");
        databaseConfig2.retar();
    }

    @Bean
    private void prototype() {
       try {
           int primerBatalla =  (int)Math.floor(Math.random()*(5)+1);
           int segundaBatalla =  (int)Math.floor(Math.random()*(5)+1);
           Estrategia primerEstrategia = new PrimerBatalla();
           Estrategia segundaEstrategia = new SegundaBatalla();

           for(int i=0;i<primerBatalla;i++){
               primerEstrategia.crearEstrategia();
           }
           for(int i=0;i<segundaBatalla;i++){
               segundaEstrategia.crearEstrategia();
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    @Bean
    private void factoryMethod(){
        Asignacion asignacion = new Asignacion();
        Contrincante contrincante = asignacion.encontrarContrincante("Fuego");
        contrincante.verTipoBase();
    }

    @Bean
    private void abstractFactory(){
        SalaDeEntrenamiento sala;
        AprenderMovimiento aprenderFactory;
        int tipo =  (int)Math.floor(Math.random()*(2));
        switch (tipo){
            case 0:
                aprenderFactory = new PokemonFuego();
                break;
            case 1:
                aprenderFactory = new PokemonTierra();
                break;
            case 2:
                aprenderFactory = new PokemonAgua();
                break;
            default:
                LOGGER.info("No tenemos suficiente espacio en la sala de entrenamiento...");
                aprenderFactory=null;

        }
        sala = new SalaDeEntrenamiento(aprenderFactory);
        sala.Entrenar();
    }

    @Bean
    private void builder() {
        Direccion ronda = new Direccion();
        try {
            // Ronda 1
            ronda.setSeleccionBuilder(new BloqueA());
            ronda.iniciarRonda("1");
            LOGGER.info("------------------------------------------------");
            // Ronda 2
            ronda.setSeleccionBuilder(new BloqueB());
            ronda.iniciarRonda("2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
