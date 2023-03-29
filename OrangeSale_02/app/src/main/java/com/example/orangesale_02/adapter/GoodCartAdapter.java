package com.example.orangesale_02.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orangesale_02.CustomLayout.LeftSlideView;
import com.example.orangesale_02.R;
import com.example.orangesale_02.bean.ChatBean;
import com.example.orangesale_02.custominterface.GoodsCallback;
import com.example.orangesale_02.custominterface.OnDeleteClickListener;
import com.example.orangesale_02.custominterface.OnEditClickListener;

import java.util.ArrayList;
import java.util.List;

public class GoodCartAdapter extends RecyclerView.Adapter<GoodCartAdapter.GoodViewHolder>{

    private Context context;
    List<ChatBean.ChatItem> data = new ArrayList<>();
    private OnEditClickListener mOnEditClickListener;
    private OnDeleteClickListener mOnDeleteClickListener;
    private GoodsCallback goodsCallback; //商品回调
    ChatBean item;//从StoreCallback获取店铺数据

    private LeftSlideView mLeftSlideView;
    //为了防止垂直方向的同向冲突，那么需要将外层的RecyclerView传入左滑容器，在容器中会处理滑动冲突
    private RecyclerView mRecyclerView;
    View contentView;
    View menuView;

    //构造方法
    public GoodCartAdapter(Context context, List<ChatBean.ChatItem> data,
                           GoodsCallback goodsCallback, RecyclerView recyclerView){
        this.context = context;
        this.data = data;
        this.goodsCallback = goodsCallback;
        this.mRecyclerView = recyclerView;
    }

    //返回内部类MyViewHolder
    @Override
    public GoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem_good, parent, false);
