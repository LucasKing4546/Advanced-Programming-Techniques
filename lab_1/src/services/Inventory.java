package services;

import domain.Car;
import domain.CarPark;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<CarPark> carParks;
    private double inventoryBalance;

    public Inventory(ArrayList<CarPark> carParks) {
        this.carParks = carParks;
        for  (CarPark carPark : carParks) {
            for (Car car : carPark.getCarRepo().getCars()) {
                this.inventoryBalance += car.getPrice();
            }
        }
    }

    public ArrayList<CarPark> getCarParks() {
        return carParks;
    }

    public void setCarParks(ArrayList<CarPark> carParks) {
        this.carParks = carParks;
    }

    public double getInventoryBalance() {
        return inventoryBalance;
    }

    public void setInventoryBalance(double inventoryBalance) {
        this.inventoryBalance = inventoryBalance;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "carParks=" + carParks +
                ", inventoryBalance=" + inventoryBalance +
                '}';
    }

    public void buyCar(Car car, String location) {
        for (CarPark carPark : carParks) {
            if (carPark.getLocation().equals(location)) {
                carPark.add(car);
            }
        }
    }

    public void sellCar(Car car, String location) {
        for (CarPark carPark : carParks) {
            if (carPark.getLocation().equals(location)) {
                carPark.remove(car);
                this.inventoryBalance -= car.getPrice();
            }
        }
    }

    public void showAllCarParks() {
        for  (CarPark carPark : carParks) {
            System.out.println(carPark);
        }
    }

    public void updateCarPrice(Car car, double price) {
        car.setPrice(price);
    }
}
