package com.wizeline.gradle.learningjavagradle.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.wizeline.gradle.learningjavagradle.model.BookBean;
import com.wizeline.gradle.learningjavagradle.model.UsuarioRest;
import com.wizeline.gradle.learningjavagradle.model.XmlBean;

@RequestMapping("/jaxb/restTemplate")
@RestController
public class RestTemplateExampleJAXB {
private static final Logger LOGGER = Logger.getLogger(JAXBController.class.getName());

	@GetMapping("/getXML")
    public ResponseEntity<XmlBean> loginUser(){
		LOGGER.info("Test JAXB");
		RestTemplate restTemplate = new RestTemplate();
		String url= "https://reqres.in/api/users/2";
		UsuarioRest resp = restTemplate
				  .getForObject(url , UsuarioRest.class);
		LOGGER.info("Respuesta restTemplate "+restTemplate.getForObject(url , UsuarioRest.class).toString());
        XmlBean response = new XmlBean();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        try {
			marshal(resp);
			response.setData(unmarshall());
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<XmlBean>(response, responseHeaders, HttpStatus.OK);
    }

	
	public void marshal(UsuarioRest usuario) throws JAXBException, IOException {
	    JAXBContext context = JAXBContext.newInstance(UsuarioRest.class);
	    Marshaller mar= context.createMarshaller();
	    mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    mar.marshal(usuario, new File("./usuario.xml"));
	}

	
	public UsuarioRest unmarshall() throws JAXBException, IOException {
	    JAXBContext context = JAXBContext.newInstance(UsuarioRest.class);
	    return (UsuarioRest) context.createUnmarshaller()
	      .unmarshal(new FileReader("./usuario.xml"));
	}
}
