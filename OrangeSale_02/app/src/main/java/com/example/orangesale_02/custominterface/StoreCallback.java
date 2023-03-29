package com.example.orangesale_02.custominterface;

public interface StoreCallback {
    /**
     *  结算按钮的UI
     */
    void allPrice(double totalPrice, int totalCount);

    /**
     *  全选按钮的UI
     */
    void isSelectAll(boolean b);
}
