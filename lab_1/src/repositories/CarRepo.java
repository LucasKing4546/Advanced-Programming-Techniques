package repositories;
import domain.Car;
import java.util.ArrayList;
import java.util.Objects;

public class CarRepo {
    private ArrayList<Car> cars;

    public CarRepo(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "CarRepo{" +
                "cars=" + cars +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CarRepo carRepo = (CarRepo) o;
        return Objects.equals(cars, carRepo.cars);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cars);
    }

    public void add(Car car) {
        cars.add(car);
    }

    public void remove(Car car) {
        cars.remove(car);
    }
}
