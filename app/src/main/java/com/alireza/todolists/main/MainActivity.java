package com.alireza.todolists.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.alireza.todolists.R;
import com.alireza.todolists.detail.TaskDetailActivity;
import com.alireza.todolists.model.AppDataBase;
import com.alireza.todolists.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View,TaskAdapter.ItemEventListener{
    public static final int RESULT_CODE_ADD = 1003;
    public static final int RESULT_CODE_UPDATE = 1004;
    public static final int RESULT_CODE_DELETE = 1005;
    public static final int REQUEST_CODE = 1001;
    private List <Task> tasks=new ArrayList<>();
    public static final String KEY_EXTRA = "task";
    private MainContract.Presenter presenter;
    private View emptyState;
    private TaskAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter=new MainPresenter(AppDataBase.getAppDataBase(this).getTaskDao());
        emptyState=findViewById(R.id.emptyState);
        adapter=new TaskAdapter(this,this);
        RecyclerView recyclerView=findViewById(R.id.rvMain);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));


        View btnDeleteAll=findViewById(R.id.deleteAllBtn);
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDeleteBtnClick();
            }
        });

        EditText etSearch=findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.search(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        View btnAddTask=findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, TaskDetailActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        presenter.onAttach(this);
    }

    @Override
    public void showItems(List<Task> tasks) {
        adapter.setTasks(tasks);
    }

    @Override
    public void update(Task task) {
        adapter.update(task);
    }

    @Override
    public void add(Task task) {

    }

    @Override
    public void delete(Task task) {

    }

    @Override
    public void clearItems() {
        adapter.clear();
    }

    @Override
    public void setEmptyVisibility(boolean visible) {
        emptyState.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemClick(Task task) {
        presenter.onTaskItemClick(task);
    }

    @Override
    public void onItemLongClick(Task task) {
        Intent intent=new Intent(this,TaskDetailActivity.class);
        intent.putExtra(KEY_EXTRA,task);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE){
            if ((resultCode == RESULT_CODE_ADD || resultCode==RESULT_CODE_UPDATE || resultCode==RESULT_CODE_DELETE) && data!=null){
                Task task=data.getParcelableExtra(KEY_EXTRA);
                if (task != null) {
                    if (resultCode==RESULT_CODE_ADD){
                        adapter.addItem(task);
                    }else if (resultCode==RESULT_CODE_UPDATE){
                        adapter.update(task);
                    }else
                        adapter.delete(task);
                    setEmptyVisibility(adapter.getItemCount()==0);


                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}