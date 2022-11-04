package com.wizeline.gradle.practicajava.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;

@XmlRootElement(name = "book")
@XmlType(propOrder = { "id", "name", "date" })
@XmlSeeAlso({ BookBean.class })
public class BookBean implements Serializable {

	private static final long serialVersionUID = 4991876266904831460L;

	private Long id;
	private String name;
	private String author;
	private Date date;

	public BookBean() {
		super();
	}

	public BookBean(Long id, String name, String author, Date date) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	@XmlAttribute
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@XmlElement(value = "title")
	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	@XmlTransient
	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
