package com.example.orangesale_02.CustomLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过代码创建的布局（如View，LinearLayout）等实现ListView效果
 * 调用
 * PriceBoard priceBoard = new PriceBoard(context,null);
 * priceData = new PriceData();
 * priceData.setName("现货白银");
 * priceData.setEnCode("Ag");
 * priceData.setPrice(4006);
 * priceBoard.add(priceData);
 * priceData = new PriceData();
 * priceData.setName("现货铜");
 * priceData.setEnCode("Cu");
 * priceData.setPrice(43895);
 * priceBoard.add(priceData);
 * priceData = new PriceData();
 * priceData.setName("现货镍");
 * priceData.setEnCode("Ni");
 * priceData.setPrice(43895);
 * priceBoard.add(priceData);
 * addView(priceBoard);
 */

public class PriceBoard extends LinearLayout {
    private ListView listView;
    private List items;
    private LinearLayout.LayoutParams params;

    public PriceBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        items = new ArrayList();
        this.setOrientation(HORIZONTAL);
        params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        listView = new ListView(context);
        listView.setLayoutParams(params);
        PriceBoardAdapter priceBoardAdapter = new PriceBoardAdapter(context);
        listView.setAdapter(priceBoardAdapter);
        addView(listView, params);
    }

    public static class PriceData {
        private String name;
        private String encode;
        private int price;

        public String getName() {
            return name;
        }
        public void setName(String name){
            this.name = name;
        }

        public String getEnCode() {
            return encode;
        }
        public void setEnCode(String getEnCode){
            this.encode = encode;
        }

        public int getPrice() {
            return price;
        }
        public void setPrice(int price){
            this.price = price;
        }
    }

    public void add(PriceData data) {
        PriceBoardItem item = new PriceBoardItem(this.getContext(), null);
        item.setItem(data);
        items.add(item);
        params.setMargins(10, 0, 0, 2);
//    item.setLayoutParams(params);
    }

    public PriceBoardItem getItemView(int index) {
        return (PriceBoardItem) items.get(index);
    }

    private class PriceBoardItem extends LinearLayout {
        private TextView nameView;
        private TextView enCodeView;
        private TextView priceView;
        private PriceData priceData;

        public PriceBoardItem(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.setOrientation(HORIZONTAL);
            nameView = new TextView(context);
            nameView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 38);
            enCodeView = new TextView(context);
            enCodeView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 28);
            priceView = new TextView(context);
            priceView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 48);
            priceView.setGravity(Gravity.CENTER);
            setLayout();
        }

        public TextView getNameView() {
            return nameView;
        }

        public TextView getEnCodeView() {
            return enCodeView;
        }

        public TextView getPriceView() {
            return priceView;
        }

        public PriceData getPriceData() {
            return priceData;
        }

        private void setLayout() {
            LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(VERTICAL);
            linearLayout.addView(nameView, p);
            linearLayout.addView(enCodeView, p);
            addView(linearLayout, p);

            p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(VERTICAL);
            linearLayout.addView(priceView, p);
            addView(linearLayout, p);
        }

        public void setItem(PriceData data) {
            priceData = data;
        }
    }

    private class PriceBoardAdapter extends BaseAdapter {
        private Context _context;

        public PriceBoardAdapter(Context context) {
            _context = context;
        }

        public int getCount() {
            return items.size();
        }

        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PriceBoardItem view = getItemView(position);
            PriceData data = ((PriceBoardItem) items.get(position)).getPriceData();
            view.getNameView().setText(data.getName());
            view.getEnCodeView().setText(data.getEnCode());
            view.getPriceView().setText(String.valueOf(data.getPrice()));
            convertView = view;
            return convertView;
        }
    }

}