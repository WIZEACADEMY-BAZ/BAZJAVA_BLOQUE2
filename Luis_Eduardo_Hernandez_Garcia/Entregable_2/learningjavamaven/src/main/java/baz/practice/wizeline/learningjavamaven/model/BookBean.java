package baz.practice.wizeline.learningjavamaven.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement(name = "book")
@XmlType(propOrder = {"id","name","date"})
@XmlSeeAlso({BookBean.class})
public class BookBean implements Serializable {
    public static final long serialVersionUID = 9923019230491093L;

    private Long id;
    private String name;
    private String author;
    private Date date;

    public BookBean(){
        super();
    }

    public BookBean(Long id, String name, String author, Date date){
        super();
        this.id = id;
        this.name = name;
        this.author = author;
        this.date = date;
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
    public void setAuthor(String author) {
        this.author = author;
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

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }
}
