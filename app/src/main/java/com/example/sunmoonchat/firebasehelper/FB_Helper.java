package com.example.sunmoonchat.firebasehelper;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FB_Helper {
    private FirebaseAuth mAuth;
    Context currentContext;

    public FB_Helper (Context context) {
        mAuth = FirebaseAuth.getInstance();
        currentContext = context;
    }

    public void join (String email, String password) {
        mAuth.signOut();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(
                                    currentContext, "회원가입 완료", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                currentContext, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public FirebaseUser getUser () {
        return mAuth.getCurrentUser();
    }
}
