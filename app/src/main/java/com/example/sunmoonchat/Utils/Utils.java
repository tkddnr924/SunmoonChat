package com.example.sunmoonchat.Utils;

import android.content.Context;
import android.widget.Toast;

public class Utils {
    public static void viewToast (Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
