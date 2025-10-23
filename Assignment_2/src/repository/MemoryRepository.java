package repository;

import domain.Identifiable;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryRepository<ID, T extends Identifiable<ID>> implements IRepository<ID, T>{
    private HashMap<ID, T> elements = new HashMap<>();

    @Override
    public Iterable<T> getAll() {
        return new ArrayList<>(elements.values());
    }

    @Override
    public void addElement(ID id, T element) {
        elements.put(id, element);
    }

    @Override
    public void removeElement(ID id) {
        elements.remove(id);
    }

    @Override
    public void updateElement(ID id, T newElement) {
        removeElement(id);
        addElement(id, newElement);
    }

    @Override
    public T findById(ID id){
        return elements.get(id);
    }
}
