package com.wizeline.baz.LearningSpring.patron.comportamiento;

public interface OrquestaMessage {

    void attach(Observer o);
    void dettach(Observer o);
    void notifyMessage(Chat chat);
}
