package com.example.orangesale_02.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orangesale_02.R;
import com.example.orangesale_02.adapter.BottomBar_fragmentAdapter;
import com.example.orangesale_02.fragment.BottomBar_cart;
import com.example.orangesale_02.fragment.BottomBar_product;
import com.example.orangesale_02.fragment.BottomBar_profile;
import com.example.orangesale_02.fragment.BottomBar_shouye;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity  implements
        ViewPager.OnPageChangeListener{

    private BottomNavigationView bv_bottomNavigation;

//    private TextView txt_topbar;
//    private RadioGroup rg_tab_bar;
//    private RadioButton rb_channel;
//    private RadioButton rb_message;
//    private RadioButton rb_better;
//    private RadioButton rb_setting;

    private ViewPager vpager;
    private BottomBar_fragmentAdapter mAdapter;
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        bindViews();

        /***
         * ViewPager与BottomNavigationView的联动--点击
         * ***/
        bv_bottomNavigation.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_shouye:
                        //ViewPager 跳转到对应fragment
                        vpager.setCurrentItem(PAGE_ONE);
                        break;
                    case R.id.menu_product:
                        vpager.setCurrentItem(PAGE_TWO);
                        break;
                    case R.id.menu_cart:
                        vpager.setCurrentItem(PAGE_THREE);
                        break;
                    case R.id.menu_profile:
                        vpager.setCurrentItem(PAGE_FOUR);
                        break;
                }
                return true;
                //这里返回true，表示事件已经被处理。如果返回false，为了达到条目选中效果，还需要下面的代码
                // item.setChecked(true);  不论点击了哪一个，都手动设置为选中状态true（该控件并没有默认实现)
                // 。如果不设置，只有第一个menu展示的时候是选中状态，其他的即便被点击选中了，图标和文字也不会做任何更改
            }
        });
        //默认选中底部导航栏中的第一个
        bv_bottomNavigation.getMenu().getItem(0).setChecked(true);
    }

    private void bindViews() {
//        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
//        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
//        rb_channel = (RadioButton) findViewById(R.id.rb_channel);
//        rb_message = (RadioButton) findViewById(R.id.rb_message);
//        rb_better = (RadioButton) findViewById(R.id.rb_better);
//        rb_setting = (RadioButton) findViewById(R.id.rb_setting);
//        rg_tab_bar.setOnCheckedChangeListener(this);
        bv_bottomNavigation = (BottomNavigationView) findViewById(R.id.bv_bottomNavigation);
        vpager = (ViewPager) findViewById(R.id.vpager);

        //把fragment添加到list
        list = new ArrayList<>();
        list.add(new BottomBar_shouye());
        list.add(new BottomBar_product());
        list.add(new BottomBar_cart());
        list.add(new BottomBar_profile());
        mAdapter = new BottomBar_fragmentAdapter(getSupportFragmentManager(),list);

        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(PAGE_ONE);
        vpager.addOnPageChangeListener(this);
        vpager.setOffscreenPageLimit(4); //限制只有4个view可以左右滑动
//        rb_channel.setChecked(true);
//        rb_channel.setBackgroundColor(Color.RED);//被选中背景色就为红色
    }

//    @Override
//    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//        switch (checkedId) {
//            case R.id.rb_channel:
//                vpager.setCurrentItem(PAGE_ONE);
////                txt_topbar.setText("首页");
//                break;
//            case R.id.rb_message:
//                vpager.setCurrentItem(PAGE_TWO);
////                txt_topbar.setText("商品种类");
//                break;
//            case R.id.rb_better:
//                vpager.setCurrentItem(PAGE_THREE);
////                txt_topbar.setText("");
//                break;
//            case R.id.rb_setting:
//                vpager.setCurrentItem(PAGE_FOUR);
////                txt_topbar.setText("个人信息");
//                break;
//        }
//    }
    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    /***
     * ViewPager与BottomNavigationView的联动--左右滑动
     * ***/
    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                //ViewPager 左右滑动时，BottomNavigationView显示对应item
                bv_bottomNavigation.getMenu().getItem(0).setChecked(true);
//                rb_channel.setBackgroundColor(Color.RED);
//                rb_message.setBackgroundColor(Color.WHITE);
//                rb_better.setBackgroundColor(Color.WHITE);
//                rb_setting.setBackgroundColor(Color.WHITE);
                break;
            case 1:
                bv_bottomNavigation.getMenu().getItem(1).setChecked(true);
//                rb_channel.setBackgroundColor(Color.WHITE);
//                rb_message.setBackgroundColor(Color.RED);
//                rb_better.setBackgroundColor(Color.WHITE);
//                rb_setting.setBackgroundColor(Color.WHITE);
                break;
            case 2:
                bv_bottomNavigation.getMenu().getItem(2).setChecked(true);
//                rb_channel.setBackgroundColor(Color.WHITE);
//                rb_message.setBackgroundColor(Color.WHITE);
//                rb_better.setBackgroundColor(Color.RED);
//                rb_setting.setBackgroundColor(Color.WHITE);
                break;
            case 3:
                bv_bottomNavigation.getMenu().getItem(3).setChecked(true);
//                rb_channel.setBackgroundColor(Color.WHITE);
//                rb_message.setBackgroundColor(Color.WHITE);
//                rb_better.setBackgroundColor(Color.WHITE);
//                rb_setting.setBackgroundColor(Color.RED);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
//                case PAGE_ONE:
////                    rb_channel.setChecked(true);
//                    break;
//                case PAGE_TWO:
////                    rb_message.setChecked(true);
//                    break;
//                case PAGE_THREE:
////                    rb_better.setChecked(true);
//                    break;
//                case PAGE_FOUR:
////                    rb_setting.setChecked(true);
//                    break;
            }
        }
    }



}