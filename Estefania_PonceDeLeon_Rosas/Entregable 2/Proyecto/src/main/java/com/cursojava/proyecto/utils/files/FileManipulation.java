package com.cursojava.proyecto.utils.files;

import com.cursojava.proyecto.model.ErrorDTO;
import com.cursojava.proyecto.model.ResponseDTO;
import com.cursojava.proyecto.repository.PokemonDAOImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class FileManipulation {
    private static final Logger LOGGER = Logger.getLogger(FileManipulation.class.getName());
    public static synchronized ResponseDTO createFile() {
        ResponseDTO response=new ResponseDTO();
        try {
            File myObj = new File("pokemonsFile.txt");
            if (myObj.createNewFile()) {
                LOGGER.info("File created: " + myObj.getName());
            } else {
                LOGGER.info("File already exists.");
            }
        } catch (IOException e) {
            LOGGER.info("An error occurred.");
            e.printStackTrace();
            response.setErrors(new ErrorDTO( "ER001", "Error al crear archivo"));
        }
        return response;
    }

    public static synchronized ResponseDTO writeFile(String nombre, String sonido, String msgOptionals) {
        ResponseDTO response=new ResponseDTO();
        try {
            File file = new File("pokemonsFile.txt");
            if (file.createNewFile()) {
                LOGGER.info("File created: " + file.getName());
            } else {
                LOGGER.info("File already exists.");
            }
            FileWriter fileWritter = new FileWriter(file.getName(),true);

            BufferedWriter bw = new BufferedWriter(fileWritter);

            bw.write(nombre+" te saluda con un "+sonido +","+msgOptionals);
            bw.newLine();
            bw.close();
            LOGGER.info("Successfully wrote to the file.");
        } catch (IOException e) {
            LOGGER.info("An error occurred.");
            e.printStackTrace();
            response.setErrors(new ErrorDTO( "ER001", "Error al escribir en el archivo"));
        }
        return response;
    }
}
