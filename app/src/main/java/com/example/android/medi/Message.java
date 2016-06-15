package com.example.android.medi;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Deepanshu on 3/23/2016.
 */
public class Message {
    public static  void message(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}