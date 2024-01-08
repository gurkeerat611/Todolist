package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {

    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskAdapter = new TaskAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(taskAdapter);

        FloatingActionButton fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(v -> openAddTaskActivity());
    }

    @Override
    public void onUpdateTask(Task task) {
        showUpdateTaskDialog(task);
    }

    private void showUpdateTaskDialog(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Task");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_update_task, null);
        final EditText input = viewInflated.findViewById(R.id.inputTask);
        input.setText(task.getTitle());

        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            String updatedTaskTitle = input.getText().toString().trim();
            if (!updatedTaskTitle.isEmpty()) {
                int position = taskAdapter.getTasks().indexOf(task);
                if (position != -1) {
                    Task updatedTask = new Task(updatedTaskTitle, task.isDone());
                    taskAdapter.updateTask(position, updatedTask);
                }
            }
        });

        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    public void onDeleteTask(Task task) {
        showDeleteTaskDialog(task);
    }

    private void showDeleteTaskDialog(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    int position = taskAdapter.getTasks().indexOf(task);
                    if (position != -1) {
                        taskAdapter.getTasks().remove(position);
                        taskAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void openAddTaskActivity() {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivityForResult(intent, AddTaskActivity.ADD_TASK_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AddTaskActivity.ADD_TASK_REQUEST && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra(AddTaskActivity.EXTRA_TASK)) {
                Task newTask = (Task) data.getSerializableExtra(AddTaskActivity.EXTRA_TASK);

                if (taskAdapter.getTasks() == null) {
                    taskAdapter.setTasks(new ArrayList<>());
                }

                taskAdapter.getTasks().add(newTask);
                taskAdapter.notifyDataSetChanged();
            }
        }
    }
}
