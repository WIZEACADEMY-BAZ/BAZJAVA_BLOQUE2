package com.wizeline.maven.learningjavamaven.builder.builders;

import com.wizeline.maven.learningjavamaven.builder.attachments.Document;

public class PostWithDocumentBuilder extends PostBuilder{
  @Override
  public void addAttatchment() {
    getPostDTO().attachments.add(new Document());
  }
}
