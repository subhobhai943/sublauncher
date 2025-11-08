package com.subhobhai.sublauncher.version;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.subhobhai.sublauncher.R;

public class VersionDetailActivity extends AppCompatActivity {
    public static final String EXTRA_VERSION_ID = "version_id";
    public static final String EXTRA_VERSION_TYPE = "version_type";
    public static final String EXTRA_VERSION_RELEASE = "version_release";
    public static final String EXTRA_VERSION_TIME = "version_time";
    public static final String EXTRA_VERSION_URL = "version_url";

    private TextView idView, typeView, releaseView, ramValue, statusView;
    private SeekBar ramSlider;
    private MaterialButton installBtn;
    private ProgressBar progressBar;
    private int ramMB = 1024;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_detail);

        idView = findViewById(R.id.version_detail_id);
        typeView = findViewById(R.id.version_detail_type);
        releaseView = findViewById(R.id.version_detail_release);
        ramSlider = findViewById(R.id.install_ram_slider);
        ramValue = findViewById(R.id.install_ram_value);
        installBtn = findViewById(R.id.install_btn);
        progressBar = findViewById(R.id.install_progress);
        statusView = findViewById(R.id.install_status);

        Intent intent = getIntent();
        String versionId = intent.getStringExtra(EXTRA_VERSION_ID);
        String versionType = intent.getStringExtra(EXTRA_VERSION_TYPE);
        String releaseTime = intent.getStringExtra(EXTRA_VERSION_RELEASE);

        idView.setText(versionId != null ? versionId : "?version?");
        typeView.setText(versionType != null ? versionType : "?");
        releaseView.setText("Release: " + (releaseTime != null ? releaseTime : "?"));

        ramValue.setText(ramMB + " MB");
        ramSlider.setProgress(ramMB);
        ramSlider.setMax(4096);
        ramSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ramMB = progress;
                ramValue.setText(ramMB + " MB");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        installBtn.setOnClickListener(v -> {
            installBtn.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            statusView.setVisibility(View.VISIBLE);
            statusView.setText("Downloading...");
            // TODO: Begin version installation
            // For now, fake a delay:
            v.postDelayed(() -> {
                progressBar.setVisibility(View.GONE);
                statusView.setText("Install complete!");
                Toast.makeText(this, "Installed " + versionId, Toast.LENGTH_SHORT).show();
                installBtn.setEnabled(true);
            }, 2600);
        });
    }
}
