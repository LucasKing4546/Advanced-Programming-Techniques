package service;

public interface Subject {
     void attach(Observer observer);
     void notifyObservers();
}
