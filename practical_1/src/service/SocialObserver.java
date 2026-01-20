package service;

import domain.Post;
import java.util.List;

public interface SocialObserver {
    void refreshFeed();
    String getUserName();
    List<String> getSubscribedTopics();
}

