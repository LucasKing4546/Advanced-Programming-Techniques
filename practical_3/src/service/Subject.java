package service;

import domain.Package;

public interface Subject {
    abstract void attach(Observer observer);
    abstract void notifyObservers(Package p);
}
