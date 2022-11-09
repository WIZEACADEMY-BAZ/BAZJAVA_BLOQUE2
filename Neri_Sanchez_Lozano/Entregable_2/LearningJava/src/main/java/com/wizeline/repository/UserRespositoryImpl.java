package com.wizeline.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

@Repository
public class UserRespositoryImpl implements UserRepository{
	
	private static final Logger log = Logger.getLogger(UserRespositoryImpl.class.getName());
	
	public String createUser(String user, String password) {
		createFile();
		log.info("Inicia procesamiento en capa de acceso a datos");
		log.info("Inicia proceso de alta de usuario en BD...");
		writeFile(user, password);
		
		log.info("Alta exitosa");
		return "success";
	}

	public String login(String user, String password) {
		createFile();
		log.info("Inicia procesamiento en capa de acceso a datos");
		log.info("Inicia proceso de login...");
		if("success".equals(readFile(user, password))) {
			log.info("Login exitoso");
			return "success";
		}else {
			return "Usuario o Password incorrecto";
		}
		
	}
	
	private void writeFile(String user, String password) {
		
		try {
			File file = new File("file.txt");
			if(file.createNewFile()) {
				log.info("File created " + file.getName());
			}else {
				log.info("File already exists");
			}
			
			FileWriter fileWritter = new FileWriter(file.getName(),true);
			BufferedWriter bw = new BufferedWriter(fileWritter);
			bw.write(user + ", " + password);
			bw.newLine();
			bw.close();
			
			log.info("Successfully wrote to the file");
			
		}catch(IOException e) {
			log.info("An error ocurred");
			e.printStackTrace();
		}
		
		
	}

	private void createFile() {
		
		try {
			File myObj = new File("file.txt");
			if(myObj.createNewFile()) {
				log.info("File created " + myObj.getName());
			}else {
				log.info("File already exists");
			}
		}catch (IOException e) {
			log.info("An error ocurred");
			e.printStackTrace();
		}
		
		
	}

	private String readFile(String user, String password) {
		String result = "fail";
		try {
			File file = new File("file.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			while((line = br.readLine()) != null) {
				if(line.contains(user) && line.contains(password)) {
					return "success";
				}
			}
			br.close();
			
		}catch(IOException e) {
			log.info("An error ocurred");
			e.printStackTrace();
		}
		return result;
	}
	
	

}