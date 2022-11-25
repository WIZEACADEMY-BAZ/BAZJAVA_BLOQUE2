package baz.practice.wizeline.learningjavamaven.observer;

public interface Subject {

    public void attach(Observer observer);

    public void log(String message);
}
