package com.example.orangesale_02.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orangesale_02.CustomLayout.TouchEventLayout;
import com.example.orangesale_02.CustomLayout.DPIUtil;
import com.example.orangesale_02.R;
import com.example.orangesale_02.bean.ChatBean;
import com.example.orangesale_02.custominterface.GoodsCallback;
import com.example.orangesale_02.custominterface.StoreCallback;

import java.util.ArrayList;
import java.util.List;

public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.ShopViewHolder>
        implements GoodsCallback{
    private Context context;
    private GoodCartAdapter goodsAdapter;
    private List<ChatBean> shopslist = new ArrayList<>();//店铺列表
    public List<Integer> shopIdList = new ArrayList<>();//全选列表
    private StoreCallback storesCallback; //商铺回调
    public double totalPrice = 0.00;//商品总价
    public int totalCount = 0;//商品总数量

    //构造方法
    public ShopCartAdapter(Context context, List<ChatBean> shopslist, StoreCallback storesCallback){
        this.context = context;
        this.shopslist = shopslist;
        this.storesCallback = storesCallback;
    }

    //返回我们的内部类MyViewHolder
    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cartitem_store, parent, false);

        // 初始化转换工具
        DPIUtil.setDensity(context.getResources().getDisplayMetrics().density);
        // 左滑删除容器LeftSlideView 的父级 TouchEventLayout，监听父级的触摸事件
        View root = view.findViewById(R.id.root);
        if (root instanceof TouchEventLayout) {
            TouchEventLayout ll = (TouchEventLayout) root;
            ll.setOnTouchListener(new TouchEventLayout.OnTouchListener() {
                @Override
                public void doTouch(Point point) {
                    if (goodsAdapter != null) {
                        goodsAdapter.restoreItemView(point);//还原itemView
                    }
                }
            });
        }
        return new ShopViewHolder(view);
    }

    //对每一项的TextView进行赋值，取出实体的某一属性进行显示，position为下标。
    @Override
    public void onBindViewHolder(ShopViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ChatBean shop = shopslist.get(position);

        //绑定商品适配器
        holder.rv_goods.setLayoutManager(new LinearLayoutManager(context));
        goodsAdapter = new GoodCartAdapter(context, shop.getgoodsList(),
                this,holder.rv_goods);
        holder.rv_goods.setAdapter(goodsAdapter);

        goodsAdapter.item = shop;//将shop传递给商品的item，用于判断商品是否已全选，是否选中店铺

        //商店名数据填充
        holder.tv_store_name.setText(shop.getShopName());

        //店铺初始状态是否勾选
        if(shop.getShopSelect()){
            holder.iv_checked_store.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_checked));
        }else {
            holder.iv_checked_store.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check));
        }

        //shop选中的点击事件
        holder.iv_checked_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当前商铺勾选状态取反
                shop.setShopSelect(!shop.getShopSelect());
                notifyDataSetChanged();

                //全选事件--当一个一个选中店铺时，全选按钮为true
                if (shop.getShopSelect()){
                    selectAllGoods(shop.getShopId(), true);//该商铺下所有商品勾选状态同商铺一样

                    //该店铺已被选中，但全选列表中没有该店铺id
                    if(!shopIdList.contains(shop.getShopId())){
                        shopIdList.add(shop.getShopId());//添加到店铺列表
                    }
                }else{
                    selectAllGoods(shop.getShopId(), false);//该商铺下所有商品勾选状态同商铺一样
                    //该店铺未被选中，全选列表移除该店铺id
                    if(shopIdList.contains(shop.getShopId())){
                        shopIdList.remove((Integer) shop.getShopId());
                    }
                }
                controllAllChecked(); //传递全选按钮的状态到主页
            }
        });

    }

    //获得实体list的长度，即显示项的个数
    @Override
    public int getItemCount() {
        return shopslist == null?0:shopslist.size();
    }

    class ShopViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rv_goods;
        private TextView tv_store_name;
        private ImageView iv_checked_store;//店铺是否被选中

        //变量绑定layout
        public ShopViewHolder(View itemView) {
            super(itemView);
            rv_goods = itemView.findViewById(R.id.rv_goods);
            tv_store_name = (TextView) itemView.findViewById(R.id.tv_store_name);
            iv_checked_store = (ImageView) itemView.findViewById(R.id.iv_checked_store);
        }
    }

    /****************************自定义方法*************************************************/
    /**
     * 传递全选按钮的状态到主页
     */
    public void controllAllChecked() {
        if(shopIdList.size() == shopslist.size() && shopslist.size() != 0){
            //全选
            storesCallback.isSelectAll(true);
        }else {
            //不全选
            storesCallback.isSelectAll(false);
        }
        calculationPrice();
    }

    /**
     * shop点击事件--选中店铺，该店铺下所有商品选中
     */
    public void selectAllGoods(int shopId, boolean state) {
        //根据店铺id选中该店铺下所有商品
        for (ChatBean shop : shopslist) {
            //店铺id等于传递过来的店铺id  则选中该店铺下所有商品
            if (shop.getShopId() == shopId) {
                for (ChatBean.ChatItem good : shop.getgoodsList()) {
                    good.setGoodChecked(state);
                    //刷新
                    notifyDataSetChanged();
                }
            }
        }
    }

    /*************************** 回调 *******************************/
    /**
     * GoodsCallback 回调
     * 商品价格
     */
    @Override
    public void calculationPrice(){
        //每次计算之前先置零
        totalPrice = 0.00;
        totalCount = 0;
        //循环购物车中的店铺列表
        for (int i = 0; i < shopslist.size(); i++) {
            ChatBean shop = shopslist.get(i);
            //循环店铺中的商品列表
            for (int j = 0; j < shop.getgoodsList().size(); j++) {
                ChatBean.ChatItem good = shop.getgoodsList().get(j);
                //当有选中商品时计算数量和价格
                if (good.getGoodChecked()) {
                    totalCount++;
                    totalPrice += good.getGoodsPrice() * good.getGoodsNum();
                }
            }
        }
        storesCallback.allPrice(totalPrice,totalCount);//通过StoreCallback 设置首页的总价格
    }

    /**
     * GoodsCallback 回调
     * 1.某店铺以下的所有商品被选中时，店铺被选中（GoodsCallback 控制店铺按钮的状态）
     * 2.一个一个的选中商品，商品对应的店铺被选中，达成全选（传递全选按钮的状态）
     */
    @Override
    public void selectAllStore(int shopId, boolean state) {
        for (ChatBean shop : shopslist) {
            if(shopId == shop.getShopId()){
                //GoodsCallback 回调 -- 商品选中状态决定店铺选中状态
                //遍历，在 shopslist 找到需要设置的店铺，设置为选中状态
                shop.setShopSelect(state);
                notifyDataSetChanged();

                //StoreCallback 回调 -- 店铺选中状态决定全选按钮状态
                //单击商品,记录选中店铺的shopid,添加到全选列表中
                if (!shopIdList.contains(shop.getShopId()) && state) {
                    //如果列表中没有这个店铺Id且当前店铺为选中状态
                    shopIdList.add(shop.getShopId());
                }else {
                    if(shopIdList.contains(shop.getShopId())){
                        //通过list.indexOf获取属性的在列表中的下标，不过强转Integer更简洁
                        shopIdList.remove((Integer) shop.getShopId());
                    }
                }
            }
        }
        controllAllChecked();//传递全选按钮的状态到主页
    }

    /** GoodsCallback 回调
     *  商品选中时的删除事件
     * **/
    @Override
    public void deleteCheckedGoods() {
        //店铺列表
        List<ChatBean> storeList = new ArrayList<>();
        for (int i = 0; i < shopslist.size(); i++) {
            ChatBean store = shopslist.get(i);
            if (store.getShopSelect()) {
                //店铺如果选择则添加到此列表中
                storeList.add(store);
            }
            //商品列表
            List<ChatBean.ChatItem> newgoodsList = new ArrayList<>();
            List<ChatBean.ChatItem> goodsList = store.getgoodsList();
            //循环店铺中的商品列表
            for (int j = 0; j < goodsList.size(); j++) {
                ChatBean.ChatItem good = goodsList.get(j);
                //当有选中商品时添加到此列表中
                if (good.getGoodChecked()) {
                    newgoodsList.add(good);
                }
            }
            //删除商品
            goodsList.removeAll(newgoodsList);
        }

        shopslist.removeAll(storeList);//删除店铺
        shopIdList.clear();//删除了选中商品，清空已选择的标识
        controllAllChecked();////传递全选按钮的状态到主页
        //刷新数据
        notifyDataSetChanged();
    }

    /** GoodsCallback 回调
     * 商品未选中时的删除事件
     * **/
    @Override
    public void deleteUncheckedGoods(int shopId) {
        List<ChatBean> storeList = new ArrayList<>();
        for (int i = 0; i < shopslist.size(); i++){
            ChatBean shop = shopslist.get(i);
                if (shopId == shop.getShopId()) {
//                    Log.i("data - getShopId", String.valueOf(shopId));
                    storeList.add(shop);
                }
        }
        shopslist.removeAll(storeList);//删除店铺
        storesCallback.allPrice(totalPrice,totalCount);
        notifyDataSetChanged();
    }
}

