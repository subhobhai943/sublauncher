package com.subhobhai.sublauncher;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeText;
    private Button launchButton;
    private Button settingsButton;
    private Button profilesButton;
    private Button modsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        welcomeText = findViewById(R.id.welcome_text);
        launchButton = findViewById(R.id.btn_launch);
        settingsButton = findViewById(R.id.btn_settings);
        profilesButton = findViewById(R.id.btn_profiles);
        modsButton = findViewById(R.id.btn_mods);

        // Set up click listeners
        launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGame();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        profilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfiles();
            }
        });

        modsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMods();
            }
        });

        // Check permissions on startup
        checkPermissions();
    }

    private void checkPermissions() {
        // TODO: Implement permission checking for storage and other required permissions
        Toast.makeText(this, "SubLauncher - Ready to launch!", Toast.LENGTH_SHORT).show();
    }

    private void launchGame() {
        // TODO: Implement game launching logic
        Toast.makeText(this, "Launching Minecraft...", Toast.LENGTH_SHORT).show();
        // This is where the actual Minecraft Java Edition launch logic would go
    }

    private void openSettings() {
        Toast.makeText(this, "Settings - Coming soon!", Toast.LENGTH_SHORT).show();
        // TODO: Open settings activity
    }

    private void openProfiles() {
        Toast.makeText(this, "Profiles - Coming soon!", Toast.LENGTH_SHORT).show();
        // TODO: Open profiles management
    }

    private void openMods() {
        Toast.makeText(this, "Mods - Coming soon!", Toast.LENGTH_SHORT).show();
        // TODO: Open mod manager
    }
}
