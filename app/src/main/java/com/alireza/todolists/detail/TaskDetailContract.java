package com.alireza.todolists.detail;

import com.alireza.todolists.BasePresenter;
import com.alireza.todolists.BaseView;
import com.alireza.todolists.model.Task;

public interface TaskDetailContract {

    interface View extends BaseView{
        void deleteBtnVisibility(boolean visible);

        void showTask(Task task);

        void showError(String error);

        void returnResult(int resultCode,Task task);
    }

    interface Presenter extends BasePresenter<View>{

        void deleteItem();

        void saveChanges(int importance,String tittle);
    }
}
