package com.example.orangesale_02.custominterface;

import android.view.View;

import com.example.orangesale_02.bean.ChatBean;

import java.util.List;

/**
 * 商品回调接口
 * @author llw
 */
public interface GoodsCallback {
    /**
     * 计算商品价格
     */
    void calculationPrice();

    /**
     * 根据商品的选中数量，确定是否选中店铺
     */
    void selectAllStore(int shopId, boolean state);

    /**
     * 商品选中时的删除事件
     */
    void deleteCheckedGoods();

    /**
     * 商品未选中时的删除事件
     */
    void deleteUncheckedGoods(int shopId);
}
