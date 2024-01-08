package com.example.todolist;

import android.app.Application;
import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private List<Task> tasks;

    @Override
    public void onCreate() {
        super.onCreate();
        tasks = new ArrayList<>();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
