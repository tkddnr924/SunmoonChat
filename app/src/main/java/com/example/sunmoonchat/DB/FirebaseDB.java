package com.example.sunmoonchat.DB;

import androidx.annotation.NonNull;

import com.example.sunmoonchat.User.User;
import com.example.sunmoonchat.Utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDB {
    private static String USERS_NODE = "Users";

    public static void saveUser (String email, String name, String nickname) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference userRef = db.getReference(USERS_NODE);

        User user = new User(email, name, nickname);

        String userMailId = Utils.emailToBase64(email);

        userRef.child(userMailId).setValue(user.toMap());
    }

    public static void setUserInfo (String encode, final OnSetUser callback) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
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

    public interface OnSetUser {
        void setUser (User user);
        void setError (String msg);
    }
}
