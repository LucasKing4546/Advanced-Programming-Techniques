package service;

import domain.Post;

public interface SocialSubject {
    void attach(SocialObserver observer);
    void notifyObservers(Post post);
}
