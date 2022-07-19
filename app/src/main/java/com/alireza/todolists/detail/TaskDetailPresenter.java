package com.alireza.todolists.detail;

import com.alireza.todolists.main.MainActivity;
import com.alireza.todolists.model.Task;
import com.alireza.todolists.model.TaskDao;

public class TaskDetailPresenter implements TaskDetailContract.Presenter{
    private TaskDao taskDao;
    private Task task;
    private TaskDetailContract.View view;
    public TaskDetailPresenter (TaskDao taskDao , Task task){
        this.taskDao=taskDao;
        this.task =task;
    }
    @Override
    public void onAttach(TaskDetailContract.View view) {
        this.view =view;
        if (task!=null){
            view.showTask(task);
            view.deleteBtnVisibility(true);
        }
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void deleteItem() {
        if (task!=null){
            int result=taskDao.delete(task);
            if (result>0){
                view.returnResult(MainActivity.RESULT_CODE_DELETE,task);
            }
        }
    }

    @Override
    public void saveChanges(int importance, String tittle) {
        if (tittle.isEmpty()){
            view.showError("عنوان خالی است / در باکس بالا باید عنوان وارد شود");
            return;
        }if (task==null){
            Task task=new Task();
            task.setTittle(tittle);
            task.setImportance(importance);
            long id=taskDao.add(task);
            task.setId(id);
            view.returnResult(MainActivity.RESULT_CODE_ADD,task);
        }else {
            task.setTittle(tittle);
            task.setImportance(importance);
            int result=taskDao.update(task);
            if (result>0){
                view.returnResult(MainActivity.RESULT_CODE_UPDATE,task);
            }
        }
    }
}
