package com.wizeline.baz.LearningSpring.patron.comportamiento;

import java.util.ArrayList;
import java.util.List;

public class ChatPublisher implements OrquestaMessage {

    private List<Observer> observerList = new ArrayList<>();


    @Override
    public void attach(Observer o) {
        observerList.add(o);
    }

    @Override
    public void dettach(Observer o) {
        observerList.remove(o);
    }

    @Override
    public void notifyMessage(Chat chat) {
        for(Observer o: observerList){
            o.update(chat);
        }
    }
}
