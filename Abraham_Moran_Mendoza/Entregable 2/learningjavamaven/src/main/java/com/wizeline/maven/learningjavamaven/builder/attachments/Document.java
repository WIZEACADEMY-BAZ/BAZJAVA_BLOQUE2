package com.wizeline.maven.learningjavamaven.builder.attachments;

import static com.wizeline.maven.learningjavamaven.utils.Utils.randomAcountNumber;

public class Document extends Attachment{
  public Document(){
    setId((int)randomAcountNumber());
    setUriContent("C:/documents/exampleDocument");
    setExtension("txt");
    setSize("5kb");
  }
}
