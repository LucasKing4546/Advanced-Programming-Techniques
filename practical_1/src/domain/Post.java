package domain;

import java.security.Timestamp;
import java.sql.Time;
import java.time.LocalDateTime;

public class Post {
    private int id;
    private String text;
    private LocalDateTime timestamp;
    private String username;

    public Post(int id, String text, LocalDateTime timestamp, String username) {
        this.id = id;
        this.text = text;
        this.timestamp = timestamp;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s", timestamp.toString(), username, text);
    }
}
