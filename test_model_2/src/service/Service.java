package service;

import domain.Medication;
import org.w3c.dom.ls.LSInput;
import repository.MedicationRepository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Service {
    private MedicationRepository repository;

    public Service(MedicationRepository repository) {
        this.repository = repository;
    }

    public List<Medication> viewData(){
        return this.repository.getAll().stream().
                sorted(Comparator.comparing(Medication::getCategory))
                .toList();
    }

    public List<Medication> filter(String category, String name){
        Predicate<Medication> predicate = medication -> {
            if (category == null){
                return medication.getName().contains(name);
            }
            else{
                return medication.getCategory().contains(category);
            }
        };
        return this.repository.getAll().stream()
                .filter(predicate)
                .toList();
    }

    public List<String> sideEffects(String name){
        return Arrays.asList(this.repository.getAll().stream()
                .filter(medication -> Objects.equals(medication.getName(), name))
                .map(medication -> medication.getSideEffects().split(","))
                .toList().getLast());
    }

    public ArrayList<Medication> getAll(){
        return this.repository.getAll();
    }
}
