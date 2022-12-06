package com.wizeline.maven.learningjavamaven.factory;

import com.wizeline.maven.learningjavamaven.notifications.EmailNotification;
import com.wizeline.maven.learningjavamaven.notifications.Notification;
import com.wizeline.maven.learningjavamaven.notifications.PushNotification;
import com.wizeline.maven.learningjavamaven.notifications.SMSNotification;

public class NotificationFactory {

    public Notification createNotification(String channel) {
        if (channel == null || channel.isEmpty())
            return null;
        switch (channel) {
            case "SMS":
                return new SMSNotification();
            case "EMAIL":
                return new EmailNotification();
            case "PUSH":
                return new PushNotification();
            default:
                throw new IllegalArgumentException("Unknown channel " + channel);
        }
    }
}
