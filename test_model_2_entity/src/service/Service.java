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

    public void updateElement(Entity newEntity){
        this.repository.updateElement(newEntity.getName(), newEntity);
    }

    public List<Entity> viewTable(){
        return this.repository.getAll().stream()
                .sorted(Comparator.comparing(Entity::getCategory))
                .toList();
    }

    public List<Entity> filter(String name, String category){
        Predicate<Entity> predicate = entity -> {
            if (name == null){
                return entity.getCategory().contains(category);
            }
            else {
                return entity.getName().contains(name);
            }
        };

        return this.repository.getAll().stream()
                .filter(predicate)
                .toList();
    }

    public List<String> sideEffects(String name){
        return Arrays.asList(this.repository.getAll().stream()
                .filter(medication -> medication.getName().contains(name))
                .map(medication -> medication.getSideEffects().split(","))
                .toList().getLast());
    }
}
