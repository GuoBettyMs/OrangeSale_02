package com.example.orangesale_02.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orangesale_02.R;
import com.example.orangesale_02.bean.ProductLeftBean;
import com.example.orangesale_02.bean.ProductRightBean;

import java.util.List;
import java.util.Random;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class ProductRightAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private List<ProductLeftBean> mLeft;
    private List<ProductRightBean>  mRight;
    private Context context;

    public ProductRightAdapter(List<ProductLeftBean>  mLeft, List<ProductRightBean> mRight,
                               Context context) {
        this.mLeft = mLeft;
        this.mRight = mRight;
        this.context = context;
    }
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setBackgroundColor(Color.WHITE);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(15);
        tv.setText(mRight.get(position).type);
        return tv;
    }

    @Override
    public long getHeaderId(int position) {
        return mRight.get(position).typeId;
    }

    @Override
    public int getCount() {
        return mRight.size();
    }

    @Override
    public Object getItem(int position) {
        return mRight.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Produce_rightViewHolder viewHolder = null;
        if (convertView==null){
            convertView = View.inflate(context, R.layout.productitem_right,null);
            viewHolder = new Produce_rightViewHolder();
            viewHolder.tv_foodname = (TextView) convertView.findViewById(R.id.tv_foodname);
            viewHolder.tv_foodprice = (TextView) convertView.findViewById(R.id.tv_foodprice);
            viewHolder.tv_foodsale = (TextView) convertView.findViewById(R.id.tv_foodsale);
            viewHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (Produce_rightViewHolder) convertView.getTag();
        }
        ProductRightBean rightBean = mRight.get(position);
        viewHolder.tv_foodname.setText(rightBean.foodname);
        viewHolder.iv_img.setImageResource(rightBean.img);

        //使用Random获取随机数
        Random random = new Random();
        int i = random.nextInt(100);
        viewHolder.tv_foodsale.setText("月销量"+i+"份");
        int j = random.nextInt(50);
        viewHolder.tv_foodprice.setText("$"+j);
        return convertView;
    }

    //创建一个viewholder,用来复用对象
    class Produce_rightViewHolder{
        TextView tv_foodname;
        TextView tv_foodprice;
        TextView tv_foodsale;
        ImageView iv_img;
    }
}
