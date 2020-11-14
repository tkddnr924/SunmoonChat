package com.example.sunmoonchat.Chat;

import java.util.HashMap;
import java.util.Map;

public class Chat {
    public String email;
    public String createdAt;
    public String msg;
    public String nickName;

    public Chat () {}
    public Chat (String id, String time, String message, String nick) {
        email = id;
        createdAt = time;
        msg = message;
        nickName = nick;
    }

    public Map<String, Object> toMap () {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("createdAt", createdAt);
        result.put("msg", msg);
        result.put("nickName", nickName);

        return result;
    }

    public String getEmail () {
        return email;
    }
    public String getNickName () {
        return nickName;
    }
    public String getMsg () {
        return msg;
    }
    public String getCreatedAt () {
        return createdAt;
    }
}
