package com.example.sunmoonchat.DB;

import com.example.sunmoonchat.User.User;
import com.example.sunmoonchat.Utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FirebaseDB {

    public static void saveUser (String email, String name, String nickname) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference userRef = db.getReference("Users");

        User user = new User(email, name, nickname);

        String userMailId = Utils.emailToBase64(email);
        HashMap<String, Object> node = new HashMap<>();
        node.put(userMailId, user.toMap());

        userRef.updateChildren(node);
    }
}
