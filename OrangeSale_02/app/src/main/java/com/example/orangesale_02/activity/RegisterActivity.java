package com.example.orangesale_02.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orangesale_02.R;
import com.example.orangesale_02.database.OrangeDatabase;
import com.wheelpicker.AdministrativeMap;
import com.wheelpicker.AdministrativeUtil;
import com.wheelpicker.DataPicker;
import com.wheelpicker.OnCascadeWheelListener;
import com.wheelpicker.OnMultiDataPickListener;
import com.wheelpicker.PickOption;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener{
    private EditText usernameEdit, passwordEdit, surePasswordEdit;
    private TextView cityText;
    private Button regButton;
    private RadioGroup sexGroup;
    private String sexStr = "男";
    private String cityStr = "";
    private AdministrativeMap mAdministrativeMap;
    private List<Integer> mCascadeInitIndex = new ArrayList<Integer>();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    //界面组件初始化
    private void init(){
        cityText = findViewById(R.id.reg_province);
        cityText.setOnClickListener(this);
        usernameEdit = findViewById(R.id.reg_username);
        passwordEdit = findViewById(R.id.reg_password);
        surePasswordEdit = findViewById(R.id.reg_sure_password);
        regButton = findViewById(R.id.reg_register);
        regButton.setOnClickListener(this);
        sexGroup = findViewById(R.id.sex);
        sexGroup.setOnCheckedChangeListener(this);

        mContext = this;//城市选择（级联操作）
//        //设置OnCascadeWheelListener即可满足级联
//        findViewById(R.id.reg_province).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pickCity(AdministrativeUtil.PROVINCE_CITY_AREA, mCascadeInitIndex);
//            }
//        });
    }
    //城市选择方法
    private void pickCity(int mode, final List<Integer> initIndex) {
        if (mAdministrativeMap == null) {
            mAdministrativeMap = AdministrativeUtil.loadCity(RegisterActivity.this);
        }
        PickOption option = getPickDefaultOptionBuilder(mContext)
                .setMiddleTitleText("请选择城市")
                .setFlingAnimFactor(0.4f)
                .setVisibleItemCount(7)
                .setItemTextSize(mContext.getResources()
                        .getDimensionPixelSize(com.wheelpicker.R.dimen.font_24px))
                .setItemLineColor(0xFF558800)
                .build();

        DataPicker.pickData(mContext, initIndex,
                AdministrativeUtil.getPickData(mAdministrativeMap, initIndex, mode), option,
                new OnMultiDataPickListener() {
                    @Override
                    public void onDataPicked(List indexArr, List val, List data) {
//                        String s = indexArr.toString() + ":" + val.toString();
//                        Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
                        cityStr = val.get(0).toString()+ "," +val.get(1).toString()
                                + "," +val.get(2).toString();
                        cityText.setText(cityStr);
                        initIndex.clear();
                        initIndex.addAll(indexArr);
                    }
                }, new OnCascadeWheelListener<List<?>>() {
                    @Override
                    public List<?> onCascade(int wheelIndex, List<Integer> itemIndex) {
                        //级联数据
                        if (wheelIndex == 0) {
                            return mAdministrativeMap.provinces.get(itemIndex.get(0)).city;
                        } else if (wheelIndex == 1) {
                            return mAdministrativeMap.provinces.get(itemIndex.get(0)).city
                                    .get(itemIndex.get(1)).areas;
                        }
                        return null;
                    }
                });
    }
    //城市选择器默认设置
    private PickOption.Builder getPickDefaultOptionBuilder(Context context) {
        return PickOption.getPickDefaultOptionBuilder(context)
                .setLeftTitleColor(0xFF1233DD)
                .setRightTitleColor(0xFF1233DD)
                .setMiddleTitleColor(0xFF333333)
                .setTitleBackground(0XFFDDDDDD)
                .setLeftTitleText("取消")
                .setRightTitleText("确定");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.reg_province://显示城市
                pickCity(AdministrativeUtil.PROVINCE_CITY_AREA, mCascadeInitIndex);
                break;
            case R.id.reg_register:
                validateRegister();//注册验证方法
                break;
        }
    }
    //注册验证
    public void validateRegister(){
        Intent intent = new Intent(RegisterActivity.this, UserActivity.class);
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String surePassword = surePasswordEdit.getText().toString();
        String city = cityText.getText().toString();
        //判断两次密码是否一致
        if (password.equals(surePassword)){
            if (!username.equals("") || !password.equals("")){
                if (!city.equals("")){
                    Bundle bundle = new Bundle();
                    //传送用户信息到个人主页
                    bundle.putString("username", username);
                    bundle.putString("password", password);
                    bundle.putString("sex", sexStr);
                    bundle.putString("city", city);
                    //使用安卓自带的SQlite数据库存储注册的信息
                    OrangeDatabase orangeDatabase = new OrangeDatabase(RegisterActivity.this);
                    SQLiteDatabase sqLiteDatabase = orangeDatabase.getWritableDatabase();
                    insertData(sqLiteDatabase, bundle);
                    intent.putExtras(bundle);
                    startActivity(intent); //跳转到个人主页
                }else{
                    Toast.makeText(RegisterActivity.this,"请选择地址",
                            Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(RegisterActivity.this, "账号或密码未填写",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(RegisterActivity.this, "两次密码输入不一致",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //插入数据的值
    private void insertData(SQLiteDatabase sqLiteDatabase, Bundle bundle){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", bundle.getString("username"));
        contentValues.put("password", bundle.getString("password"));
        contentValues.put("sex", bundle.getString("sex"));
        contentValues.put("city", bundle.getString("city"));
        sqLiteDatabase.insert("orange_user", null, contentValues);
        sqLiteDatabase.close();
    }

    //监听RadioGroup
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        //根据用户选择来改变性别的值
        sexStr = checkedId == R.id.reg_man ? "男" : "女";
    }
}