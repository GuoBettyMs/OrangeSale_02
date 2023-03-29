package com.example.orangesale_02.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.orangesale_02.R;
import com.example.orangesale_02.adapter.ShouyeGridBaseA;
import com.example.orangesale_02.bean.ShouyeGridIcon;

import java.util.ArrayList;

public class BottomBar_shouye extends Fragment {

    private Context mContext;
    private GridView gridView;
    private GridView firegridView;
    private BaseAdapter mAdapter = null;
    private ArrayList<ShouyeGridIcon> mData = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.bottom_bar_shouye, container, false);
        return view;
    }
    //一般onCreateView()用于初始化Fragment的视图，
    // onViewCreated()一般用于初始化视图内各个控件，
    // 而onCreate()用于初始化与Fragment视图无关的变量。
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.gridview);
        firegridView = view.findViewById(R.id.firegridview);

        mData = new ArrayList<ShouyeGridIcon>();
        mData.add(new ShouyeGridIcon(R.drawable.apple_one, "苹果🍎"));
        mData.add(new ShouyeGridIcon(R.drawable.avocado, "牛油果🥑"));
        mData.add(new ShouyeGridIcon(R.drawable.banana, "香蕉🍌"));
        mData.add(new ShouyeGridIcon(R.drawable.orange, "橘子🍊"));
        mData.add(new ShouyeGridIcon(R.drawable.orange_one, "柠檬🍋"));
        mData.add(new ShouyeGridIcon(R.drawable.cherry, "樱桃🍒"));
        mData.add(new ShouyeGridIcon(R.drawable.watermelon, "西瓜🍉"));

        mAdapter = new ShouyeGridBaseA(mData);
        gridView.setAdapter(mAdapter);
        firegridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(getActivity(),
                        "你点击了~" + position + "~项", Toast.LENGTH_SHORT).show();
            }
        });
        firegridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(getActivity(),
                        "你点击了~" + position + "~项", Toast.LENGTH_SHORT).show();
            }
        });
    }

}