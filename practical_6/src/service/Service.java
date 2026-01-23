package service;

import domain.Flight;
import domain.Ticket;
import domain.User;
import repository.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Service implements Subject{
    private Repository repository;
    private List<Observer> observers = new ArrayList<>();

    public Service(Repository repository){
        this.repository = repository;
    }


    public List<User> getUsers(){
        return repository.getUsers();
    }

    public List<Flight> getFlights() {
        return repository.getFlights();
    }

    public List<Ticket> getTickets() {
        return repository.getTickets();
    }

    // User service

    public void butTicket(int userID, int flightID){
        repository.buyTicket(userID, flightID, (int) ((Math.random() * (100 - 1)) + 1));
        notifyObservers();
    }

    public List<Flight> getUpcomingFlights(){
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm");
        String localTime = formatter.format(LocalDateTime.now());
        return repository.getFlights().stream()
                .filter(flight -> flight.getDepartureTime().compareTo(localTime) > 0)
                .toList();

    }

    public void cancelTicket(int userID, int flightID){
        repository.cancelTicket(userID, flightID);
        notifyObservers();
    }

    public List<Ticket> getUserTickets(int userID) {
        return repository.getUserTicket(userID);
    }




    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers){
            observer.update();
        }
    }
}
