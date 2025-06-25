package com.example.phonebook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GenericAdapter<T> extends RecyclerView.Adapter<GenericAdapter.GenericViewHolder> {

    public interface Binder<T> {
        void bind(View itemView, T item, int position);
    }

    private List<T> listItem;
    private Binder<T> binder;
    private int layoutId;

    public GenericAdapter(int layoutId, List<T> listItem, Binder<T> binder) {
        this.layoutId = layoutId;
        this.listItem = listItem;
        this.binder = binder;
    }

    @NonNull
    @Override
    public GenericAdapter.GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new GenericViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericAdapter.GenericViewHolder holder, int position) {
        T item = listItem.get(position);
        binder.bind(holder.itemView, item, position);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class GenericViewHolder extends RecyclerView.ViewHolder {

        public GenericViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
