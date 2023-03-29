package com.example.orangesale_02.bean;

import java.util.List;

//店铺类
public class ChatBean {
    private String shopName;//店铺名
    private boolean shopChecked;
    private int shopId;
    private List<ChatItem> goodsList;

    public ChatBean(String shopname, int shopId,List<ChatItem> goodsList) {
        this.shopName = shopname;
        this.shopId = shopId;
        this.goodsList = goodsList;
    }

    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<ChatItem> getgoodsList() {
        return goodsList;
    }
    public void setgoodsList(List<ChatItem> goodsList) {
        this.goodsList = goodsList;
    }

    public boolean getShopSelect(){
        return shopChecked;
    }
    public void setShopSelect(boolean shopChecked) {
        this.shopChecked = shopChecked;
    }

    public int getShopId() {
        return shopId;
    }
    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    //商品类
    public static class ChatItem {

        private String goodsname;//商品
        private int goodsImg;
        private int goodsPrice;
        private int goodsNum;
        private String defaultPic;
        private boolean goodChecked;
        private boolean shopChecked;
        private int shopId;
        private String color;
        private String shopName;
        private int productId;
        private int index;
        private String size;

        public ChatItem(String goodsname, int goodsImg, String color,String size,
                        int goodsPrice,int goodsNum) {
            this.goodsname = goodsname;
            this.goodsImg = goodsImg;
            this.color = color;
            this.size = size;
            this.goodsPrice = goodsPrice;
            this.goodsNum = goodsNum;
        }

        public String getGoodsname() {
            return goodsname;
        }
        public void setGoodsname(String goodsname) {
            this.goodsname = goodsname;
        }

        public String getColor() {
            return color;
        }
        public void setColor(String color) {
            this.color = color;
        }

        public String getSize() {
            return size;
        }
        public void setSize(String size) {
            this.size = size;
        }

        public int getGoodsPrice() {
            return goodsPrice;
        }
        public void setGoodsPrice(int goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public int getGoodsNum() {
            return goodsNum;
        }
        public void setGoodsNum(int count) {
            this.goodsNum = count;
        }

        //本地图片
        public int getGoodsImg() {return goodsImg;}
        public void setGoodsImg(int goodsImg) {
            this.goodsImg = goodsImg;
        }

        //网络图片
//        public String getDefaultPic() {
//            return defaultPic;
//        }
//        public void setDefaultPic(String defaultPic) {
//            this.defaultPic = defaultPic;
//        }
        public boolean getGoodChecked() {
            return goodChecked;
        }
        public void setGoodChecked(boolean goodChecked) {
            this.goodChecked = goodChecked;
        }

        public int getIsFirst() {
            return index;
        }
        public void setIsFirst(int index) {
            this.index = index;
        }

        public int getGoodToShopId() {
            return shopId;
        }
        public void setGoodToShopId(int shopId) {
            this.shopId = shopId;
        }

        public boolean getIsShopSelect(){
            return shopChecked;
        }
        public void setShopSelect(boolean shopChecked) {
            this.shopChecked = shopChecked;
        }

        public int getProductId() {
            return productId;
        }
        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getShopName() {
            return shopName;
        }
        public void setShopName(String shopName) {
            this.shopName = shopName;
        }


    }

}

