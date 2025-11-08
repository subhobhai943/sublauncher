package com.subhobhai.sublauncher.version;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.subhobhai.sublauncher.R;
import com.subhobhai.sublauncher.utils.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VersionListActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView errorView;
    private RecyclerView recyclerView;
    private VersionListAdapter adapter;
    private List<MinecraftVersion> versionList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_list);

        progressBar = findViewById(R.id.progress);
        errorView = findViewById(R.id.error);
        recyclerView = findViewById(R.id.version_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VersionListAdapter(versionList, version -> {
            // Placeholder: on version click
            Logger.i("VersionListActivity", "Selected: " + version);
            // TODO: Go to download/launch activity for this version
        });
        recyclerView.setAdapter(adapter);

        loadVersions();
    }

    private void loadVersions() {
        progressBar.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        File cacheDir = getCacheDir();
        VersionManifestDownloader.fetchVersions(cacheDir, new VersionManifestDownloader.VersionListCallback() {
            @Override
            public void onSuccess(List<MinecraftVersion> versions) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    versionList.clear();
                    versionList.addAll(versions);
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    errorView.setVisibility(View.VISIBLE);
                    errorView.setText("Failed to load versions: " + e.getMessage());
                    recyclerView.setVisibility(View.GONE);
                });
            }
        });
    }
}
