package com.wizeline.learningjavamaven.patron.Iterator;

import com.wizeline.learningjavamaven.model.UserDateDTO;

import java.util.ArrayList;
import java.util.List;

public class UserDateDTOCollectionImpl implements UserDateDTOCollection {

  private List<UserDateDTO> userDateDTOList;

  public UserDateDTOCollectionImpl() {
    userDateDTOList = new ArrayList<>();
  }

  @Override
  public void addUserDate(UserDateDTO userDateDTO) {
    this.userDateDTOList.add(userDateDTO);
  }

  @Override
  public void removeUserDate(UserDateDTO userDateDTO) {
    this.userDateDTOList.remove(userDateDTO);
  }

  @Override
  public Iterator iterator(String userId) {
    return new IteratorImpl(userId, this.userDateDTOList);
  }
}
