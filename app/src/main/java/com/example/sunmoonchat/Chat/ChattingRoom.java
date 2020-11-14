package com.example.sunmoonchat.Chat;

import java.util.HashMap;
import java.util.Map;

public class ChattingRoom {
    public String roomID;
    public String sender;
    public String receiver;
    public String createdAt;

    public ChattingRoom () {}
    public ChattingRoom (String senderID, String receiverID, String time, String id) {
        roomID = id;
        sender = senderID;
        receiver = receiverID;
        createdAt = time;
    }

    public Map<String, Object> toMap () {
        HashMap<String, Object> result = new HashMap<>();
        result.put("sender", sender);
        result.put("receiver", receiver);
        result.put("createdAt", createdAt);
        result.put("roomID", roomID);

        return result;
    }
}
