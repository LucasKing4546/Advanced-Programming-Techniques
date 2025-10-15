package repository;

import domain.Identifiable;

import java.util.ArrayList;


public class Repository<T extends Identifiable> {
    private ArrayList<T> elements = new ArrayList<>();

    public ArrayList<T> getElements() {
        return elements;
    }

    public void setElements(ArrayList<T> elements) {
        this.elements = elements;
    }

    public void addElement(T element) {
        elements.add(element);
    }

    public void removeElement(int id) {
        for (T element : elements) {
            if (element.getId() == id) {
                elements.remove(element);
                break;
            }
        }
    }

    public void updateElement(int old_id, T newElement) {
        removeElement(old_id);
        addElement(newElement);
    }
}
