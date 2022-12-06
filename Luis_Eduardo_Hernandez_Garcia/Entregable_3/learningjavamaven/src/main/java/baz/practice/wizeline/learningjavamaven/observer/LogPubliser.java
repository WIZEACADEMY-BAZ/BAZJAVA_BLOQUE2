package baz.practice.wizeline.learningjavamaven.observer;

import java.util.ArrayList;
import java.util.List;

public class LogPubliser implements Subject{

    private List<Observer> observerList = new ArrayList<>();

    @Override
    public void attach(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void log(String message) {
        for (Observer observer : observerList){
            observer.update(message);
        }
    }
}
