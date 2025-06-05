package com.example.phonebook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebook.Interface.OnclickListener;
import com.example.phonebook.R;

import java.util.List;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.NumberViewHolder> {
    Context context;
    List<String> listNumber;
    OnclickListener onclickListener;

    public NumberAdapter(Context context, List<String> listNumber, OnclickListener onclickListener) {
        this.context = context;
        this.listNumber = listNumber;
        this.onclickListener = onclickListener;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.number, parent, false);
        return new NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        String number = listNumber.get(position);
        //holder.imgNumber.setImageResource(Integer.parseInt(number));
        holder.tvNumber.setText(number);
        holder.itemView.setOnClickListener(view -> onclickListener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return listNumber.size();
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder{
        ImageView imgNumber, ivDelete;
        TextView tvNumber;
        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNumber = itemView.findViewById(R.id.iv_number);
            tvNumber = itemView.findViewById(R.id.tv_title);
        }
    }
}
