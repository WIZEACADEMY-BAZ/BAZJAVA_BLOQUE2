package com.wizeline.maven.learningjavamaven.paterns.behavioral.Iterator;

import com.wizeline.maven.learningjavamaven.model.PostDTO;

import java.util.ArrayList;
import java.util.List;

public class PostDTOCollectionImpl implements PostDTOCollection {

  private List<PostDTO> postDTOList;

  public PostDTOCollectionImpl(){
    postDTOList = new ArrayList<>();
  }

  @Override
  public void addPost(PostDTO postDTO) {
    this.postDTOList.add(postDTO);
  }

  @Override
  public void removePost(PostDTO postDTO) {
    this.postDTOList.remove(postDTO);
  }

  @Override
  public Iterator iterator(String userId) {
    return new IteratorImpl(userId, this.postDTOList);
  }
}
