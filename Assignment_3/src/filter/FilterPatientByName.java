package filter;

import domain.Patient;

import java.util.Objects;

public class FilterPatientByName implements AbstractFilter<Patient>{
    private String name;
    public FilterPatientByName(String name){
        this.name = name;
    }
    @Override
    public boolean accept(Patient entity) {
        return entity.getName().toLowerCase().contains(name.toLowerCase());
    }
}
