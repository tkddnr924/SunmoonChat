package com.example.sunmoonchat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.sunmoonchat.Chat.ChattingRoom;
import com.example.sunmoonchat.ChatActivity;
import com.example.sunmoonchat.DB.FirebaseDB;
import com.example.sunmoonchat.R;
import com.example.sunmoonchat.User.User;
import com.example.sunmoonchat.Utils.Utils;

import java.util.ArrayList;

public class RoomListAdapter extends BaseAdapter {
    private ArrayList<ChattingRoom> rooms = new ArrayList<>();
    private User user;
    private LayoutInflater inflater;
    private Context context;
    private Boolean isYour = false;

    public RoomListAdapter () {}

    public RoomListAdapter (Context context, ArrayList<ChattingRoom> rooms, User user) {
        this.context = context;
        this.rooms = rooms;
        this.user = user;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) { return rooms.get(position); }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChattingRoom room = rooms.get(position);

        if (convertView == null) {
            convertView = inflater
                    .inflate(R.layout.item_chatting_room, parent, false);
        }

        final View finalConvertView = convertView;

        String otherID;
        String userEmailID = Utils.toBase64(user.email);

        if (room.sender.equals(userEmailID)) {
            otherID = room.receiver;
            isYour = true;
        } else {
            otherID = room.sender;
            isYour = false;
        }

        FirebaseDB.getUserInfo(otherID, new FirebaseDB.OnSetUser() {
            @Override
            public void setUser(final User receiver) {
                settingUser(finalConvertView, receiver);
            }

            @Override
            public void setError(String msg) {
                settingError(msg);
            }
        });

        return convertView;
    }

    public void settingUser (final View convertView, final User receiver) {
        TextView tvNick = convertView.findViewById(R.id.tv_room_nickname);
        Button btnChat = convertView.findViewById(R.id.btn_room_chat);

        tvNick.setText(receiver.nickname);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStartChat(user.email, receiver.email);
            }
        });
    }

    public void settingError (String msg) {
        Utils.viewToast(context, msg);
    }

    public void clickStartChat (String sender, String receiver) {
        String senderID = Utils.toBase64(sender);
        String receiverID = Utils.toBase64(receiver);
        String roomID;

        if (isYour) {
            roomID = Utils.toBase64(senderID + "/" + receiverID);
        } else {
            roomID = Utils.toBase64(receiverID + "/" + senderID);
        }

        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("roomID", roomID);
        intent.putExtra("emailID", senderID);
        context.startActivity(intent);
    }
}
