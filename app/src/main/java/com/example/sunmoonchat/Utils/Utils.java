package com.example.sunmoonchat.Utils;

import android.content.Context;
import android.widget.Toast;
import java.util.Base64;

public class Utils {
    public static void viewToast (Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static String emailToBase64 (String email) {
        return Base64.getEncoder().encodeToString(email.getBytes());
    }
}
