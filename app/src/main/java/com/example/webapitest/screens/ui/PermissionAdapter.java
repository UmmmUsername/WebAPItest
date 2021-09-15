package com.example.webapitest.screens.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webapitest.databinding.PermissionViewHolderBinding;

import java.util.List;

public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.ViewHolder> {

    private List<String> permissions;

    public PermissionAdapter(List<String> permissions) {
        super();
        this.permissions = permissions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PermissionViewHolderBinding binding = PermissionViewHolderBinding.inflate(inflater);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(permissions.get(position));
    }

    @Override
    public int getItemCount() {
        return permissions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final PermissionViewHolderBinding binding;

        private ViewHolder(PermissionViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String text) {
            binding.textView.setText(text);
        }
    }
}
