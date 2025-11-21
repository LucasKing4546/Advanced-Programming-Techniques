package repository;

import domain.Identifiable;
import filter.AbstractFilter;

import java.util.ArrayList;

public class FilterRepository<ID, T extends Identifiable<ID>> extends MemoryRepository<ID, T>{
    public AbstractFilter<T> filter;
    public FilterRepository(Iterable<T> allEntities, AbstractFilter<T> filter){
        super();
        this.filter = filter;
        for (T entity : allEntities){
            super.addElement(entity.getId(), entity);
        }
    }
    @Override
    public Iterable<T> getAll() {
        ArrayList<T> elements = new ArrayList<>();
        for (T entity : super.getAll()) {
            if(filter.accept(entity)){
                elements.add(entity);
            }
        }
        return elements;
    }
}
