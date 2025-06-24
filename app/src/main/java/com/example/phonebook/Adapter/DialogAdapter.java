package com.example.phonebook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebook.Fragment.AddAttDialogFragment;
import com.example.phonebook.R;

import java.util.List;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.DialogViewHolder> {
    public interface OnItemClick {
        void onItemClick(String type);
    }
    OnItemClick listener;
    List<String> listType;

    public DialogAdapter(List<String> listType, OnItemClick listener) {
        this.listType = listType;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_string, parent, false);
        return new DialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogViewHolder holder, int position) {
        String item = listType.get(position);
        holder.tvTitle.setText(item);
        holder.itemView.setOnClickListener(click -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return listType.size();
    }

    public class DialogViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public DialogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item);
        }
    }
}
