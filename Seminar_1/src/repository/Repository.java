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
    public void  addElement(T element) {
        this.elements.add(element);
    }
}
