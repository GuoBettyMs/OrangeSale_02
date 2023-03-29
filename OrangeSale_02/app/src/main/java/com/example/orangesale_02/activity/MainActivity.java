package com.example.orangesale_02.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orangesale_02.R;
import com.example.orangesale_02.database.OrangeDatabase;

public class MainActivity extends Activity implements View.OnClickListener{
    private Button registerButton, loginButton;
    private EditText usernameText, paswdEdit;
    public boolean loginIn;
    public static MainActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        OrangeDatabase orangeDatabase = new OrangeDatabase(MainActivity.this);
        orangeDatabase.query(orangeDatabase, "orange_user"); //查询数据库

        instance = this;
//        SQLiteDatabase sqLiteDatabase = orangeDatabase.getWritableDatabase();
//        Cursor c = sqLiteDatabase.query("orange_user", null,
//                null, null,null,
//                null,null);//游标接口，相当于结果集ResultSet
//
//        Log.i("query result: ", String.valueOf(orangeDatabase.list.size()));
//        Log.i("query result: ", String.valueOf(orangeDatabase.list.get(0).getUsername()));
    }
    //界面组件初始化
    private void init(){
        usernameText = findViewById(R.id.username);
        paswdEdit = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(this);
        registerButton = (Button)findViewById(R.id.register);
        registerButton.setOnClickListener(this);

    }
    //登录验证
    private boolean validateLogin(){
        String username = usernameText.getText().toString();
        String password = paswdEdit.getText().toString();
        OrangeDatabase orangeDatabase = new OrangeDatabase(MainActivity.this);
        SQLiteDatabase sqLiteDatabase = orangeDatabase.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from orange_user where " +
                "username = ? and password = ?", new String[]{ username, password});
        if (cursor.getCount() > 0 ){
            return true;
        }
        return false;
    }

    @SuppressLint("Range")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
//                loginIn = false;
//                usernameText.setText("XXXX");
//                Intent intent = new Intent(MainActivity.this, UserActivity.class);
//                Bundle bundle1 = new Bundle();
//                OrangeDatabase orangeDatabase1 = new OrangeDatabase(MainActivity.this);
//                bundle1.putString("username", usernameText.getText().toString());
////                bundle1 = orangeDatabase1.queryUserInfo(
////                        orangeDatabase1.getReadableDatabase(), bundle1);
//                intent.putExtras(bundle1);
//                startActivity(intent);
                break;
            case R.id.login:
                //注册时，引入了数据库，登录可以通过数据库进行验证，验证通过跳转到首页，不通过进行提示
                if (validateLogin()){
                    Intent intent1 = new Intent(MainActivity.this, IndexActivity.class);
                    Bundle bundle = new Bundle();
                    OrangeDatabase orangeDatabase = new OrangeDatabase(MainActivity.this);
                    loginIn = true;
                    //传送用户信息到首页的"个人"
                    bundle.putString("username", usernameText.getText().toString());

                    bundle = orangeDatabase.queryUserInfo(
                            orangeDatabase.getReadableDatabase(), bundle);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                }else{
                    Toast.makeText(MainActivity.this, "账号或者密码错误",
                            Toast.LENGTH_SHORT).show();
                    OrangeDatabase orangeDatabase = new OrangeDatabase(MainActivity.this);
//        orangeDatabase.delete(orangeDatabase, "orange_user", 2);//删除数据
//        orangeDatabase.insert(orangeDatabase,"orange_user", "mike",
//                            "1234", "男", "广东省深圳市福田区");//增加数据
//        orangeDatabase.update(orangeDatabase, "orange_user", 1, "jane",
//                            "6666", "男", "广东省,深圳市,福田区"); //更新数据
                    orangeDatabase.query(orangeDatabase, "orange_user"); //查询数据库
                }
                break;
        }
    }


}