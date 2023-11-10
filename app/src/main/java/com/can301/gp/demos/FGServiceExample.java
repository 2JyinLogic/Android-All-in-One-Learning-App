package com.can301.gp.demos;

import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.can301.gp.Demonstration;
import com.can301.gp.R;
import com.can301.gp.codepage.CodePage;

public class FGServiceExample extends AppCompatActivity {

    private String demoTitle;
    // codeId is needed for the code page to load the corresponding code
    // and for this activity to load the documentation link
    private String codeId;

    void goToCodePage() {
        Intent intent = new Intent(this, CodePage.class);

        intent.putExtra(CodePage.CODE_ID_KEY, codeId);
        intent.putExtra(CodePage.CODE_CLASS_NAME_KEY, EFFECT_ACTIVITY_NAME);
        intent.putExtra(Demonstration.EFFECT_DEMO_TITLE_KEY, demoTitle);

        startActivity(intent);
    }

    // Start Service Button
    Button startSvcBtn;
    // Stop Service Button
    Button stopSvcBtn;

// Don't change END

    // Change this to exactly the string as in the AndroidManifest.xml
    public static final String EFFECT_ACTIVITY_NAME = ".demos.FGServiceExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change here to the layout name
        setContentView(R.layout.activity_fgservice_example);

        Button effectButton = findViewById(R.id.effectBottomButton);
        Button codeButton = findViewById(R.id.codeBottomButton);
        effectButton.setEnabled(false);
        codeButton.setEnabled(true);

        // Handling parameters
        Intent inIntent = getIntent();
        if (!inIntent.hasExtra(Demonstration.EFFECT_DEMO_TITLE_KEY)) {
            throw new RuntimeException("Give me the effect title!");
        }
        if (!inIntent.hasExtra(Demonstration.EFFECT_DEMO_CODE_ID_KEY)) {
            throw new RuntimeException("Give me the code ID!");
        }
        demoTitle = inIntent.getStringExtra(Demonstration.EFFECT_DEMO_TITLE_KEY);
        codeId = inIntent.getStringExtra(Demonstration.EFFECT_DEMO_CODE_ID_KEY);

        // Set information about this effect
        TextView effectTextView = findViewById(R.id.effectExampleName);
        effectTextView.setText(demoTitle);

        // Go to the corresponding code page
        codeButton.setOnClickListener(v -> goToCodePage());

        // The service example related
        {
            startSvcBtn = findViewById(R.id.startSvcBtn);
            stopSvcBtn = findViewById(R.id.stopSvcBtn);

            startSvcBtn.setOnClickListener(v -> startExampleService());
            stopSvcBtn.setOnClickListener(v -> stopExampleService());


        }
    }

    void startExampleService() {
        Intent intent = new Intent(this, FGServiceExampleService.class);
        ContextCompat.startForegroundService(this, intent);
    }

    void stopExampleService() {
        Intent intent = new Intent(this, FGServiceExampleService.class);
        stopService(intent);
    }

}