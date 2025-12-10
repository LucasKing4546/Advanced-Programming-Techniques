package service;

import domain.Session;
import repository.SessionRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private SessionRepository repository;

    public Service(SessionRepository repository) {
        this.repository = repository;
    }

    public ArrayList<Session> getAll(){
        return this.repository.getAll();
    }

    public List<Session> filterByIntensity(int intensity){
        return this.getAll().stream()
                .filter(session -> session.getIntensity() > intensity)
                .sorted(Comparator.comparing(Session::getStart_time))
                .toList();
    }

    public List<Session> filterByDescAndHours(int start_time, String description){
        return this.getAll().stream()
                .filter(session -> session.getStart_time() >= start_time)
                .filter(session -> description.contains(session.getDescription()))
                .toList();
    }

}
