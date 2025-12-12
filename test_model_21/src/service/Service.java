package service;

import domain.Entity;
import repository.Repository;

import java.util.*;
import java.util.function.Predicate;

public class Service {
    private Repository repository;

    public Service(Repository repository){
        this.repository = repository;
    }

    public ArrayList<Entity> getAll(){
        return this.repository.getAll();
    }

    public void updateElement(String coefficients, Entity newEntity){
        this.repository.updateElement(coefficients, newEntity);
    }

    public List<Entity> viewTable(){
        return this.repository.getAll().stream()
                .sorted(Comparator.comparing(Entity::getDifficulty))
                .toList();
    }

    public List<Entity> filter(int degree, String difficulty){
        Predicate<Entity> predicate = entity -> {
            if (degree == -1){
                return entity.getDifficulty().contains(difficulty);
            }
            else{
                return entity.getDegree() == degree;
            }
        };
        return this.repository.getAll().stream()
                .filter(predicate)
                .toList();
    }

    public List<String> solutions(Entity entity){
        List<String> solution = new ArrayList<>();
        String[] list = entity.getCoefficients().split(",");
        if (entity.getDegree() == 1){
            double a = Double.parseDouble(list[0]);
            double b = Double.parseDouble(list[1]);
            double result = -1*b / a;
            solution.add(String.valueOf(result));
        }
        else{
            double a = Double.parseDouble(list[0]);
            double b = Double.parseDouble(list[1]);
            double c = Double.parseDouble(list[2]);
            double delta = b*b - 4*a*c;
            if (delta > 0){
                double result1 = (-1*b - Math.sqrt(delta)) / 2*a;
                double result2 = (-1*b + Math.sqrt(delta)) / 2*a;
                solution.add(String.valueOf(result1));
                solution.add(String.valueOf(result2));
            }
            else if (delta == 0){
                double result1 = -1*b / 2*a;
                solution.add(String.valueOf(result1));
            }
            else{
                solution.add("No solutions found!");
            }
        }
        return solution;
    }
}
