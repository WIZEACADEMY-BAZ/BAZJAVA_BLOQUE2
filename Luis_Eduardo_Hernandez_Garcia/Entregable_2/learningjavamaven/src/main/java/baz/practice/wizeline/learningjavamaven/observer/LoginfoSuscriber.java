package baz.practice.wizeline.learningjavamaven.observer;

public class LoginfoSuscriber implements Observer{
    @Override
    public void update(String message) {
        System.out.println("LogInfoSuscriber: "+message);
    }
}