//        return new GoodViewHolder(view);

        //左滑删除容器 LeftSlideView
        final LeftSlideView leftSlideView = new LeftSlideView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        leftSlideView.setLayoutParams(params);
        contentView = LayoutInflater.from(context).inflate(R.layout.cartitem_good, null);
        menuView = LayoutInflater.from(context).inflate(R.layout.cartitem_good_menuview, null);
        leftSlideView.addContentView(contentView);
        leftSlideView.addMenuView(menuView);
        leftSlideView.setRecyclerView(mRecyclerView);

        leftSlideView.setStatusChangeLister(new LeftSlideView.OnDelViewStatusChangeLister() {
            @Override
            public void onStatusChange(boolean show) {
                if (show) {
                    // 如果编辑菜单在显示
                    mLeftSlideView = leftSlideView;
                }
            }
        });
        return new GoodViewHolder(leftSlideView);
    }

    //对每一项的TextView进行赋值，取出实体的某一属性进行显示，position为下标。
    @Override
    public void onBindViewHolder(GoodViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final ChatBean.ChatItem good = data.get(position);

        Glide.with(context).load(good.getGoodsImg()).into(holder.iv_goodsImage);
        holder.tv_goodsName.setText(good.getGoodsname());
        holder.tv_goodsDescription.setText("颜色：" + good.getColor()+"  "+"尺寸：" + good.getSize());
        holder.tv_goodsPrice.setText("¥" + good.getGoodsPrice());
        holder.tv_goodsNum.setText(good.getGoodsNum()+ "");

        //商品初始状态是否勾选
        if(good.getGoodChecked()){
            holder.iv_goodsSelect.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_checked));
        }else {
            holder.iv_goodsSelect.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check));
        }
        //商品item中的点击事件
        holder.iv_goodsSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                good.setGoodChecked(!good.getGoodChecked());
                notifyDataSetChanged();
                controlStore(item); //该店铺如果所有商品都选中，则店铺自动选中
                goodsCallback.calculationPrice();//计算商品价格
            }
        });
        //给每个item->GoodsMin绑定点击事件，数量的改变
        holder.iv_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).getGoodsNum() > 1) {
                    int count = data.get(position).getGoodsNum() - 1;
                    if (mOnEditClickListener != null) {
                        mOnEditClickListener.onEditClick(position, data.get(position).getProductId(), count);
                    }
                    data.get(position).setGoodsNum(count);
                    notifyDataSetChanged();
                }
            }
        });
        //给每个item->GoodsAdd绑定点击事件，数量的改变
        holder.iv_goodsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = data.get(position).getGoodsNum() + 1;
                if(mOnEditClickListener != null){
                    mOnEditClickListener.onEditClick(position,data.get(position).getProductId(),count);
                }
                data.get(position).setGoodsNum(count);
                notifyDataSetChanged();
            }
        });

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "点击内容区域", Toast.LENGTH_SHORT).show();
            }
        });
        //删除的点击事件
        menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "点击删除按钮", Toast.LENGTH_SHORT).show();
                if(good.getGoodChecked()){
                    goodsCallback.deleteCheckedGoods();//商品选中时
                }else{
                    showDialog(view,position);//商品未选中时
                }
            }
        });

    }

    //获得实体list的长度，即显示项的个数
    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    static class GoodViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_goodsName,tv_goodsDescription,tv_goodsPrice,tv_goodsNum;
        private ImageView iv_goodsImage,iv_mine,iv_goodsAdd,iv_goodsSelect;

        //变量绑定layout
        public GoodViewHolder(View itemView) {
            super(itemView);
            tv_goodsName = (TextView) itemView.findViewById(R.id.tv_goodsName);
            tv_goodsDescription = (TextView) itemView.findViewById(R.id.tv_goodsDescription);
            tv_goodsPrice = (TextView) itemView.findViewById(R.id.tv_goodsPrice);
            tv_goodsNum = (TextView) itemView.findViewById(R.id.tv_goodsNum);
            iv_goodsImage = (ImageView) itemView.findViewById(R.id.iv_goodsImage);
            iv_goodsSelect = (ImageView) itemView.findViewById(R.id.iv_goodsSelect);
            iv_mine = (ImageView) itemView.findViewById(R.id.iv_mine);
            iv_goodsAdd = (ImageView) itemView.findViewById(R.id.iv_goodsAdd);
        }
    }

    /*********************************自定义方法****************************************************/
    /**
     * 还原itemView
     * @param point
     */
    public void restoreItemView(Point point) {
        if (mLeftSlideView != null) {
            int[] pos = new int[2];
            mLeftSlideView.getLocationInWindow(pos);

            int width = mLeftSlideView.getWidth();
            int height = mLeftSlideView.getHeight();

            // 触摸点在view的区域内，那么直接返回
            if (point.x >= pos[0] && point.y >= pos[1]
                    && point.x <= pos[0] + width && point.y <= pos[1] + height) {
                return;
            }
            mLeftSlideView.resetDelStatus();//重置
        }
    }

    /**
     * 若该店铺下所有商品都选中，则店铺自动选中
     * 记录选中商品数量，遍历完成之后，判断选中总数量是否等于店铺拥有商品数量，相等则店铺自动选中，否则店铺不选中
     */
    public void controlStore(ChatBean item) {
        int num = 0;
        for (ChatBean.ChatItem bean : item.getgoodsList()) {
            if (bean.getGoodChecked()) {
                ++num;
            }
        }
        if (num == item.getgoodsList().size()) {
            //商品全选中，ShopCartAdapter需要设置店铺为选中状态
            //通过回调接口 goodsCallback 向ShopCartAdapter 传递需要设置的店铺id和店铺状态设置值
            goodsCallback.selectAllStore(item.getShopId(),true);
        } else {
            goodsCallback.selectAllStore(item.getShopId(),false);
        }
    }

    //每个item删除的点击事件
    private void showDialog(final View view, final int position){
        //调用删除某个规格商品的接口
        if(mOnDeleteClickListener != null){
            mOnDeleteClickListener.onDeleteClick(
                    view,position,data.get(position).getProductId());//找到商品id
        }
        data.remove(position);
        //刷新数据
        notifyDataSetChanged();

        if (data.size() == 0){
            goodsCallback.deleteUncheckedGoods(item.getShopId());
        }
    }

}
