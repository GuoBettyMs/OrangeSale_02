package com.example.orangesale_02.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orangesale_02.R;
import com.example.orangesale_02.adapter.ShopCartAdapter;
import com.example.orangesale_02.bean.ChatBean;
import com.example.orangesale_02.custominterface.StoreCallback;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class BottomBar_cart extends Fragment implements View.OnClickListener
, StoreCallback {

    private LinearLayout lay_edit;
    private TextView tv_total,tv_edit,tv_commit,tv_share_goods,tv_collect_goods,tv_delete_goods;
    private ImageView iv_selectAll;
    private RecyclerView rvStore;

    private List<ChatBean> shopsList = new ArrayList<>();
    private List<ChatBean.ChatItem> goodsList = new ArrayList<>();
    private ShopCartAdapter mShopCartAdapter;
    private AlertDialog dialog;//弹窗
    private RefreshLayout refresh;//刷新布局
    private LinearLayout layEmpty;//空布局

    private boolean isEdit = false;//是否编辑
    private boolean isAllChecked = false;//是否全选
    private boolean isHaveGoods = false;//购物车是否有商品

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.bottom_bar_cart, container, false);
        return view;
    }
    //一般onCreateView()用于初始化Fragment的视图，
    // onViewCreated()一般用于初始化视图内各个控件，
    // 而onCreate()用于初始化与Fragment视图无关的变量。
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    /**
     * 初始化UI
     */
    private void initData(){
        //绑定首页控件
        tv_edit = getActivity().findViewById(R.id.tv_edit);//编辑
        lay_edit = getActivity().findViewById(R.id.lay_edit);
        iv_selectAll= getActivity().findViewById(R.id.iv_selectAll);
        tv_total = getActivity().findViewById(R.id.tv_total);
        tv_commit= getActivity().findViewById(R.id.tv_commit);
        rvStore = getActivity().findViewById(R.id.recycleview);
        tv_share_goods = getActivity().findViewById(R.id.tv_share_goods);
        tv_collect_goods = getActivity().findViewById(R.id.tv_collect_goods);
        tv_delete_goods = getActivity().findViewById(R.id.tv_delete_goods);
        refresh = getActivity().findViewById(R.id.refresh);
        layEmpty = getActivity().findViewById(R.id.lay_empty);

        //首页控件添加点击事件
        tv_edit.setOnClickListener((View.OnClickListener) this);
        iv_selectAll.setOnClickListener((View.OnClickListener) this);
        tv_commit.setOnClickListener((View.OnClickListener) this);
        tv_share_goods.setOnClickListener((View.OnClickListener) this);
        tv_collect_goods.setOnClickListener((View.OnClickListener) this);
        tv_delete_goods.setOnClickListener((View.OnClickListener) this);

        //绑定商铺适配器
        rvStore.setLayoutManager(new LinearLayoutManager(getActivity()));
        mShopCartAdapter = new ShopCartAdapter(getActivity(),shopsList,this);
        rvStore.setAdapter(mShopCartAdapter);

        ChatBean.ChatItem waitao = new ChatBean.ChatItem("可乐",
                R.drawable.cola,"卡其","均码",1,2);
        waitao.setProductId(0);
        ChatBean.ChatItem maozi = new ChatBean.ChatItem("汉堡",
                R.drawable.hamburger,"卡其","均码",1,2);
        maozi.setProductId(1);
        goodsList.add(waitao);
        goodsList.add(maozi);

        List<ChatBean.ChatItem> goodsList1 = new ArrayList<>();
        ChatBean.ChatItem yifu = new ChatBean.ChatItem("披萨🍕",
                R.drawable.cat_1,"卡其","均码",1,2);
        yifu.setProductId(0);
        goodsList1.add(yifu);

        //商店初始化数据
        shopsList.add(new ChatBean("ww店", 0,goodsList));
        shopsList.add(new ChatBean("SS店", 1,goodsList1));

        isHaveGoods = true; //购物车有商品
        refresh.finishRefresh(); //下拉加载数据完成后，关闭下拉刷新动画
        layEmpty.setVisibility(View.GONE);//隐藏布局

        //禁用下拉刷新和上拉加载更多
        refresh.setEnableRefresh(false);
        refresh.setEnableLoadMore(false);
        //下拉刷新监听
        refresh.setOnRefreshListener(refreshLayout -> initData());

    }
    /**
     * 页面控件点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_edit://编辑
                if (!isHaveGoods){
                    showMsg("当前购物车空空如也");
                    return;
                }
                if (isEdit) {
                    tv_edit.setText("编辑");
                    lay_edit.setVisibility(View.GONE);
                    isEdit = false;
                } else {
                    tv_edit.setText("完成");
                    lay_edit.setVisibility(View.VISIBLE);
                    isEdit = true;
                }
                break;
            case R.id.iv_selectAll://全选
                if (!isHaveGoods){
                    showMsg("当前购物车空空如也");
                    return;
                }
                if (isAllChecked){
                    selectAllAct(false);//取消全选
                }else{
                    selectAllAct(true);//全选
                }
                break;
            case R.id.tv_commit://结算
                if (!isHaveGoods){
                    showMsg("当前购物车空空如也");
                    return;
                }
                if (mShopCartAdapter.totalCount == 0){
                    showMsg("请选择要结算的商品");
                    return;
                }
                //弹窗
                dialog = new AlertDialog.Builder(getContext())
                        .setMessage("总计:" + mShopCartAdapter.totalCount + "种商品，"
                                + mShopCartAdapter.totalPrice + "元")
                        .setPositiveButton("确定", (dialog, which) ->
                                mShopCartAdapter.deleteCheckedGoods())
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .create();
                dialog.show();
                break;
            case R.id.tv_delete_goods://删除
                if (mShopCartAdapter.totalCount == 0){
                    showMsg("请选择要删除的商品");
                    return;
                }
                //弹窗
                dialog = new AlertDialog.Builder(getContext())
                        .setMessage("确定要删除所选商品吗？")
                        .setPositiveButton("确定",(dialog,which)->
                                mShopCartAdapter.deleteCheckedGoods())
                        .setNegativeButton("取消",(dialog,which)-> dialog.dismiss())
                        .create();
                dialog.show();
                //改变界面UI
                tv_edit.setText("编辑");
                lay_edit.setVisibility(View.GONE);
                isEdit = false;
                break;
            case R.id.tv_collect_goods://收藏
                if (mShopCartAdapter.totalCount == 0){
                    showMsg("请选择要收藏的商品");
                    return;
                }
                showMsg("收藏成功");
                break;
            case R.id.tv_share_goods://分享
                if (mShopCartAdapter.totalCount == 0){
                    showMsg("请选择要分享的商品");
                    return;
                }
                showMsg("分享成功");
                break;
            default:
                break;
        }
    }

    /**
     * StoreCallback 回调
     * 主页结算按钮 -- UI显示
     */
    @Override
    public void allPrice(double totalPrice, int totalCount) {
        tv_total.setText("￥" + mShopCartAdapter.totalPrice);
        tv_commit.setText( mShopCartAdapter.totalCount
                == 0 ? "结算" : "结算(" +  mShopCartAdapter.totalCount + ")");
        if (shopsList.size() <= 0 ){
            isHaveGoods = false; //购物车无商品
            refresh.setEnableRefresh(true); //启动下拉刷新动画
            layEmpty.setVisibility(View.VISIBLE);//显示空布局
        }
    }

    /**
     * StoreCallback 回调
     * 主页全选按钮-- 接收传递值，修改UI
     */
    @Override
    public void isSelectAll(boolean state) {
        iv_selectAll.setImageDrawable(getResources().getDrawable(
                state ? R.drawable.ic_checked : R.drawable.ic_check));
        isAllChecked = state;
    }

    /****************************自定义方法*************************************************/
    /**
     * 主页全选按钮的点击事件
     * @param state 状态
     */
    private void selectAllAct(boolean state) {
        //修改背景
        iv_selectAll.setImageDrawable(getResources().getDrawable(
                state ? R.drawable.ic_checked : R.drawable.ic_check));
        for (ChatBean shop : shopsList) {
            //商品是否选中
            mShopCartAdapter.selectAllGoods(shop.getShopId(), state);
            //店铺是否选中
            mShopCartAdapter.selectAllStore(shop.getShopId(), state);
        }
        isAllChecked = state;
    }

    /**
     * Toast提示
     * @param msg
     */
    private void showMsg(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

}