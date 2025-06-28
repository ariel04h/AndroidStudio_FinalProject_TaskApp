package com.example.taskapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.viewmodel.TaskViewModel;

import java.util.ArrayList;

/**
 * Fragment displaying active (incomplete) tasks for the logged-in user.
 * Uses ViewModel to observe real-time task updates from Firestore.
 */
public class HomeFragment extends Fragment {

    private TaskAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up the adapter for active tasks (not profile view)
        adapter = new TaskAdapter(new ArrayList<>(), false);
        recyclerView.setAdapter(adapter);

        // Observe tasks from ViewModel (incomplete only)
        TaskViewModel viewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        viewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            adapter.updateTasks(tasks);
        });

        return view;
    }
}
