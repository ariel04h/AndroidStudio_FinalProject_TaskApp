package com.example.taskapp.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.taskapp.R;
import com.example.taskapp.model.Task;
import com.example.taskapp.repository.TaskRepository;

import java.util.Calendar;

/**
 * Fragment allowing users to add a new task with title, description, and due date.
 */
public class AddTaskFragment extends Fragment {

    private EditText inputTitle, inputDescription, inputDueDate;
    private Button btnAddTask;
    private final TaskRepository repository = new TaskRepository();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);

        inputTitle = view.findViewById(R.id.inputTitle);
        inputDescription = view.findViewById(R.id.inputDescription);
        inputDueDate = view.findViewById(R.id.inputDueDate);
        btnAddTask = view.findViewById(R.id.btnAddTask);

        // Submit button logic
        btnAddTask.setOnClickListener(v -> {
            String title = inputTitle.getText().toString().trim();
            String desc = inputDescription.getText().toString().trim();
            String dueDate = inputDueDate.getText().toString().trim();

            if (TextUtils.isEmpty(title)) {
                inputTitle.setError("Title required");
                return;
            }

            repository.addTask(new Task(title, desc, dueDate));
            Toast.makeText(getContext(), "Task added!", Toast.LENGTH_SHORT).show();

            // Reset fields
            inputTitle.setText("");
            inputDescription.setText("");
            inputDueDate.setText("");
        });

        // Show date picker when due date field is clicked
        inputDueDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        String dateString = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        inputDueDate.setText(dateString);
                    }, year, month, day);

            datePickerDialog.show();
        });

        return view;
    }
}
