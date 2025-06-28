package com.example.taskapp.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.model.Task;
import com.example.taskapp.repository.TaskRepository;

import java.util.List;

/**
 * Adapter for displaying tasks in a RecyclerView.
 * Shows different buttons depending on whether it's used in Home (active tasks)
 * or Profile (completed tasks).
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private boolean isProfileView;

    /**
     * @param taskList      the list of tasks to display
     * @param isProfileView true if used in ProfileFragment, false for HomeFragment
     */
    public TaskAdapter(List<Task> taskList, boolean isProfileView) {
        this.taskList = taskList;
        this.isProfileView = isProfileView;
    }

    public void updateTasks(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.title.setText(task.getTitle());
        holder.description.setText(task.getDescription());
        holder.dueDate.setText("📅 Due: " + task.getDueDate());

        if (isProfileView) {
            // Show "In Progress" button for completed tasks
            holder.btnComplete.setText("In Progress");
            holder.btnDelete.setVisibility(View.GONE);

            holder.btnComplete.setOnClickListener(v -> {
                task.setCompleted(false);
                TaskRepository.markTaskCompleted(task.getId(), false);
                Toast.makeText(v.getContext(), "Moved back to active tasks", Toast.LENGTH_SHORT).show();
            });

        } else {
            // Show "Done" and "Delete" for active tasks
            holder.btnComplete.setText("Done");
            holder.btnDelete.setVisibility(View.VISIBLE);

            holder.btnComplete.setOnClickListener(v -> {
                task.setCompleted(true);
                TaskRepository.markTaskCompleted(task.getId(), true);
                Toast.makeText(v.getContext(), "Task marked as complete", Toast.LENGTH_SHORT).show();
            });

            holder.btnDelete.setOnClickListener(v -> {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete Task")
                        .setMessage("Are you sure you want to delete this task?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            TaskRepository.deleteTask(task.getId());
                            Toast.makeText(v.getContext(), "Task deleted", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    /**
     * ViewHolder class holding views for a single task item.
     */
    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, dueDate;
        Button btnComplete, btnDelete;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.taskTitle);
            description = itemView.findViewById(R.id.taskDescription);
            dueDate = itemView.findViewById(R.id.taskDueDate);
            btnComplete = itemView.findViewById(R.id.btnComplete);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
