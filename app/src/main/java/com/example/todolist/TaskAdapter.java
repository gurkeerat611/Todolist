package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private OnTaskClickListener onTaskClickListener;

    public TaskAdapter(List<Task> tasks, OnTaskClickListener onTaskClickListener) {
        this.tasks = tasks;
        this.onTaskClickListener = onTaskClickListener;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void updateTask(int position, Task updatedTask) {
        if (tasks != null && position >= 0 && position < tasks.size()) {
            tasks.set(position, updatedTask);
            notifyItemChanged(position);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkboxTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkboxTask = itemView.findViewById(R.id.checkboxTask);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onTaskClickListener != null) {
                    Task task = tasks.get(position);
                    onTaskClickListener.onUpdateTask(task);
                }
            });

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onTaskClickListener != null) {
                    Task task = tasks.get(position);
                    onTaskClickListener.onDeleteTask(task);
                    return true; // consumed the long click
                }
                return false;
            });
        }

        public void bind(Task task) {
            checkboxTask.setChecked(task.isDone());
            checkboxTask.setText(task.getTitle());
        }
    }

    public interface OnTaskClickListener {
        void onUpdateTask(Task task);
        void onDeleteTask(Task task);
    }
}
