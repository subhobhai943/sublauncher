package com.subhobhai.sublauncher.version;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter for showing Minecraft versions in a RecyclerView
 */
public class VersionListAdapter extends RecyclerView.Adapter<VersionListAdapter.ViewHolder> {
    public interface OnVersionClickListener {
        void onVersionClick(MinecraftVersion version);
    }

    private final List<MinecraftVersion> versionList;
    private final OnVersionClickListener clickListener;

    public VersionListAdapter(List<MinecraftVersion> list, OnVersionClickListener listener) {
        this.versionList = list;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_version, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MinecraftVersion version = versionList.get(position);
        holder.versionName.setText(version.id);
        holder.versionType.setText(version.type.name());
        // Bold and color for releases, gray for others
        if (version.type == MinecraftVersion.Type.RELEASE) {
            holder.versionType.setTextColor(Color.parseColor("#388E3C"));
        } else if (version.type == MinecraftVersion.Type.SNAPSHOT) {
            holder.versionType.setTextColor(Color.parseColor("#F57C00"));
        } else {
            holder.versionType.setTextColor(Color.GRAY);
        }
        holder.cardView.setOnClickListener(v -> {
            if (clickListener != null) clickListener.onVersionClick(version);
        });
    }

    @Override
    public int getItemCount() {
        return versionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView versionName, versionType;
        CardView cardView;
        ViewHolder(View v) {
            super(v);
            versionName = v.findViewById(R.id.version_name);
            versionType = v.findViewById(R.id.version_type);
            cardView = v.findViewById(R.id.version_card);
        }
    }
}
