package com.wizeline.maven.learningjavagmaven.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
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

import com.wizeline.maven.learningjavagmaven.model.BookBean;
import com.wizeline.maven.learningjavagmaven.model.XmlBean;

@RequestMapping("/jaxb")
@RestController
public class JAXBController {

    private static final Logger LOGGER = Logger.getLogger(JAXBController.class.getName());

    @GetMapping("/getXML")
    public ResponseEntity<XmlBean> loginUser(){
        LOGGER.info("Test JAXB");
        System.out.println("Inicio Path del getXML");
        XmlBean response = new XmlBean();
        HttpHeaders responseHeaders = new HttpHeaders();
        System.out.println("responseHeaders " + responseHeaders.get("Content-Type"));
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        try {
            marshal();
            response.setData(unmarshall());
        } catch (JAXBException | IOException e) {
            System.out.println("+++++++++++++++++++++++++++cayó en el catch se imprime e. stacktrace");
            e.printStackTrace();

        }
        return new ResponseEntity<XmlBean>(response, responseHeaders, HttpStatus.OK);
    }


    public void marshal() throws JAXBException, IOException {
        BookBean book = new BookBean();
        System.out.println("esta dentro del MARSHALL");
        book.setId(1L);
        book.setName("Book1");
        book.setAuthor("Author1");
        book.setDate(new Date());
        JAXBContext context = JAXBContext.newInstance(BookBean.class);
        Marshaller mar= context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(book, new File("./book.xml"));
    }


    public BookBean unmarshall() throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(BookBean.class);
        System.out.println("entro en el UNMARSHALL");
        return (BookBean) context.createUnmarshaller()
                .unmarshal(new FileReader("./book.xml"));
    }

}