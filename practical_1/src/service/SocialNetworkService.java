package service;

import domain.Post;
import repository.SocialRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SocialNetworkService implements SocialSubject {
    private SocialRepository repository;
    private List<SocialObserver> observers = new ArrayList<>();

    public SocialNetworkService(SocialRepository repository) {
        this.repository = repository;
    }


    public void publishPost(String user, String text) throws Exception {
        if (text.length() < 3) {
            throw new Exception("Post must be at least 3 characters long.");
        }
        Post post = new Post(0, text, LocalDateTime.now(), user);
        repository.savePost(post);
        notifyObservers(post);
    }


    public void updatePost(Post post, String newText) {
        repository.updatePostText(post.getId(), newText);
        for (SocialObserver observer : observers) {
            observer.refreshFeed();
        }
    }

    public void subscribe(String user, String topic) {
        repository.addSubscription(user, topic);
    }

    public List<String> searchTopics(String query) {
        return repository.findTopics(query);
    }

    public List<Post> getUserHistory(String user) {
        return repository.getUserPosts(user);
    }

    public List<Post> getFeed(String user) {
        return repository.getFeedForUser(user);
    }

    public List<String> getSubscriptions(String user) {
        return repository.getSubscriptions(user);
    }

    // --- Observer Pattern Implementation ---

    @Override
    public void attach(SocialObserver observer) {
        observers.add(observer);
    }


    @Override
    public void notifyObservers(Post post) {
        for (SocialObserver observer : observers) {
            if (isRelevant(post, observer.getUserName(), observer.getSubscribedTopics())) {
                observer.refreshFeed();
            }
        }
    }

    // --- Helper Logic for Relevance ---
    // Used to decide WHO gets the notification signal.
    private boolean isRelevant(Post post, String userName, List<String> userTopics) {
        String text = post.getText();

        if (text.contains("@" + userName)) {
            return true;
        }

        for (String topic : userTopics) {
            if (text.contains("#" + topic)) {
                return true;
            }
        }

        return false;
    }
}