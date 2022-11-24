package com.cursojava.proyecto.patterns.behavioral;

import com.cursojava.proyecto.model.EntrenadorDTO;

import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.patterns.behavioral.chainofresponsibility.ChainOfClasificadorHandler;
import com.cursojava.proyecto.patterns.behavioral.chainofresponsibility.ClassAHandler;
import com.cursojava.proyecto.patterns.behavioral.chainofresponsibility.ClassBHandler;
import com.cursojava.proyecto.patterns.behavioral.chainofresponsibility.ClassCHandler;
import com.cursojava.proyecto.patterns.behavioral.command.CatchCommand;
import com.cursojava.proyecto.patterns.behavioral.command.Invoker;
import com.cursojava.proyecto.patterns.behavioral.command.Receiver;
import com.cursojava.proyecto.patterns.behavioral.iterator.Iterator;
import com.cursojava.proyecto.patterns.behavioral.iterator.PokedexCollection;
import com.cursojava.proyecto.patterns.behavioral.iterator.PokedexCollectionImpl;
import com.cursojava.proyecto.patterns.behavioral.observer.Observable;
import com.cursojava.proyecto.patterns.behavioral.observer.observers.ArenaEstrado1;
import com.cursojava.proyecto.patterns.behavioral.observer.observers.ArenaEstrado2;
import com.cursojava.proyecto.patterns.behavioral.observer.observers.ArenaEstrado3;
import com.cursojava.proyecto.patterns.behavioral.visitor.ElementVisitor;
import com.cursojava.proyecto.patterns.behavioral.visitor.Visitor;
import com.cursojava.proyecto.patterns.behavioral.visitor.elements.Hoenn;
import com.cursojava.proyecto.patterns.behavioral.visitor.elements.Johto;
import com.cursojava.proyecto.patterns.behavioral.visitor.elements.Kanto;
import com.cursojava.proyecto.patterns.behavioral.visitor.elements.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BehavioralPatterns {

    private static final Logger LOGGER = LoggerFactory.getLogger(BehavioralPatterns.class);
    @Bean
    public void ChainOfResposibility(){
        ChainOfClasificadorHandler ClassA = new ClassAHandler();
        ChainOfClasificadorHandler ClassB = new ClassBHandler();
        ChainOfClasificadorHandler ClassC = new ClassCHandler();

        // Setting up the change
        ClassA.setNextHandler(ClassB);
        ClassB.setNextHandler(ClassC);

        EntrenadorDTO entrenadorJuan = new EntrenadorDTO("Juan",null);
        EntrenadorDTO entrenadorFelipe = new EntrenadorDTO("Felipe",null);
        EntrenadorDTO entrenadorChristian = new EntrenadorDTO("Christian",null);
        EntrenadorDTO entrenadorArturo = new EntrenadorDTO("Arturo",null);

        LOGGER.info("----------------------------------------");

        ClassA.handler(entrenadorJuan, 50);
        ClassA.handler(entrenadorFelipe, 1500);
        ClassA.handler(entrenadorChristian, 3000);
        ClassA.handler(entrenadorArturo, 5000);
        LOGGER.info("----------------------------------------");

    }

    @Bean
    public void Command(){
        Invoker invoker = new Invoker();
        Receiver receiver = new Receiver();
        invoker.executeOperation(new CatchCommand(new Receiver()));
        invoker.executeOperation(() -> receiver.esperar());
        invoker.executeOperation(() -> receiver.capturar());
        LOGGER.info("----------------------------------------");

    }

    @Bean
    public void Iterator() {

        PokedexCollection pokedex = new PokedexCollectionImpl();
        pokedex.addPokemon(new PokemonDTO("Bulbasaur", "Planta"));
        pokedex.addPokemon(new PokemonDTO("Charmander", "Fuego"));
        pokedex.addPokemon(new PokemonDTO("Squirtle", "Agua"));
        pokedex.addPokemon(new PokemonDTO("Pikachu", "Electrico"));
        pokedex.addPokemon(new PokemonDTO("Sandslash", "Tierra"));

        Iterator baseIterator = pokedex.iterator("ALL");
        LOGGER.info("Todos los tipos...");
        while (baseIterator.hasNext()) {
            PokemonDTO c = baseIterator.next();
            LOGGER.info(c.toStringSimple());
        }
        LOGGER.info("----------------------------------------");

        // Channel Type Iterator
        Iterator plantaIterator = pokedex.iterator("Planta");
        LOGGER.info("Tipo Planta");
        while (plantaIterator.hasNext()) {
            PokemonDTO c = plantaIterator.next();
            LOGGER.info(c.toStringSimple());
        }
        LOGGER.info("----------------------------------------");
    }

    @Bean
    public void Observer(){
        Observable observable = new Observable();
        observable.attach(new ArenaEstrado1());
        observable.attach(new ArenaEstrado3());
        observable.attach(new ArenaEstrado2());

        EntrenadorDTO entrenadorDTO=new EntrenadorDTO("Arturo");
        LOGGER.info("-------------PRIMER ESTADO--------------");
        observable.setEntrenadorDTO(entrenadorDTO);
        observable.setEntrenadorDTO(new EntrenadorDTO("Juan"));
        observable.setEntrenadorDTO(new EntrenadorDTO("Benito"));
        LOGGER.info("------------SEGUNDO ESTADO--------------");
        observable.removeEntrenadorDTO(new EntrenadorDTO("Juan"));
        LOGGER.info("------------TERCER ESTADO---------------");
        observable.removeEntrenadorDTO(new EntrenadorDTO("Benito"));
        LOGGER.info("------------ULTIMO ESTADO---------------");
        observable.anunciarGanador(entrenadorDTO);

    }

    @Bean
    public void visitor(){
        List<Region> elements = new ArrayList<>();
        elements.add(new Kanto());
        elements.add(new Johto());
        elements.add(new Hoenn());

        Visitor visitor = new ElementVisitor();
        visitor.visit(elements);
    }
}
