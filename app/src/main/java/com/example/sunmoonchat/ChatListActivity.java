package com.example.sunmoonchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sunmoonchat.DB.FirebaseDB;
import com.example.sunmoonchat.User.User;
import com.example.sunmoonchat.Utils.Utils;

public class ChatListActivity extends AppCompatActivity {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 안드로이드 상태 바 제거 (전체화면으로 보여줌)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_chat_list);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        if (email != null) {
            String encodeEmail = Utils.emailToBase64(email);

            FirebaseDB.setUserInfo(encodeEmail, new FirebaseDB.OnSetUser() {
                @Override
                public void setUser (User res) { settingUser(res); }
                @Override
                public void setError (String msg) { settingError(msg); }
            });
        } else {
            Utils.viewToast(this, "DB에 E-mail이 없습니다.");
            finish();
        }
    }

    public void clickChatting (View v) {
        Intent intent = new Intent(this, UserListActivity.class);
        intent.putExtra("email", user.email);
        startActivity(intent);
    }

    public void settingUser (User res) {
        user = res;
        TextView tv_nickname = findViewById(R.id.tv_chat_list_nick_name);
        tv_nickname.setText(res.nickname);

        // List View에 추가 해야함...
    }

    public void settingError (String msg) {
        Utils.viewToast(this, msg);
    }
}
