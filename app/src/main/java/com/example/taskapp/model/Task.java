package com.example.taskapp.model;

import com.google.firebase.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Model class representing a Task.
 * Includes title, description, due date, and completion status.
 */
public class Task {
    private String id;
    private String title;
    private String description;
    private String dueDate;
    private Timestamp createdAt;
    private boolean isCompleted = false;

    // Required empty constructor for Firestore deserialization
    public Task() {}

    public Task(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.createdAt = Timestamp.now();
        this.dueDate = dueDate;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public Timestamp getCreatedAt() { return createdAt; }

    // Prepares a map for storing to Firestore
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("description", description);
        map.put("createdAt", createdAt);
        map.put("dueDate", dueDate);
        map.put("isCompleted", isCompleted);
        return map;
    }
}
