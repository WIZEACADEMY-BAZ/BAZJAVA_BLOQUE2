package com.wizeline.maven.learningjavamaven.Iterator;

import com.wizeline.maven.learningjavamaven.model.PostDTO;

import java.util.List;

public class IteratorImpl implements Iterator{

  private String userId;
  private List<PostDTO> postDTOS;
  private int position;

  public IteratorImpl(String userId, List<PostDTO> postDTOS){
    this.userId = userId;
    this.postDTOS = postDTOS;
  }


  @Override
  public boolean hasNext() {
    while(position < postDTOS.size()){
      PostDTO postDTO = postDTOS.get(position);
      if(postDTO.getUserId().equals(userId)){
        return true;
      }else{
        position++;
      }
    }
    return false;
  }

  @Override
  public PostDTO next() {
    PostDTO postDTO = postDTOS.get(position);
    position++;
    return postDTO;
  }
}
