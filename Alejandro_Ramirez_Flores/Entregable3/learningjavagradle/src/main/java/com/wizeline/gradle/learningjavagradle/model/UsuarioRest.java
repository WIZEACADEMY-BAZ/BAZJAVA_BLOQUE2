package com.wizeline.gradle.learningjavagradle.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "usuario")
@XmlSeeAlso({UsuarioRest.class})
public class UsuarioRest {
  private Data data;
  private Support support;
  
public Data getData() {
	return data;
}
@XmlElement(name = "datos")
public void setData(Data data) {
	this.data = data;
}
public Support getSupport() {
	return support;
}
@XmlElement(name = "support")
public void setSupport(Support support) {
	this.support = support;
}
@Override
public String toString() {
	return "UsuarioRest [data=" + data.toString() + ", support=" + support.toString() + "]";
}
  
}
