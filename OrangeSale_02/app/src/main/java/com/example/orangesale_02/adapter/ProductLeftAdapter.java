package com.example.orangesale_02.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.orangesale_02.R;
import com.example.orangesale_02.bean.ProductLeftBean;

import java.util.List;

public class ProductLeftAdapter extends BaseAdapter {

    private List<ProductLeftBean> leftList;
    private int rightclickItem = -1;
    private int leftclickItem = -1;
    public boolean isClickLeft = false;

    //创建一个构造方法
    public ProductLeftAdapter(List<ProductLeftBean> mList) {
        this.leftList = mList;
    }

    @Override
    public int getCount() {
        return leftList.size();
    }

    @Override
    public Object getItem(int position) {
        return leftList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Produce_leftViewHolder viewHolder = null;
        if (convertView==null){
            convertView = View.inflate(parent.getContext(), R.layout.productitem_left,null);

            //创建viewHolder对象
            viewHolder = new Produce_leftViewHolder();
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.v_bg = (LinearLayout) convertView.findViewById(R.id.v_bg);

            //让viewholder挂在convertview上面一起复用
            convertView.setTag(viewHolder);
        }else {
            //当convertView不为空时,吧viewholder取出来
            viewHolder = (Produce_leftViewHolder) convertView.getTag();
        }

        //获取对应条目的内容
        ProductLeftBean leftBean = (ProductLeftBean) getItem(position);
        //把对应条目的内容设置在控件上
        viewHolder.tv_title.setText(leftBean.title);

        //给左侧条目设置颜色
        if (isClickLeft){
            if (leftclickItem != position){
                viewHolder.v_bg.setBackgroundColor(
                        parent.getContext().getResources().getColor(R.color.yellow));
            }else{
                viewHolder.v_bg.setBackgroundColor(Color.WHITE);
            }
        }else{
            if (rightclickItem != position){
                viewHolder.v_bg.setBackgroundColor(
                        parent.getContext().getResources().getColor(R.color.yellow));
            }else{
                viewHolder.v_bg.setBackgroundColor(Color.WHITE);
            }
        }
        return convertView;
    }

    //创建一个viewholder,用来复用对象
    class Produce_leftViewHolder{
        TextView tv_title;
        LinearLayout v_bg;
    }

    //左侧条目点击事件，设置UI
    public void setCurrentSelect_Left(int position){
        this.leftclickItem = position;
    }

    //右侧滚动或点击，设置对应的左侧条目UI
    public void setCurrentSelect_Right(int currentLeftItem){
        this.rightclickItem = currentLeftItem;
    }
}
