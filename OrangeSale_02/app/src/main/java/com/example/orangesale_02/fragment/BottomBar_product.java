package com.example.orangesale_02.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orangesale_02.R;
import com.example.orangesale_02.adapter.BottomBar_fragmentAdapter;
import com.example.orangesale_02.adapter.ProductLeftAdapter;
import com.example.orangesale_02.adapter.ProductRightAdapter;
import com.example.orangesale_02.bean.ProductData;
import com.example.orangesale_02.bean.ProductLeftBean;
import com.example.orangesale_02.bean.ProductRightBean;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class BottomBar_product extends Fragment implements AbsListView.OnScrollListener {

    private ListView lv_left;
    private StickyListHeadersListView lv_right;
    private int currentLeftItem;
    ProductLeftAdapter leftAdapter;
    ProductRightAdapter rightAdapter;
    List<ProductLeftBean> leftData;
    List<ProductRightBean> rightData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.bottom_bar_product, container, false);
        return view;
    }
    //一般onCreateView()用于初始化Fragment的视图，
    // onViewCreated()一般用于初始化视图内各个控件，
    // 而onCreate()用于初始化与Fragment视图无关的变量。
    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_left = view.findViewById(R.id.lv_left);
        lv_right = view.findViewById(R.id.lv_right);

        initData();
    }

    private void initData() {
        //创建适配器
        leftAdapter = new ProductLeftAdapter(ProductData.getLeftData());
        //获取左侧数据
        leftData = ProductData.getLeftData();
        //获取右侧数据
        rightData = ProductData.getRightData(leftData);
        rightAdapter = new ProductRightAdapter(leftData, rightData, getContext());
        //为左侧布局设置适配器
        lv_left.setAdapter(leftAdapter);
        lv_right.setAdapter(rightAdapter);
        //为左侧条目设置点击事件
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                //当左侧条目被点击时,记录下被点击条目的type
                int type = leftData.get(position).type;
                leftAdapter.isClickLeft = true;
                //遍历右侧条目,并获取右侧条目的typeId,与刚刚获取的type对比,是否一致
                for (int i = 0; i < rightData.size(); i++) {
                    if (type == rightData.get(i).typeId) {
                        //如果找到对应的条目,那就将右侧条目滚动至对应条目,并跳出循环
                        lv_right.smoothScrollToPosition(i);
                        currentLeftItem = i;
                        //左侧条目点击事件，设置UI
                        leftAdapter.setCurrentSelect_Left(position);
                        //刷新数据适配器
                        leftAdapter.notifyDataSetChanged();
                        break;
                    }
                }
//                Toast.makeText(getActivity(),
//                        "您选中了"+leftData.get(position).title, Toast.LENGTH_SHORT).show();
            }
        });
        //为右侧条目设置点击事件
        lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
//            Toast.makeText(getActivity(),
//                    "右侧条目被点击了"+position, Toast.LENGTH_SHORT).show();
                //当右侧条目被点击时,获取被点击条目的typeId
                int typeId = rightData.get(position).typeId;
                leftAdapter.isClickLeft = false;
                //遍历左侧条目
                for (int i = 0; i < leftData.size(); i++) {
                    //获取左侧条目的type,与右侧条目的typeId对比是否一致
                    if (typeId == leftData.get(i).type) {
                        //说明找到了对应条目,跳出循环,设置当前被选中的条目
                        currentLeftItem = i;
                        //右侧滚动或点击，设置对应的左侧条目UI
                        leftAdapter.setCurrentSelect_Right(currentLeftItem);
                        //刷新数据适配器
                        leftAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });
        //为右侧条目添加滑动监听
        lv_right.setOnScrollListener(this);
    }
    /**
     * 右边滑动的事件
     * */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
    /**
     * 右边滑动的事件
     * */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        ProductRightBean data = rightData.get(firstVisibleItem);
        leftAdapter.isClickLeft = false;
        for (int i = 0; i < leftData.size(); i++) {
            //获取左侧条目的type,与右侧条目的typeId对比是否一致
            if (data.typeId == leftData.get(i).type) {
                //说明找到了对应条目,跳出循环,设置当前被选中的条目
                currentLeftItem = i;
                //右侧滚动或点击，设置对应的左侧条目UI
                leftAdapter.setCurrentSelect_Right(currentLeftItem);
                //刷新数据适配器
                leftAdapter.notifyDataSetChanged();
                break;
            }
        }
        int firdtVisiblePositon = lv_left.getFirstVisiblePosition();
        int lastVisiblePositon = lv_left.getLastVisiblePosition();
        if (data.typeId >= lastVisiblePositon || data.typeId <= firdtVisiblePositon){
            lv_left.setSelection(data.typeId);
        }
//        Log.i("ProductRightBean","" + data.typeId);
    }
}