package com.wizeline.maven.learningjava.Learning.patron.Iterator;

import com.wizeline.maven.learningjava.Learning.model.UserDateDTO;

public interface UserDateDTOCollection {

    public void addUserDate(UserDateDTO userDateDTO);

    public void removeUserDate(UserDateDTO userDateDTO);

    public Iterator iterator(String userId);
}