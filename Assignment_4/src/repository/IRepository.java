package repository;

import domain.Identifiable;

import java.util.ArrayList;

public interface IRepository<ID, T extends Identifiable<ID>> {
    public void addElement(ID id, T element);
    public void removeElement(ID id);
    public void updateElement(ID old_id, T newElement);
    public T findById(ID id);
    public Iterable<T> getAll();
}
