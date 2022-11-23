package com.wizeline.learningjavamaven.patron.Iterator;

import com.wizeline.learningjavamaven.model.UserDateDTO;

import java.util.List;

public class IteratorImpl implements Iterator {

  private String userId;
  private List<UserDateDTO> userDateDTOS;
  private int position;

  public IteratorImpl(String userId, List<UserDateDTO> userDateDTOS) {
    this.userId = userId;
    this.userDateDTOS = userDateDTOS;
  }

  @Override
  public boolean hasNext() {
    while (position < userDateDTOS.size()) {
      UserDateDTO userDateDTO = userDateDTOS.get(position);
      if (userDateDTO.getUser().equals(userId)) {
        return true;
      } else {
        position++;
      }
    }
    return false;
  }

  @Override
  public UserDateDTO next() {
    UserDateDTO userDateDTO = userDateDTOS.get(position);
    position++;
    return userDateDTO;
  }
}
