package com.wizeline.maven.learningjavamaven.paterns.creational.builder.attachments;

import static com.wizeline.maven.learningjavamaven.utils.Utils.randomAcountNumber;

public class Image extends Attachment{
  public Image(){
    setId((int)randomAcountNumber());
    setTitle("Victory");
    setUriContent("C:/images/image");
    setExtension("jpeg");
    setSize("1kb");
    setHeight("30px");
    setWidth("30px");
  }
}
