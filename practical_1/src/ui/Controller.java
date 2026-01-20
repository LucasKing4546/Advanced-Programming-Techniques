package ui;

import domain.Post;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import service.SocialNetworkService;
import service.SocialObserver;

import java.util.ArrayList;
import java.util.List;

public class Controller implements SocialObserver {

    private SocialNetworkService service;
    private String userName;

    // --- UI Components ---
    @FXML private ListView<Post> feedList;
    @FXML private ListView<Post> myPostsList;
    @FXML private ListView<String> subsList;

    @FXML private TextField postTextField;
    @FXML private TextField searchTopicField;
    @FXML private ListView<String> searchResultList;

    // --- Data Models ---
    private final ObservableList<Post> feedData = FXCollections.observableArrayList();
    private final ObservableList<Post> myPostsData = FXCollections.observableArrayList();
    private final ObservableList<String> subsData = FXCollections.observableArrayList();
    private final ObservableList<String> searchData = FXCollections.observableArrayList();

    // NO CONSTRUCTOR NEEDED

    @FXML
    public void initialize() {
        // 1. Bind UI to Data
        feedList.setItems(feedData);
        myPostsList.setItems(myPostsData);
        subsList.setItems(subsData);
        searchResultList.setItems(searchData);
    }

    // --- Dependency Injection ---
    // Call this from your Main class immediately after loading FXML
    public void setService(SocialNetworkService service, String userName) {
        this.service = service;
        this.userName = userName;

        // 1. Register as Observer
        this.service.attach(this);

        // 2. Load Initial Data
        refreshAll();
    }

    // --- Handlers ---

    @FXML
    public void handlePublishPost() {
        try {
            service.publishPost(userName, postTextField.getText());
            postTextField.clear();
            refreshMyPostsHelper();
        } catch (Exception e) { showError(e.getMessage()); }
    }

    @FXML
    public void handleSearchTopic(KeyEvent event) {
        String query = searchTopicField.getText();
        if (query != null && !query.isEmpty()) {
            populateList(searchData, service.searchTopics(query));
        } else {
            searchData.clear();
        }
    }

    @FXML
    public void handleSubscribe() {
        String selected = searchResultList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.subscribe(userName, selected);
            refreshSubsHelper();
            searchTopicField.clear();
            searchData.clear();
        }
    }

    // --- Observer Implementation ---

    @Override
    public void refreshFeed() {
        Platform.runLater(this::refreshAll);
    }

    @Override
    public String getUserName() { return userName; }

    @Override
    public List<String> getSubscribedTopics() {
        return service.getSubscriptions(userName);
    }

    // --- Helpers ---

    private void refreshAll() {
        refreshFeedHelper();
        refreshMyPostsHelper();
        refreshSubsHelper();
    }

    private void refreshFeedHelper() {
        if (service != null) populateList(feedData, service.getFeed(userName));
    }

    private void refreshMyPostsHelper() {
        if (service != null) populateList(myPostsData, service.getUserHistory(userName));
    }

    private void refreshSubsHelper() {
        if (service != null) populateList(subsData, service.getSubscriptions(userName));
    }

    private <T> void populateList(ObservableList<T> list, Iterable<T> items) {
        List<T> temp = new ArrayList<>();
        items.forEach(temp::add);
        list.setAll(temp);
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}