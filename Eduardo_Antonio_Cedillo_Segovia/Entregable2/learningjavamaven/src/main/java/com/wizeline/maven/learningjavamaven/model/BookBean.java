package com.wizeline.maven.learningjavamaven.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement(name = "book")
@XmlType(propOrder = {"id", "name", "date"})
@XmlSeeAlso({BookBean.class})
public class BookBean implements Serializable {

    private static final long serialVersionUID = -8967927368021920536L;
    private Long id;
    private String name;
    private String autor;
    private Date date;

    public BookBean(Long id, String name, String autor, Date date) {
        this.id = id;
        this.name = name;
        this.autor = autor;
        this.date = date;
    }

    public BookBean() {

    }

    @XmlAttribute
    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAutor() {
        return autor;
    }

    public Date getDate() {
        return date;
    }
}
