package com.wizeline.maven.learningjavamaven.notifications;

public class SMSNotification implements Notification{

    @Override
    public void notifyUser() {
        System.out.println("Sending a SMS notification ...");
    }
}
