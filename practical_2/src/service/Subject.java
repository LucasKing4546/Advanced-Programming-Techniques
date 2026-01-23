package service;

import domain.Booking;

public interface Subject {
    void attach(Observer observer);
    void notifyObservers(Booking booking);
}
