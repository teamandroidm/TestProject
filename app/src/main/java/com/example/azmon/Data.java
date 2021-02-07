package com.example.azmon;

import android.content.Intent;
import android.view.View;

import java.util.Date;

public class Data {

    private static final String _URL1="http://192.168.1.102:8080/MyWebService/showalluser.php";
    public void sendIdField(int userId, byte fieldId, OnResult onResult) {
        if (userId == 1 && fieldId == 3) {
            onResult.success(true);
            return;
        } else {
            onResult.success(false);

        }
    }
    public void sendCourses(int userId, byte termdId, Date date,int price ,OnResult onResult) {
        date = new Date();
//        if (userId == 1 && fieldId == 3) {
//            onResult.success(true);
//            return;
//        } else {
//            onResult.success(false);

//        }
    }
}