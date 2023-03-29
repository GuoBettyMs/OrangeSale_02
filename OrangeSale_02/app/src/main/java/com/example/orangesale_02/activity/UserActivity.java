package com.example.orangesale_02.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orangesale_02.R;
import com.example.orangesale_02.database.OrangeDatabase;

public class UserActivity extends Activity implements View.OnClickListener{

    Button backMainButton,loginoutButton;
    TextView user_username;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //从注册活动页的textView 获取用户信息
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        username = bundle.getString("username");
        String sex = bundle.getString("sex");
        String city = bundle.getString("city");

        user_username = findViewById(R.id.user_username);
        user_username.setText("用户名："+username);
        TextView user_sex = findViewById(R.id.user_sex);
        user_sex.setText("性别："+sex);
        TextView user_city = findViewById(R.id.user_city);
        user_city.setText("城市："+city);

        backMainButton = (Button)findViewById(R.id.backMain);
        backMainButton.setOnClickListener(this);
        loginoutButton = (Button)findViewById(R.id.loginout);
        loginoutButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backMain:
//                startActivity(new Intent(this, IndexActivity.class));
                Intent intent = new Intent(UserActivity.this, IndexActivity.class);
                Bundle bundle1 = new Bundle();
                OrangeDatabase orangeDatabase1 = new OrangeDatabase(UserActivity.this);
                //传送用户信息到首页的"个人"
                bundle1.putString("username", username);
                bundle1 = orangeDatabase1.queryUserInfo(
                        orangeDatabase1.getReadableDatabase(), bundle1);
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.loginout:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}