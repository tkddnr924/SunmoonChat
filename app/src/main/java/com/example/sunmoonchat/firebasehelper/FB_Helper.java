package com.example.sunmoonchat.firebasehelper;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.sunmoonchat.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FB_Helper {

    private static FirebaseAuth getFireBaseInstance () {
        return FirebaseAuth.getInstance();
    }

    public static void join (final Context context, String email, String password) {
        FirebaseAuth mAuth = getFireBaseInstance();
        mAuth.signOut();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Utils.viewToast(context, "회원가입 완료");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utils.viewToast(context, e.getLocalizedMessage());
                    }
                });
    }

    public static FirebaseUser getUser () {
        FirebaseAuth mAuth = getFireBaseInstance();
        return mAuth.getCurrentUser();
    }
}
