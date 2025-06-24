package com.example.phonebook.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebook.Adapter.DialogAdapter;
import com.example.phonebook.R;

import java.util.List;

public class AddAttDialogFragment extends DialogFragment {

    TextView tvCancel;
    RecyclerView rvTypes;
    DialogAdapter dialogAdapter;

    public interface OnClickTypeListener {
        void onClickType(String type);
    }

    private final OnClickTypeListener onClickTypeListener;
    private List<String> types;

    public AddAttDialogFragment(List<String> types, OnClickTypeListener onClickTypeListener) {
        this.types = types;
        this.onClickTypeListener = onClickTypeListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.diaglog_add_att, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(cancel -> dismiss());
        rvTypes = view.findViewById(R.id.rv_types);
        rvTypes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        dialogAdapter = new DialogAdapter(types, new DialogAdapter.OnItemClick() {
            @Override
            public void onItemClick(String type) {
                onClickTypeListener.onClickType(type);
                dismiss();
            }
        });
        rvTypes.setAdapter(dialogAdapter);
        Toast.makeText(getContext(), "Size: " + types.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Cài đặt lại kích thước Dialog ở đây
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}
