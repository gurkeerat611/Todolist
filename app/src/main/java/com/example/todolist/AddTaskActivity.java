package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    public static final int ADD_TASK_REQUEST = 1;
    public static final String EXTRA_TASK = "com.example.todolist.EXTRA_TASK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        EditText etTaskTitle = findViewById(R.id.etTaskTitle);
        Button btnSaveTask = findViewById(R.id.btnSaveTask);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskTitle = etTaskTitle.getText().toString().trim();
                if (!taskTitle.isEmpty()) {
                    List<Task> tasks = ((MyApplication) getApplication()).getTasks();

                    Task newTask = new Task(taskTitle, false);
                    tasks.add(newTask);

                    // Set the result for the calling activity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(EXTRA_TASK, newTask);
                    setResult(Activity.RESULT_OK, resultIntent);

                    finish();
                }
            }
        });
    }
}
