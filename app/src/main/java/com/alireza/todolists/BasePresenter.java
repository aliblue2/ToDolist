package com.alireza.todolists;

public interface BasePresenter <T extends BaseView>{
 void onAttach(T view);

 void onDetach();
}
