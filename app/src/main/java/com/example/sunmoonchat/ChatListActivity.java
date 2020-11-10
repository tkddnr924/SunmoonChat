package com.example.sunmoonchat;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sunmoonchat.DB.FirebaseDB;
import com.example.sunmoonchat.User.User;
import com.example.sunmoonchat.Utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChatListActivity extends AppCompatActivity {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 안드로이드 상태 바 제거 (전체화면으로 보여줌)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_chat_list);

        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

        if (fbUser != null) {
            String Email = fbUser.getEmail();
            if (Email != null) {
                String encodeEmail = Utils.emailToBase64(Email);

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
        } else {
            Utils.viewToast(this, "DB Error");
            finish();
        }
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
