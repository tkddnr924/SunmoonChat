package com.example.sunmoonchat.User;

import java.util.HashMap;
import java.util.Map;

public class User {
    String email;
    String name;
    String nickname;

    public User () {}

    public User (String email, String name, String nickname) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
    }

    public Map<String, Object> toMap () {
        HashMap<String, Object> result = new HashMap<>();
        result.put("E-mail", email);
        result.put("Name", name);
        result.put("NickName", nickname);

        return result;
    }
}
