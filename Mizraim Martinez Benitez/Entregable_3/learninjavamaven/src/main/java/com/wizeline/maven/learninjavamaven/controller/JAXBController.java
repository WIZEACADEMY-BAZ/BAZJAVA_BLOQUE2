package com.wizeline.maven.learninjavamaven.controller;

import com.wizeline.maven.learninjavamaven.model.BookBean;
import com.wizeline.maven.learninjavamaven.model.XMLBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

@RequestMapping("/jaxb")
@RestController
public class JAXBController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @GetMapping("/getXML")
    public ResponseEntity<XMLBean> loginUser(){
        LOGGER.info("Test JAXB");
        XMLBean response = new XMLBean();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        try {
            marshal();
            response.setData(unmarshall());
        } catch (JAXBException | IOException e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

    public void marshal() throws JAXBException, IOException {
        BookBean book = new BookBean();
        book.setId(1L);
        book.setName("Book1");
        book.setAuthor("Author1");
        book.setDate(new Date());
        JAXBContext context = JAXBContext.newInstance(BookBean.class);
        Marshaller mar = context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(book, new File("./book.xml"));
    }

    public BookBean unmarshall()throws  JAXBException, IOException{
        JAXBContext context = JAXBContext.newInstance(BookBean.class);
        return (BookBean) context.createUnmarshaller()
                .unmarshal(new File("./book.xml"));
    }
}
