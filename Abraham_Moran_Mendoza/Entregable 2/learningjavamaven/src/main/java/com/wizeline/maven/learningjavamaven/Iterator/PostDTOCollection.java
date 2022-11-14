package com.wizeline.maven.learningjavamaven.Iterator;

import com.wizeline.maven.learningjavamaven.model.PostDTO;

public interface PostDTOCollection {

  public void addPost(PostDTO postDTO);

  public void removePost(PostDTO postDTO);

  public Iterator iterator(String userId);
}
