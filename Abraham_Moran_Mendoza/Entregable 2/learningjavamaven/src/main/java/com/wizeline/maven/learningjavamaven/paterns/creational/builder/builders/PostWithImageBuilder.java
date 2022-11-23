package com.wizeline.maven.learningjavamaven.paterns.creational.builder.builders;

import com.wizeline.maven.learningjavamaven.paterns.creational.builder.attachments.Image;

public class PostWithImageBuilder extends PostBuilder{
  @Override
  public void addAttatchment() {
    getPostDTO().attachments.add(new Image());
  }
}
