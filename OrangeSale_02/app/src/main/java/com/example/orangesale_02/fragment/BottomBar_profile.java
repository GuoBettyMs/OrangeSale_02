package com.example.orangesale_02.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.orangesale_02.activity.MainActivity;
import com.example.orangesale_02.R;

public class BottomBar_profile extends Fragment {
    public BottomBar_profile(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.bottom_bar_profile, container, false);
        return view;
    }

    //一般onCreateView()用于初始化Fragment的视图，
    // onViewCreated()一般用于初始化视图内各个控件，
    // 而onCreate()用于初始化与Fragment视图无关的变量。
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (MainActivity.instance.loginIn) {
            //从登录选项进入，从textView获取个人信息
            Intent intent = getActivity().getIntent();
            Bundle bundle = intent.getExtras();
            String username = bundle.getString("username");
            String sex = bundle.getString("sex");
            String city = bundle.getString("city");

            TextView user_username = getActivity().findViewById(R.id.user_username1);
            user_username.setText("用户名："+username);
            TextView user_sex = getActivity().findViewById(R.id.user_sex1);
            user_sex.setText("性别："+sex);
            TextView user_city = getActivity().findViewById(R.id.user_city1);
            user_city.setText("城市："+city);
        }else{
            Intent intent = getActivity().getIntent();
            Bundle bundle = intent.getExtras();
            String username = bundle.getString("username");
            String sex = bundle.getString("sex");
            String city = bundle.getString("city");

            TextView user_username = getActivity().findViewById(R.id.user_username1);
            user_username.setText("用户名："+username);
            TextView user_sex = getActivity().findViewById(R.id.user_sex1);
            user_sex.setText("性别："+sex);
            TextView user_city = getActivity().findViewById(R.id.user_city1);
            user_city.setText("城市："+city);
        }

        //点击事件
        Button loginoutButton = (Button) getActivity().findViewById(R.id.loginout1);
        loginoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //从fragment跳转到activity中
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
    }


}