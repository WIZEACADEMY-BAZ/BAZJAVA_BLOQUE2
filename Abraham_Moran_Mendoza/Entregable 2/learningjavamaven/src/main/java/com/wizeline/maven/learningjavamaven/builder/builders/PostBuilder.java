package com.wizeline.maven.learningjavamaven.builder.builders;

import com.wizeline.maven.learningjavamaven.model.PostDTO;

import java.util.Date;

public abstract class PostBuilder {

  private PostDTO postDTO = null;

  public PostDTO getPostDTO(){
    return postDTO;
  }

  public PostBuilder(){
    postDTO = new PostDTO();
  }
  public abstract void addAttatchment();

  public void addId(String id){
    postDTO.setId(id);
  }

  public void addUserId(String userId){
    postDTO.setUserId(userId);
  }

  public void addBody(String body){
    postDTO.setBody(body);
  }

  public void addTitle(String tittle){
    postDTO.setTitle(tittle);
  }

  public void addDate(Date date){
    postDTO.setPostDate(date);
  }


}
