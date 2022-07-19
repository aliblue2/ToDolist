package com.alireza.todolists.main;

import com.alireza.todolists.model.Task;
import com.alireza.todolists.model.TaskDao;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    private TaskDao taskDao;
    private List<Task>tasks;
    private MainContract.View view;
    public MainPresenter (TaskDao taskDao){
        this.taskDao=taskDao;
        this.tasks=taskDao.getAll();
    }
    @Override

    public void onAttach(MainContract.View view) {
        this.view = view;
        if (!tasks.isEmpty()) {
            view.showItems(tasks);
            view.setEmptyVisibility(false);
        } else
            view.setEmptyVisibility(true);

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onDeleteBtnClick() {
        taskDao.deleteAll();
        view.clearItems();
        view.setEmptyVisibility(true);
    }

    @Override
    public void search(String q) {
        if (!q.isEmpty()){
            List<Task> tasks=taskDao.search(q);
            view.showItems(tasks);
        }else {
            List<Task> tasks1=taskDao.getAll();
            view.showItems(tasks1);
        }
    }

    @Override
    public void onTaskItemClick(Task task) {
        task.setCompleted(!task.isCompleted());
        int result = taskDao.update(task);
        if (result>0){
            view.update(task);
        }
    }
}
