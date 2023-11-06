package com.can301.gp.demos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.can301.gp.Demonstration;
import com.can301.gp.R;
import com.can301.gp.codepage.CodePage;


public class BoundServiceExample extends AppCompatActivity {

    private String demoTitle;
    // codeId is needed for the code page to load the corresponding code
    // and for this activity to load the documentation link
    private String codeId;

    BoundServiceExampleService.ExampleBinder binder = null;
    boolean bound = false;

    void goToCodePage() {
        Intent intent = new Intent(this, CodePage.class);

        intent.putExtra(CodePage.CODE_ID_KEY, codeId);
        intent.putExtra(CodePage.CODE_CLASS_NAME_KEY, EFFECT_ACTIVITY_NAME);
        intent.putExtra(Demonstration.EFFECT_DEMO_TITLE_KEY, demoTitle);

        startActivity(intent);
    }

// Don't change END

    Button startAndBindBtn;
    Button unbindBtn;
    Button stopBtn;
    Button pauseResumeBtn;
    SeekBar progressSeekBar;

    ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (BoundServiceExampleService.ExampleBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // Reset the binder so that I know it's safely unbound.
            binder = null;
        }
    };

    // Change this to exactly the string as in the AndroidManifest.xml
    public static final String EFFECT_ACTIVITY_NAME = ".demos.BoundServiceExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change here to the layout name
        setContentView(R.layout.activity_bound_service_example);

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
            startAndBindBtn = findViewById(R.id.startAndBindButton);
            unbindBtn = findViewById(R.id.unbindButton);
            stopBtn = findViewById(R.id.stopButton);
            pauseResumeBtn = findViewById(R.id.pauseResumeButton);

            progressSeekBar = findViewById(R.id.musicProgress);

            startAndBindBtn.setOnClickListener(v -> startAndBindToExampleService());
            unbindBtn.setOnClickListener(v -> unBindExampleService());
            stopBtn.setOnClickListener(v -> stopExampleService());
            pauseResumeBtn.setOnClickListener(v -> pauseResume());
        }
    }

    void startAndBindToExampleService() {
        // Start the service.
        Intent intent = new Intent(this, BoundServiceExampleService.class);
        startService(intent);

        // If not bound to the service,
        // then bind to it.
        if (!bound && binder == null) {
            bindService(
                intent,
                myConnection,
                // declare that the service's important is higher than the activity so that
                // the system will try to keep the service even if the activity is killed.
                Context.BIND_ABOVE_CLIENT);

            bound = true;
        }
    }

    /**
     * Pauses or resumes the music play, depending on the current state.
     * Also updates the button's appearance.
     */
    void pauseResume() {
        if (bound && binder != null) {
            boolean pausedOrResumed = binder.pauseResume();

            // update the UI
            if(pausedOrResumed) {

            }
            else {

            }
        }
    }

    void stopExampleService() {
        Intent intent = new Intent(this, BoundServiceExampleService.class);
        stopService(intent);

        bound = false;
    }

    void unBindExampleService() {
        if(bound && binder != null) {
            unbindService(myConnection);
        }
    }
}
