package com.wizeline.maven.learningjavamaven.builder.builders;

import com.wizeline.maven.learningjavamaven.builder.attachments.Document;
import com.wizeline.maven.learningjavamaven.builder.attachments.Image;

public class PostWithDocumentAndImageBuilder extends PostBuilder{
  @Override
  public void addAttatchment() {
    getPostDTO().attachments.add(new Document());
    getPostDTO().attachments.add(new Image());
  }
}
