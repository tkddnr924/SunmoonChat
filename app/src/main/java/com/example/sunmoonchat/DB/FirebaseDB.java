package com.example.sunmoonchat.DB;

import androidx.annotation.NonNull;

import com.example.sunmoonchat.Chat.Chat;
import com.example.sunmoonchat.Chat.ChattingRoom;
import com.example.sunmoonchat.User.User;
import com.example.sunmoonchat.Utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FirebaseDB {
    private static String USERS_NODE = "Users";
    private static String CHATTING_ROOM_NODE = "ChattingRoom";
    private static String CHAT_NODE = "Chats";
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();

    public static void saveUser (String email, String name, String nickname) {
        DatabaseReference userRef = db.getReference(USERS_NODE);

        User user = new User(email, name, nickname);

        String userMailId = Utils.toBase64(email);

        userRef.child(userMailId).setValue(user.toMap());
    }


    public static void addChattingRoom (String id, String chatID, ChattingRoom room) {
        DatabaseReference userRef = db.getReference(USERS_NODE + "/" + id + "/ChattingRoom");

        userRef.child(chatID).setValue(room.toMap());
    }

    public static void createdChat (String sender, String receiver) {
        DatabaseReference roomRef = db.getReference(CHATTING_ROOM_NODE);
        String time = Utils.getNowTime("yyyy/MM/dd HH:mm:ss");
        String chattingRoomId = Utils.toBase64(sender + "/" + receiver);

        ChattingRoom room = new ChattingRoom(sender, receiver, time, chattingRoomId);

        addChattingRoom(sender, chattingRoomId, room);
        addChattingRoom(receiver, chattingRoomId, room);

        roomRef.child(chattingRoomId).setValue(room.toMap());
    }

    public static void sendChat
            (String emailID, String roomID, String msg, String nickname) {
        String time = Utils.getNowTime("yyyy/MM/dd HH:mm:ss");
        String timeID = Utils.getNowTime("yyyy-MM-dd-HH-mm-ss");

        Chat chat = new Chat(emailID, time, msg, nickname);

        DatabaseReference chatRef = db
                .getReference(CHATTING_ROOM_NODE + "/" + roomID + "/" + CHAT_NODE);

        chatRef.child(timeID).setValue(chat.toMap());
    }

    public static void getUserInfo (String encode, final OnSetUser callback) {
        DatabaseReference userRef = db.getReference(USERS_NODE + "/" + encode);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                callback.setUser(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.setError(error.getMessage());
            }
        });
    }

    public static void getUserList (final OnGetUserList callback) {
        DatabaseReference userRef = db.getReference(USERS_NODE);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<User> users = new ArrayList<>();

                for (DataSnapshot child : snapshot.getChildren()) {
                    User user = child.getValue(User.class);
                    users.add(user);
                }

                callback.getUserList(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.setError(error.getMessage());
            }
        });
    }
    public static void getChattingRoomList (String emailID, final OnGetRoomList callback) {
        DatabaseReference roomRef = db
                .getReference(USERS_NODE + "/" + emailID + "/ChattingRoom");

        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ChattingRoom> rooms = new ArrayList<>();

                for (DataSnapshot child : snapshot.getChildren()) {
                    ChattingRoom roomID = child.getValue(ChattingRoom.class);
                    rooms.add(roomID);
                }

                callback.getRoomList(rooms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.setError(error.getMessage());
            }
        });
    }

    public static void getChatList(String chatID, final OnGetChatList callback) {
        DatabaseReference chatRef = db
                .getReference(CHATTING_ROOM_NODE + "/" + chatID + "/" + CHAT_NODE);

        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Chat> chats = new ArrayList<>();

                for (DataSnapshot child : snapshot.getChildren()) {
                    Chat chat = child.getValue(Chat.class);
                    chats.add(chat);
                }
                callback.getChatList(chats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.setError(error.getMessage());
            }
        });
    }

    public interface OnSetUser {
        void setUser (User user);
        void setError (String msg);
    }

    public interface OnGetUserList {
        void getUserList (ArrayList<User> users);
        void setError (String msg);
    }

    public interface OnGetChatList {
        void getChatList (ArrayList<Chat> chats);
        void setError (String msg);
    }

    public interface OnGetRoomList {
        void getRoomList (ArrayList<ChattingRoom> rooms);
        void setError (String msg);
    }
}
