package com.example.sunmoonchat.User;

import java.util.HashMap;
import java.util.Map;

public class User {
    public String email;
    public String name;
    public String nickname;

    public User () {}

    public User (String email, String name, String nickname) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
    }

    public Map<String, Object> toMap () {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("name", name);
        result.put("nickname", nickname);

        return result;
    }

    @Override
    public String toString () {
        return String.format("User {email: %s, name: %s, nickname: %s}", email, name, nickname);
    }
}
