package baz.practice.wizeline.learningjavamaven.observer;

public class LogErrorSuscriber implements Observer{
    @Override
    public void update(String message) {
        System.out.println("LogErrorSusciber: " + message);
    }

}
