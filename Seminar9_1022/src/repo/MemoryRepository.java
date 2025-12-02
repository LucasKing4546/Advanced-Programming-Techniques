package repo;

import domain.Doctor;
import domain.Identifiable;

import java.util.ArrayList;
import java.util.Iterator;

public class MemoryRepository <T extends Identifiable> implements IRepository<T> {
    protected ArrayList<T> elements = new ArrayList<>();

    @Override
    public void add(T elem) throws RepositoryException{
        if (elements.contains(elem)){
            throw new RepositoryException("Element already present in the repo");
        }
        elements.add(elem);
    }

    @Override
    public void delete(int id) {
        for(int i=0; i<= elements.size(); i++) {
            if (elements.get(i).getId() == id) {
                elements.remove(i);
                break;
            }
        }

          // alternative implementation
//        Doctor d = new Doctor(id, "","","", 0);
//        this.elements.remove(d);
    }

    @Override
    public int getSize() {
        return elements.size();
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }
}
