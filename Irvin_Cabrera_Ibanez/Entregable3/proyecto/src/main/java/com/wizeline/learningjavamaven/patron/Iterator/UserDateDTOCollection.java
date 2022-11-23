package com.wizeline.learningjavamaven.patron.Iterator;

import com.wizeline.learningjavamaven.model.UserDateDTO;

public interface UserDateDTOCollection {

  public void addUserDate(UserDateDTO userDateDTO);

  public void removeUserDate(UserDateDTO userDateDTO);

  public Iterator iterator(String userId);
}
