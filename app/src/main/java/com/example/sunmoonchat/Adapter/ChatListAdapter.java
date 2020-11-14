package com.example.sunmoonchat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sunmoonchat.Chat.Chat;
import com.example.sunmoonchat.R;

import java.util.ArrayList;

public class ChatListAdapter extends BaseAdapter {
    private ArrayList<Chat> chats = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    private String myEmail;

    public ChatListAdapter () {}

    public ChatListAdapter (Context context, ArrayList<Chat> chats, String myEmail) {
        this.chats = chats;
        this.context = context;
        this.myEmail = myEmail;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return chats.size(); }

    @Override
    public Object getItem(int position) { return chats.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Chat chat = chats.get(position);

        TextView tvNick, tvMsg, tvTime;

        if (chat.getEmail().equals(myEmail)) {
            convertView = inflater
                    .inflate(R.layout.item_my_message, parent, false);
            tvNick = convertView.findViewById(R.id.tv_chat_item_my_nickname);
            tvMsg = convertView.findViewById(R.id.tv_chat_item_my_message);
            tvTime = convertView.findViewById(R.id.tv_chat_item_my_time);
        } else {
            convertView = inflater
                    .inflate(R.layout.item_other_message, parent, false);
            tvNick = convertView.findViewById(R.id.tv_chat_item_other_nickname);
            tvMsg = convertView.findViewById(R.id.tv_chat_item_other_message);
            tvTime = convertView.findViewById(R.id.tv_chat_item_other_time);
        }

        tvNick.setText(chat.getNickName());
        tvMsg.setText(chat.getMsg());
        tvTime.setText(chat.getCreatedAt());

        return convertView;
    }
}
