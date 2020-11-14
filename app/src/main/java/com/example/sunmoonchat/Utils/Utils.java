package com.example.sunmoonchat.Utils;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class Utils {
    public static void viewToast (Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static String toBase64 (String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String getNowTime (String format) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat(format);
        return sdfNow.format(date);
    }
}
