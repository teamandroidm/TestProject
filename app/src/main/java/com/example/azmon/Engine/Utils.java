package com.example.azmon.Engine;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azmon.Engine.RecyclerView.RecyclerViewAdapter;

public class Utils {
    private Context context;
    private Activity activity;

    public Utils(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public Object getSharedPreferences(String key, Object _default) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("AZMOON", Context.MODE_PRIVATE);
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
        SharedPreferences.Editor editor = context.getSharedPreferences("AZMOON", Context.MODE_PRIVATE).edit();
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

    public void goTo(Class _class, PutExtra... putExtras) {
        Intent a = new Intent(context, _class);
        for (PutExtra putExtra : putExtras)
            if (putExtra.data instanceof Integer)
                a.putExtra(putExtra.name, (Integer) putExtra.data);
        activity.startActivity(a);
        activity.finish();
    }

    public void goTo(Class _class, boolean notFinished) {
        activity.startActivity(new Intent(context, _class));
    }

    public void goTo(Class _class, boolean notFinished, PutExtra... putExtras) {
        Intent a = new Intent(context, _class);
        for (PutExtra putExtra : putExtras)
            if (putExtra.data instanceof Integer)
                a.putExtra(putExtra.name, (Integer) putExtra.data);
        activity.startActivity(a);
    }

    public void addRecyclerView(int recyclerViewId, RecyclerView.LayoutManager layoutManager, RecyclerViewAdapter adapter) {
        RecyclerView rcv1 = activity.findViewById(recyclerViewId);
        rcv1.setLayoutManager(layoutManager);
        rcv1.setAdapter(adapter);
    }

    public void addRecyclerView(int recyclerViewId, RecyclerView.LayoutManager layoutManager, RecyclerViewAdapter adapter, View view) {
        RecyclerView rcv1 = view.findViewById(recyclerViewId);
        rcv1.setLayoutManager(layoutManager);
        rcv1.setAdapter(adapter);
    }

    public boolean setFragment(Fragment fragment, int container) {
        FragmentTransaction transaction = ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
        transaction.replace(container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        return true;
    }

    public void alert(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void alert(String message, int length) {
        Toast.makeText(context, message, length).show();
    }

}
