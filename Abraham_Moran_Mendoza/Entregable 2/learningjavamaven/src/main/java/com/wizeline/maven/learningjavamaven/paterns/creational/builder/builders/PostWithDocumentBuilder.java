package com.wizeline.maven.learningjavamaven.paterns.creational.builder.builders;

import com.wizeline.maven.learningjavamaven.paterns.creational.builder.attachments.Document;

public class PostWithDocumentBuilder extends PostBuilder{
  @Override
  public void addAttatchment() {
    getPostDTO().attachments.add(new Document());
  }
}
