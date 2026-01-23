package service;

import domain.Courier;
import domain.Package;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class Service implements Subject{
    List<Observer> observers = new ArrayList<>();
    Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public void deliverPackage(String recipient, String address) {
        List<Package> packages = repository.getPackages().stream()
                .filter(p -> p.getRecipient().equals(recipient) && p.getAddress().equals(address))
                .toList();
        repository.deliverPackage(recipient, address);
        notifyObservers(packages.getFirst());
    }

    public List<Package> getPackages(Courier courier){
        System.out.println(repository.getPackages());
        List<Package>  result = repository.getPackages().stream()
                .filter(p -> !p.isStatus())
                .filter(p -> courier.getStreets().contains(p.getAddress().split(" ")[0])
                || isPackageInRadius(p, courier))
                .toList();
        System.out.println(result);
        return result;
    }

    public List<Package> optimizeRoute(Courier courier){
        List<Package> packages = getPackages(courier);
        int current_X = courier.getX_coord();
        int current_Y = courier.getY_coord();
        List<Package> optimized = new ArrayList<>();
        while (!packages.isEmpty()) {
            int minDistance = Integer.MAX_VALUE;
            Package nextPackage = null;
            for (Package p : packages) {
                int dx = p.getLocationX() - current_X;
                int dy = p.getLocationY() - current_Y;
                int distance = dx * dx + dy * dy; // compare squared distances
                if (distance < minDistance) {
                    minDistance = distance;
                    nextPackage = p;
                }
            }
            if (nextPackage == null) break;
            optimized.add(nextPackage);
            current_X = nextPackage.getLocationX();
            current_Y = nextPackage.getLocationY();
            packages.remove(nextPackage);
        }
        return optimized;
    }

    public boolean isPackageInRadius(Package p, Courier c){
        return (sqrt((p.getLocationX() - c.getX_coord())^2 + (p.getLocationY() - c.getY_coord())^2) <= c.getRadius());
    }

    public Courier getCourierByName(String name){
        return repository.getCourier(name);
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(Package p) {
        for (Observer observer : observers){
            observer.update(p);
        }
    }

    public List<String> getStreets(Courier courier) {
        return List.of(courier.getStreets().split(","));
    }

    public void addPackage(Package p) {
        repository.addPackage(p);
        notifyObservers(p);
    }

    public List<Package> getAllPackages() {
        return repository.getPackages();
    }
}
