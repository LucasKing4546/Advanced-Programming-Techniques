package domain;
import repositories.CarRepo;

import java.util.Objects;

public class CarPark {
    private CarRepo carRepo;
    private int id;
    private String location;

    public CarRepo getCarRepo() {
        return carRepo;
    }

    public void setCarRepo(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "CarPark{" +
                "carRepo=" + carRepo +
                ", id=" + id +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CarPark carPark = (CarPark) o;
        return id == carPark.id && Objects.equals(carRepo, carPark.carRepo) && Objects.equals(location, carPark.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carRepo, id, location);
    }

    public void add(Car car) {
        carRepo.add(car);
    }

    public void remove(Car car) {
        carRepo.remove(car);
    }
}
