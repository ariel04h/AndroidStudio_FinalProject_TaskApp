package com.example.taskapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.viewmodel.TaskViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Fragment that shows the user's email and completed tasks.
 * Includes a logout button with confirmation.
 */
public class ProfileFragment extends Fragment {

    private TextView emailDisplay;
    private Button logoutButton;
    private TaskAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        emailDisplay = view.findViewById(R.id.emailDisplay);
        logoutButton = view.findViewById(R.id.logoutButton);
        RecyclerView recyclerView = view.findViewById(R.id.completedTaskRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TaskAdapter(new java.util.ArrayList<>(), true); // true = profile view
        recyclerView.setAdapter(adapter);

        // Show user email
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            emailDisplay.setText(user.getEmail());
        }

        // Logout with confirmation
        logoutButton.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(requireContext())
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(requireContext(), LoginActivity.class));
                        requireActivity().finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Load completed tasks
        TaskViewModel viewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        viewModel.getCompletedTasks().observe(getViewLifecycleOwner(), tasks -> {
            adapter.updateTasks(tasks);
        });

        return view;
    }
}
