package service;

import domain.Car;
import domain.Client;
import domain.Rental;
import repository.Repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Service implements Subject{
    List<Observer> observers = new ArrayList<>();
    private Repository repository;

    public Service(Repository repository){
        this.repository = repository;
    }

    public void rentCar(int client, Car car, LocalDate end){
        int total = getTotalCost(car, end);
        repository.rentCar(client, car.getId(), String.valueOf(end), total);
        notifyObservers();
    }

    public List<Car> getAvailableCars(){
        return repository.getCars().stream()
                .filter(Car::isStatus)
                .toList();
    }

    public List<String> getCategories(){
        return repository.getCategories();
    }

    public Client getClient(int id){
        return repository.getClient(id);
    }

    public int getTotalCost(Car car, LocalDate endDate){
        long days = ChronoUnit.DAYS.between(LocalDate.now(), endDate) + 1;
        return Math.toIntExact(car.getPrice() * days);
    }

    public List<Car> filterCars(List<Car> cars, String category){
        return cars.stream()
                .filter(car -> car.getCategory().equals(category))
                .toList();
    }

    public List<Car> getRentedCars(){
        return repository.getCars().stream()
                .filter(car -> !car.isStatus())
                .toList();
    }

    public void returnCar(int client, int car){
        repository.returnCar(client, car);
        notifyObservers();
    }

    public List<Rental> getRentals(){
        return repository.getRentals();
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers){
            observer.update();
        }
    }
}
