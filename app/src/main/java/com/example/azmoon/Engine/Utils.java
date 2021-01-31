package com.example.azmoon.Engine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.RecyclerView;

import com.example.azmoon.Engine.RecyclerAdapter.RecyclerViewAdapter;


import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.Locale;


public class Utils {
    private Context context;
    private Activity activity;


    public Utils(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public Object getSharedPreferences(String key, Object _default) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("EShop", Context.MODE_PRIVATE);
        if (_default instanceof String) {
            return sharedpreferences.getString(key, (String) _default);
        } else if (_default instanceof Boolean) {
            return sharedpreferences.getBoolean(key, (Boolean) _default);
        } else if (_default instanceof Float) {
            return sharedpreferences.getFloat(key, (Float) _default);
        } else if (_default instanceof Integer) {
            return sharedpreferences.getInt(key, (Integer) _default);
        } else if (_default instanceof Long) {
            return sharedpreferences.getLong(key, (Long) _default);
        }
        return null;
    }

    public void setSharedPreferences(String key, Object data) {
        SharedPreferences.Editor editor = context.getSharedPreferences("EShop", Context.MODE_PRIVATE).edit();
        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        }
        editor.apply();
    }

    //  Activity => Context
    public void goTo(Class _class) {
        activity.startActivity(new Intent(context, _class));
        activity.finish();
    }

    public void addRecyclerView(RecyclerView rcv1, RecyclerView.LayoutManager layoutManager, RecyclerViewAdapter adapter) {
//        RecyclerView rcv1 = activity.findViewById(recyclerViewId);
        rcv1.setLayoutManager(layoutManager);
        rcv1.setAdapter(adapter);
    }

    public String splitDigits(Object number) {
        return new DecimalFormat("###,###,###").format(number);
    }
    public String toPersianNumber4(String english){
        final char[] chars=english.toCharArray();
        for (short i=0;i<(short)chars.length;i++)
            if(chars[i]<='9'&&chars[i] >='0')
                chars[i]=(char)(chars[i]+1728);
        return new String(chars);
    }
    public String cunvertNum(int n){

        NumberFormat nf=NumberFormat.getInstance(new Locale("ar","eg"));

        return nf.format(n);
    }

}
