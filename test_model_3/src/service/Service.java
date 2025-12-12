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

    public void updateElement(Entity newEntity){
        this.repository.updateElement(newEntity.getNumber(), newEntity);
    }

    public List<Entity> viewTable(){
        return this.repository.getAll().stream()
                .sorted(Comparator.comparing(Entity::getDestination))
                .toList();
    }

    public List<Entity> filter(String airline){
        return this.repository.getAll().stream()
                .filter(entity -> entity.getAirline().contains(airline))
                .toList();
    }

    public String calculateArrival(Entity entity){
        int time = 600;
        time = time + entity.getDuration();
        int hours = time / 60;
        int minutes = time%60;
        int status = hours/12;
        if (status == 0){
            return hours + ":" + minutes + "AM";
        }
        else{
            if (hours == 12){
                return  hours  + ":" + minutes + "PM";
            }
            else{
                return  hours % 12 + ":" + minutes + "PM";
            }
        }
    }
}
