package com.example.sunmoonchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sunmoonchat.Adapter.ChatListAdapter;
import com.example.sunmoonchat.Chat.Chat;
import com.example.sunmoonchat.DB.FirebaseDB;
import com.example.sunmoonchat.User.User;
import com.example.sunmoonchat.Utils.Utils;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private String emailID;
    private String roomID;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 안드로이드 상태 바 제거 (전체화면으로 보여줌)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        emailID = intent.getStringExtra("emailID");
        roomID = intent.getStringExtra("roomID");

        if ( emailID != null && roomID != null) {
            FirebaseDB.getUserInfo(emailID, new FirebaseDB.OnSetUser() {
                @Override
                public void setUser(User user) {
                    getUser(user);
                }

                @Override
                public void setError(String msg) {
                    settingError(msg);
                }
            });

            FirebaseDB.getChatList(roomID, new FirebaseDB.OnGetChatList() {
                @Override
                public void getChatList(ArrayList<Chat> chats) {
                    setChattingList(chats);
                }

                @Override
                public void setError(String msg) {
                    settingError(msg);
                }
            });
        } else {
            Utils.viewToast(this, "Chatting을 시작할 수 없습니다.");
            finish();
        }
    }

    public void clickSend (View v) {
        String msg = ((EditText) findViewById(R.id.et_chat_message_box)).getText().toString();

        FirebaseDB.sendChat(emailID, roomID, msg, user.nickname);

        ((EditText) findViewById(R.id.et_chat_message_box)).setText("");
    }

    public void getUser (User user) {
        this.user = user;
    }

    public void setChattingList (ArrayList<Chat> chats) {
        ListView lv_chat_list = findViewById(R.id.lv_chat_message_list);

        ChatListAdapter adapter = new ChatListAdapter(this, chats, emailID);
        lv_chat_list.setAdapter(adapter);
    }

    public void settingError (String msg) {Utils.viewToast(this, msg);}

}
