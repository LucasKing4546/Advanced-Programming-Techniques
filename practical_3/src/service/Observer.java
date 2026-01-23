package service;

import domain.Package;

public interface Observer {
    void update(Package p);
}
