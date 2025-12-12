package service;

import domain.Entity;
import repository.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Service {
    private Repository repository;

    public Service(Repository repository){
        this.repository = repository;
    }

    public ArrayList<Entity> getAll(){
        return this.repository.getAll();
    }

    public List<Entity> viewTable(){
        return this.repository.getAll().stream()
                .sorted(Comparator.comparing(Entity::getStart_time))
                .toList();
    }

    public List<Entity> filter(int intensity){
        return this.repository.getAll().stream()
                .filter(entity -> entity.getIntensity() > intensity)
                .sorted(Comparator.comparing(Entity::getStart_time))
                .toList();
    }

    public List<Entity> filterIntervals(int start_time, String description){
        return this.repository.getAll().stream()
                .filter(entity -> entity.getStart_time() >= start_time)
                .filter(entity -> description.contains(entity.getDescription()))
                .toList();
    }

    public String getTotalHours(int start_time, String description){
        List<Entity> entities = this.filterIntervals(start_time, description);
        int result = 0;
        for (Entity entity : entities){
            result = result + entity.getEnd_time() - entity.getStart_time();
        }
        return String.valueOf(result);
    }
}
