package com.example.azmon.Engine.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private int layout;
    private int size;
    private RecyclerViewMethod recyclerViewMethod;

    public RecyclerViewAdapter(Context context, int layout, int size, RecyclerViewMethod recyclerViewMethod) {
        this.size = size;
        this.mInflater = LayoutInflater.from(context);
        this.layout = layout;
        this.recyclerViewMethod = recyclerViewMethod;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        recyclerViewMethod.onItem(holder, position, holder.itemView);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;

        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}