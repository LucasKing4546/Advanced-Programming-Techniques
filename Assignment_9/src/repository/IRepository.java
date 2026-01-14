package repository;

import domain.Identifiable;
import java.util.Optional;

public interface IRepository<ID, T extends Identifiable<ID>> {
    public void addElement(ID id, T element);
    public Optional<T> removeElement(ID id);
    public void updateElement(ID old_id, T newElement);
    public Optional<T> findById(ID id);
    public Iterable<T> getAll();
}