package com.wizeline.maven.learningjavamaven.notifications;


public class EmailNotification implements Notification {

    @Override
    public void notifyUser(){
        System.out.println("Enviando notificacion Email...");
    }
}
