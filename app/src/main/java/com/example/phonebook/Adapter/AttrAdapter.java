package com.example.phonebook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebook.R;

import java.util.List;

public class AttrAdapter extends RecyclerView.Adapter<AttrAdapter.AttrViewHolder> {
    Context context;
    List<String> listAttr;

    public AttrAdapter(Context context, List<String> listAttr) {
        this.context = context;
        this.listAttr = listAttr;
    }

    @NonNull
    @Override
    public AttrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attr, parent, false);
        return new AttrViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttrViewHolder holder, int position) {
        String[] attr = listAttr.get(position).split(" ");
        holder.tvTitle.setText(attr[0]);
        holder.tvValue.setText(attr[1]);
    }

    @Override
    public int getItemCount() {
        return listAttr.size();
    }

    public class AttrViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvValue;
        public AttrViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_attr_title);
            tvValue = itemView.findViewById(R.id.item_attr_value);
        }
    }
}
