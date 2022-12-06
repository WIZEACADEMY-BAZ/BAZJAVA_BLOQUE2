package com.wizeline.maven.learningjavamaven.paterns.creational.builder.builders;

import com.wizeline.maven.learningjavamaven.paterns.creational.builder.attachments.Document;
import com.wizeline.maven.learningjavamaven.paterns.creational.builder.attachments.Image;

public class PostWithDocumentAndImageBuilder extends PostBuilder{
  @Override
  public void addAttatchment() {
    getPostDTO().attachments.add(new Document());
    getPostDTO().attachments.add(new Image());
  }
}
