package filter;

import domain.Patient;

public class FilterPatientByAge implements AbstractFilter<Patient>{
    private int age;
    public FilterPatientByAge(int age){
        this.age = age;
    }
    @Override
    public boolean accept(Patient entity) {
        return entity.getAge() == age;
    }
}
