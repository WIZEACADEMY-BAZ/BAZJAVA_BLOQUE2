package com.wizeline.maven.learningjavamaven.paterns.creational.builder;

import com.wizeline.maven.learningjavamaven.paterns.creational.builder.builders.PostBuilder;
import com.wizeline.maven.learningjavamaven.model.PostDTO;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PostDirector {
  private PostBuilder postBuilder;

  public void setPostBuilder(PostBuilder postBuilder){
    this.postBuilder = postBuilder;
  }

  public void buildPost(){
    postBuilder.addAttatchment();
    postBuilder.addDate(new Date());
  }

  public PostDTO getFinishedPost(){
    return postBuilder.getPostDTO();
  }
}
