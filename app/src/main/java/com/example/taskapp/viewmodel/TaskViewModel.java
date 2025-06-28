package com.example.taskapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskapp.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel that provides reactive access to Firestore tasks.
 * Separates active (incomplete) and completed task loading via LiveData.
 */
public class TaskViewModel extends ViewModel {

    private final MutableLiveData<List<Task>> taskList = new MutableLiveData<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String userId = FirebaseAuth.getInstance().getUid();

    public TaskViewModel() {
        loadTasks(); // load incomplete tasks by default for HomeFragment
    }

    /**
     * Returns LiveData for incomplete tasks only.
     */
    public LiveData<List<Task>> getTasks() {
        return taskList;
    }

    /**
     * Loads and observes only the completed tasks from Firestore.
     * Used in ProfileFragment.
     */
    public LiveData<List<Task>> getCompletedTasks() {
        MutableLiveData<List<Task>> completedTaskList = new MutableLiveData<>();

        if (userId == null) return completedTaskList;

        db.collection("users")
                .document(userId)
                .collection("tasks")
                .whereEqualTo("isCompleted", true)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null || snapshots == null) return;
                    List<Task> completed = new ArrayList<>();
                    for (DocumentSnapshot doc : snapshots.getDocuments()) {
                        Task task = doc.toObject(Task.class);
                        if (task != null) {
                            task.setId(doc.getId());
                            completed.add(task);
                        }
                    }
                    completedTaskList.setValue(completed);
                });

        return completedTaskList;
    }

    /**
     * Loads and observes all active (incomplete) tasks from Firestore.
     * Called when the ViewModel is initialized.
     */
    private void loadTasks() {
        if (userId == null) return;

        db.collection("users")
                .document(userId)
                .collection("tasks")
                .whereEqualTo("isCompleted", false)
                .orderBy("createdAt", Query.Direction.ASCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null || snapshots == null) return;
                    List<Task> tasks = new ArrayList<>();
                    for (DocumentSnapshot doc : snapshots.getDocuments()) {
                        Task task = doc.toObject(Task.class);
                        if (task != null) {
                            task.setId(doc.getId());
                            tasks.add(task);
                        }
                    }
                    taskList.setValue(tasks);
                });
    }
}
