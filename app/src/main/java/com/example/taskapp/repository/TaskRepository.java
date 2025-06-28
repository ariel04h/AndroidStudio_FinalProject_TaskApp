package com.example.taskapp.repository;

import android.util.Log;

import com.example.taskapp.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Handles all Firestore-related operations for Task objects.
 * Includes add, update (mark complete or incomplete), and delete functionality.
 */
public class TaskRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    /**
     * Adds a new task to the Firestore under the current user's task collection.
     */
    public void addTask(Task task) {
        if (auth.getCurrentUser() == null) {
            Log.w("TaskRepo", "No user signed in — can't add task.");
            return;
        }

        String userId = auth.getCurrentUser().getUid();

        db.collection("users")
                .document(userId)
                .collection("tasks")
                .add(task.toMap())
                .addOnSuccessListener(documentReference ->
                        Log.d("TaskRepo", "Task added: " + documentReference.getId())
                )
                .addOnFailureListener(e ->
                        Log.e("TaskRepo", "Error adding task", e)
                );
    }

    /**
     * Updates a task's completion status to true or false.
     */
    public static void markTaskCompleted(String taskId, boolean completed) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .collection("tasks")
                .document(taskId)
                .update("isCompleted", completed);
    }

    /**
     * Deletes a task from the current user's Firestore task collection.
     */
    public static void deleteTask(String taskId) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .collection("tasks")
                .document(taskId)
                .delete();
    }
}
