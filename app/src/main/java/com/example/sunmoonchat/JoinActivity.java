package com.example.sunmoonchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sunmoonchat.firebasehelper.FB_Helper;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 안드로이드 상태 바 제거 (전체화면으로 보여줌)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                 WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_join);
    }

    public void clickSignUp (View view) {
        String email = ((EditText) findViewById(R.id.et_join_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.et_join_password)).getText().toString();
        String userName = ((EditText) findViewById(R.id.et_join_user_name)).getText().toString();
        String userNickName = ((EditText) findViewById(R.id.et_join_nick_name))
                .getText().toString();

        if (email.equals("")) {
            Toast.makeText(this, "E-mail Required", Toast.LENGTH_LONG).show();
            findViewById(R.id.et_join_email).requestFocus();
            return;
        }

        if (password.equals("")) {
            Toast.makeText(this, "Password is Required", Toast.LENGTH_LONG).show();
            findViewById(R.id.et_join_password).requestFocus();
            return;
        }

        if (userName.equals("")) {
            Toast.makeText(this, "UserName is Required", Toast.LENGTH_LONG).show();
            findViewById(R.id.et_join_user_name).requestFocus();
            return;
        }

        if (userNickName.equals("")) {
            Toast.makeText(this, "NickName is Required", Toast.LENGTH_LONG).show();
            findViewById(R.id.et_join_nick_name).requestFocus();
            return;
        }

        FB_Helper helper = new FB_Helper(this);
        helper.join(email, password);

        if (helper.getUser() != null) {
            Intent intent = new Intent();
            startActivity(intent);
        }
    }

    public void clickCancel (View view) {
        finish();
    }
}
