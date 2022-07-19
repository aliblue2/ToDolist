package com.alireza.todolists.main;

import com.alireza.todolists.BasePresenter;
import com.alireza.todolists.BaseView;
import com.alireza.todolists.model.Task;

import java.util.List;

public interface MainContract {
    interface View extends BaseView{
        void showItems(List<Task> tasks);

        void update(Task task);

        void add(Task task);

        void delete(Task task);

        void clearItems();

        void setEmptyVisibility(boolean visible);
    }

    interface Presenter extends BasePresenter<View>{
        void onDeleteBtnClick();

        void search(String q);

        void onTaskItemClick(Task task);
    }
}
