package com.wizeline.maven.learningjavamaven.Iterator;

import com.wizeline.maven.learningjavamaven.model.PostDTO;

public interface Iterator {

  boolean hasNext();

  PostDTO next();
}
