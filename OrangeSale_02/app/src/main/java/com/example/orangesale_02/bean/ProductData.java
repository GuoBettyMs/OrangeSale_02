package com.example.orangesale_02.bean;

import com.example.orangesale_02.R;

import java.util.ArrayList;
import java.util.List;

public class ProductData {
    private static int[] imgs =
            new int[]{R.drawable.pig,R.drawable.chicken,R.drawable.turkey,R.drawable.chicken_leg,
                    R.drawable.bowl_one,R.drawable.chili,R.drawable.shrimp,R.drawable.fish};
    private static String[] leftData =
            new String[]{"13.9特价套餐","粗粮主食🍚","佐餐小吃🍟","用心营养套餐(不含主食)🍜",
                    "三杯鸡双拼尊享套餐🐔","带鱼双拼尊享套餐🐟","红烧肉双拼尊享套餐🐷"};
    private static String[] rightData0 =
            new String[]{"洋芋粉炒腊肉","土鸡炖香菇","新疆大盘辣子土鸡","清炖土鸡块",
                    "农家蒸碗 ","香辣野猪肉","香辣薯条大虾","麻辣鱼"};
    private static String[] rightData1 =
            new String[]{"商芝扣肉","羊肉萝卜","干烧鱼 ","干煸野猪肉 ","排骨火锅",
                    "土鸡火锅","牛肉火锅","狗肉火锅 "};
    private static String[] rightData2 =
            new String[]{"虎皮辣子炒咸肉","重庆飘香水煮鱼","红烧土鸡块","干煸辣子土鸡",
                    "清炖全鸡 "};
    private static String[] rightData3 =
            new String[]{"洋芋粉炒腊肉","土鸡炖香菇","新疆大盘辣子土鸡","清炖土鸡块",
                    "农家蒸碗 ","香辣野猪肉","香辣薯条大虾","麻辣猪血"};
    private static String[] rightData4 =
            new String[]{"洋芋粉炒腊肉","土鸡炖香菇","新疆大盘辣子土鸡","清炖土鸡块",
                    "农家蒸碗 ","香辣野猪肉","香辣薯条大虾","麻辣猪血"};
    private static String[] rightData5 =
            new String[]{"洋芋粉炒腊肉","土鸡炖香菇","新疆大盘辣子土鸡","清炖土鸡块",
                    "农家蒸碗 ","香辣野猪肉","香辣薯条大虾","麻辣猪血"};
    private static String[] rightData6 =
            new String[]{"洋芋粉炒腊肉","土鸡炖香菇","新疆大盘辣子土鸡","清炖土鸡块",
                    "农家蒸碗 ","香辣野猪肉","香辣薯条大虾","麻辣猪血"};

    public static List<ProductLeftBean> getLeftData(){
        List<ProductLeftBean> list = new ArrayList<ProductLeftBean>();
        for (int i = 0; i < leftData.length; i++) {
            ProductLeftBean bean = new ProductLeftBean();
            bean.title = leftData[i];
            bean.type = i;
            list.add(bean);
        }
        return list;
    }
    public static List<ProductRightBean> getRightData(List<ProductLeftBean> list){
        List<ProductRightBean>rightList = new ArrayList<ProductRightBean>();
        for (int i = 0; i < list.size(); i++) {
            ProductLeftBean leftBean = list.get(i);
            int mType = leftBean.type;
            switch (mType) {
                case 0:
                    rightList = getRightList(rightData0, leftBean, mType, rightList);
                    break;
                case 1:
                    rightList = getRightList(rightData1, leftBean, mType, rightList);
                    break;
                case 2:
                    rightList = getRightList(rightData2, leftBean, mType, rightList);
                    break;
                case 3:
                    rightList = getRightList(rightData3, leftBean, mType, rightList);
                    break;
                case 4:
                    rightList = getRightList(rightData4, leftBean, mType, rightList);
                    break;
                case 5:
                    rightList = getRightList(rightData5, leftBean, mType, rightList);
                    break;
                case 6:
                    rightList = getRightList(rightData6, leftBean, mType, rightList);
                    break;
            }
        }
        return rightList;
    }
    private static List<ProductRightBean> getRightList(
            String[] arr, ProductLeftBean leftBean,int mType, List<ProductRightBean> rightList){
        for (int j = 0; j < arr.length; j++) {
            ProductRightBean bean = new ProductRightBean();
            bean.type = leftBean.title;
            bean.foodname = arr[j];
            bean.typeId = mType;
            bean.img = imgs[j];
            rightList.add(bean);
        }
        return rightList;
    }
}