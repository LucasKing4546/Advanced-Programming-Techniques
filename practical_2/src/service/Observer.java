package service;

import domain.Booking;

public interface Observer {
    void update(Booking booking);
}