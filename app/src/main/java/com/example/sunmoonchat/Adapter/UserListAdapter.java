package com.example.sunmoonchat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.sunmoonchat.ChatActivity;
import com.example.sunmoonchat.DB.FirebaseDB;
import com.example.sunmoonchat.R;
import com.example.sunmoonchat.User.User;
import com.example.sunmoonchat.Utils.Utils;

import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter {
    private ArrayList<User> users = new ArrayList<>();
    private String base64Email;
    private LayoutInflater inflater;
    private Context context;

    public UserListAdapter () {}

    public UserListAdapter (Context context, ArrayList<User> users, String myEmail) {
        this.users = users;
        this.context = context;
        this.base64Email = Utils.toBase64(myEmail);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount () {
        return users.size();
    }

    @Override
    public Object getItem (int position) {
        return users.get(position);
    }

    @Override
    public long getItemId (int position) {
        return position;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater
                    .inflate(R.layout.item_user_list_view, parent,false);
        }

        TextView nickname = convertView.findViewById(R.id.tv_user_list_nickname);
        TextView name = convertView.findViewById(R.id.tv_user_list_name);
        TextView email = convertView.findViewById(R.id.tv_user_list_email);
        Button btn_chat = convertView.findViewById(R.id.btn_user_list_chat);

        nickname.setText(users.get(position).nickname);
        name.setText(users.get(position).name);
        email.setText(users.get(position).email);

        btn_chat.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStartChat(users.get(position).email);
            }
        });

        return convertView;
    }

    public void clickStartChat (String receiver) {
        String receiverID = Utils.toBase64(receiver);
        String roomID = Utils.toBase64(base64Email + "/" + receiverID);

        FirebaseDB.createdChat(base64Email, receiverID);

        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("emailID", base64Email);
        intent.putExtra("roomID", roomID);
        context.startActivity(intent);
    }
}
