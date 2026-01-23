package service;

import domain.Rental;

public interface Subject {
     void attach(Observer observer);
     void notifyObservers();
}
