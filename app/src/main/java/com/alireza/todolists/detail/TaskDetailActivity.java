package com.alireza.todolists.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alireza.todolists.R;
import com.alireza.todolists.main.MainActivity;
import com.alireza.todolists.main.MainContract;
import com.alireza.todolists.model.AppDataBase;
import com.alireza.todolists.model.Task;
import com.google.android.material.snackbar.Snackbar;

public class TaskDetailActivity extends AppCompatActivity implements TaskDetailContract.View{
    private int selectedImportance=Task.IMPORTANCE_NORMAL;
    private EditText etDetail;
    private ImageView lastSelectedImportanceIv;
    private View deleteBtn;
    private View backBtn;
    private TaskDetailContract.Presenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new TaskDetailPresenter(AppDataBase.getAppDataBase(this).getTaskDao(),(Task) getIntent().getParcelableExtra(MainActivity.KEY_EXTRA));
        setContentView(R.layout.task_detail);
        backBtn=findViewById(R.id.btnBackDetail);
        etDetail=findViewById(R.id.etTittleTask);
        deleteBtn=findViewById(R.id.icDeleteDetail);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.deleteItem();
            }
        });

        View importanceHigh=findViewById(R.id.importanceHigh);
        importanceHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedImportance!=Task.IMPORTANCE_HIGH){
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView=findViewById(R.id.importanceHighIv);
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance=Task.IMPORTANCE_HIGH;
                    lastSelectedImportanceIv=imageView;
                }
            }
        });

        View importanceLow=findViewById(R.id.importanceLow);
        importanceLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedImportance!=Task.IMPORTANCE_LOW){
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView =findViewById(R.id.importanceLowIv);
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance=Task.IMPORTANCE_LOW;
                    lastSelectedImportanceIv=imageView;
                }
            }
        });
        View importanceNormal=findViewById(R.id.importancenormal);
        lastSelectedImportanceIv=importanceNormal.findViewById(R.id.importanceNormalIv);
        importanceNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedImportance!=Task.IMPORTANCE_NORMAL){
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView=findViewById(R.id.importanceNormalIv);
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance=Task.IMPORTANCE_NORMAL;
                    lastSelectedImportanceIv=imageView;
                }
            }
        });
        View saveChangesBtn=findViewById(R.id.btnSaveChanges);
        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.saveChanges(selectedImportance,etDetail.getText().toString());
            }
        });
        presenter.onAttach(this);
    }




    @Override
    public void deleteBtnVisibility(boolean visible) {
        deleteBtn.setVisibility(visible? View.VISIBLE : View.GONE);
    }

    @Override
    public void showTask(Task task) {
        etDetail.setText(task.getTittle());
        switch (task.getImportance()){
            case Task.IMPORTANCE_HIGH:
                findViewById(R.id.importanceHigh).performClick();
                break;
                case Task.IMPORTANCE_NORMAL:
                    findViewById(R.id.importancenormal).performClick();
                    break;
                    case Task.IMPORTANCE_LOW:
                        findViewById(R.id.importanceLow).performClick();
                        break;
        }
    }

    @Override
    public void showError(String error) {
        Snackbar.make(findViewById(R.id.rootDetailTask),error,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void returnResult(int resultCode, Task task) {
        Intent intent=new Intent();
        intent.putExtra(MainActivity.KEY_EXTRA,task);
        setResult(resultCode,intent);
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
