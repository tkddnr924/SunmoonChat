package com.example.sunmoonchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sunmoonchat.Adapter.UserListAdapter;
import com.example.sunmoonchat.DB.FirebaseDB;
import com.example.sunmoonchat.User.User;
import com.example.sunmoonchat.Utils.Utils;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {
    public String email;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 안드로이드 상태 바 제거 (전체화면으로 보여줌)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_user_list);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        if (email != null) {
            String encodeEmail = Utils.emailToBase64(email);

            FirebaseDB.getUserList(new FirebaseDB.OnGetUserList() {
                @Override
                public void getUserList(ArrayList<User> users) { settingUsers(users); }

                @Override
                public void setError(String msg) { settingError(msg); }
            });
        } else {
            Utils.viewToast(this, "DB에 E-mail이 없습니다.");
            finish();
        }
    }

    public void settingUsers(ArrayList<User> users) {
        this.users = users;

        ListView lv_user_list = findViewById(R.id.lv_user_list);

        UserListAdapter adapter = new UserListAdapter(this, users);
        lv_user_list.setAdapter(adapter);
    }

    public void settingError(String msg) {
        Utils.viewToast(this, msg);
    }

}
