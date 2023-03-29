package com.example.orangesale_02.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.orangesale_02.R;
import com.example.orangesale_02.bean.ProductLeftBean;
import com.example.orangesale_02.bean.ShouyeGridIcon;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ShouyeGridBaseA extends BaseAdapter {

    private List<ShouyeGridIcon> gridList;

    //创建一个构造方法
    public ShouyeGridBaseA(List<ShouyeGridIcon> mList) {
        this.gridList = mList;
    }

    @Override
    public int getCount() {
        return  gridList.size();
    }

    @Override
    public Object getItem(int position) {
        return gridList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewHolder viewHolder = null;

        if (convertView==null){
            convertView = View.inflate(parent.getContext(), R.layout.shouyeitem_grid_icon,null);
            //创建viewHolder对象
            viewHolder = new GridViewHolder();
            viewHolder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
            viewHolder.txt_icon = (TextView) convertView.findViewById(R.id.txt_icon);

            //让viewholder挂在convertview上面一起复用
            convertView.setTag(viewHolder);
        }else {
            //当convertView不为空时,吧viewholder取出来
            viewHolder = (GridViewHolder) convertView.getTag();
        }
        viewHolder.img_icon.setImageResource(gridList.get(position).getiId());
        viewHolder.txt_icon.setText(gridList.get(position).getiName());

        return convertView;
    }

    //创建一个viewholder,用来复用对象
    class GridViewHolder{
        ImageView img_icon;
        TextView txt_icon;
    }
}
