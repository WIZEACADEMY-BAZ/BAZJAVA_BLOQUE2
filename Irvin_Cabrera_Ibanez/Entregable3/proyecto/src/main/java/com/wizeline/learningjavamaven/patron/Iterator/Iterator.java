package com.wizeline.learningjavamaven.patron.Iterator;

import com.wizeline.learningjavamaven.model.UserDateDTO;

public interface Iterator {

  boolean hasNext();

  UserDateDTO next();
}
