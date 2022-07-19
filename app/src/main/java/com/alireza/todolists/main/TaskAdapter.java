package com.alireza.todolists.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alireza.todolists.R;
import com.alireza.todolists.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks=new ArrayList<>();
    private ItemEventListener eventListener;
    private Drawable importanceDrawableHigh;
    private Drawable importanceDrawableNormal;
    private Drawable importanceDrawableLow;

    public TaskAdapter (Context context,ItemEventListener eventListener){
        this.eventListener=eventListener;
        this.importanceDrawableHigh= ResourcesCompat.getDrawable(context.getResources(),R.drawable.shape_rect_high,null);
        this.importanceDrawableNormal= ResourcesCompat.getDrawable(context.getResources(),R.drawable.shape_rect_normal,null);
        this.importanceDrawableLow= ResourcesCompat.getDrawable(context.getResources(),R.drawable.shape_rect_low,null);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrecy,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
    holder.bindTask(tasks.get(position));
    }

    public void addItem(Task task){
        tasks.add(0,task);
        notifyItemInserted(0);
    }

    public void update(Task task){
        for (int i = 0; i < tasks.size(); i++) {
            if (task.getId()==tasks.get(i).getId()){
                tasks.set(i,task);
                notifyItemChanged(i);
            }
        }
    }

    public void delete(Task task){
        for (int i = 0; i < tasks.size(); i++) {
            if (task.getId()==tasks.get(i).getId()){
                tasks.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public void addAll(List<Task> tasks){
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public void setTasks(List<Task> tasks){
        this.tasks=tasks;
        notifyDataSetChanged();
    }

    public void clear(){
        this.tasks.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTittle;
        private ImageView checkBox;
        private View importanceView;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTittle=itemView.findViewById(R.id.tvTaskTittle);
            checkBox=itemView.findViewById(R.id.checkboxTask);
            importanceView=itemView.findViewById(R.id.importanceView);
        }

        public void bindTask(Task task){
            tvTittle.setText(task.getTittle());
            if (task.isCompleted()){
                checkBox.setBackgroundResource(R.drawable.shape_circle_checkbox_solid);
                checkBox.setImageResource(R.drawable.ic_check_white_24dp);
                tvTittle.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }else {
                checkBox.setBackgroundResource(R.drawable.shape_circle_checkbox);
                checkBox.setImageResource(0);
                tvTittle.setPaintFlags(0);

            }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        eventListener.onItemClick(task);
                    }
                });

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        eventListener.onItemLongClick(task);
                        return false;
                    }
                });

                switch (task.getImportance()){
                    case Task.IMPORTANCE_HIGH:
                        importanceView.setBackground(importanceDrawableHigh);
                        break;
                    case Task.IMPORTANCE_NORMAL:
                        importanceView.setBackground(importanceDrawableNormal);
                        break;
                    case Task.IMPORTANCE_LOW:
                        importanceView.setBackground(importanceDrawableLow);
                        break;
                }
        }
    }

    public interface ItemEventListener{
        void onItemClick(Task task);

        void onItemLongClick(Task task);
    }
}
