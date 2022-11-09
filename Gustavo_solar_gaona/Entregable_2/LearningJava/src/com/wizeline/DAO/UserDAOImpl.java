package com.wizeline.DAO;

import java.io.*;
import java.util.logging.Logger;

public class UserDAOImpl implements UserDAO{

    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class.getName());

    @Override
    public String createUser(String user, String password){
        createFile();
        LOGGER.info("Inicia procesamiento en capa de acceso de datos");
        LOGGER.info("Inicia proceso de alta de usuario en DB...");

        writeFile(user, password);

        LOGGER.info("Alta exitosa");
        return "success";
    }

    @Override
    public String login(String user, String password){
        createFile();
        LOGGER.info("Inicia procesamiento de capa de acceso de datos");
        LOGGER.info("Inicia proceso de login ...");

        if("success".equals(readFile(user, password))){
            LOGGER.info("Login exitoso");
            return "success";
        }
        else{
            return "User or password incorrectos";
        }
    }

    private void createFile(){
        try{
            File myObj = new File("file.txt");
            if(myObj.createNewFile()){
                LOGGER.info("File created" + myObj.getName());
            }else{
                LOGGER.info("File already exists");
            }
        }catch (IOException e){
            LOGGER.info("An error occurred");
            e.printStackTrace();
        }
    }

    public void writeFile(String user, String password){
        try{
            File file = new File("file.txt");
            if(file.createNewFile()){
                LOGGER.info("File created" + file.getName());
            }else{
                LOGGER.info("file already exists");
            }

            FileWriter fileWriter = new FileWriter(file.getName(), true);
            BufferedWriter bw = new BufferedWriter(fileWriter);

            bw.write(user + ", " + password);
            bw.newLine();
            bw.close();
            LOGGER.info("Successfully wrote to the file");

        }catch (IOException e){
            LOGGER.info("An error occurred");
            e.printStackTrace();
        }
    }

    public String readFile(String user, String password){
        String result = "faild";
        try{
            File file = new File("file.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while((line = br.readLine()) != null){
                if(line.contains(user) && line.contains(password)){
                    result = "success";
                }
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }

}
