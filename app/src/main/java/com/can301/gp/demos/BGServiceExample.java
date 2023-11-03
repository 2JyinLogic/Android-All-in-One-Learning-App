package com.can301.gp.demos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.can301.gp.Demonstration;
import com.can301.gp.R;
import com.can301.gp.codepage.CodePage;

public class BGServiceExample extends AppCompatActivity {

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

// Don't change END

    // Change this to exactly the string as in the AndroidManifest.xml
    public static final String EFFECT_ACTIVITY_NAME = ".demos.BGServiceExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change here to the layout name
        setContentView(R.layout.activity_bgservice_example);

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
            Button startServiceBtn = findViewById(R.id.StartServicebutton);
            Button stopServiceBtn = findViewById(R.id.StopServicebutton);
            startServiceBtn.setOnClickListener(v -> startExampleService());
            stopServiceBtn.setOnClickListener(v -> stopExampleService());

            TextView tv = findViewById(R.id.integerValueText);
            // Update the integer text every 0.2 second
            new Thread(() -> {
                while(true) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                    runOnUiThread(() -> tv.setText("The value of the integer: " +
                        Integer.toString(BGServiceExampleService.theInteger)
                    ));
                }
            }).start();
        }
    }

    void startExampleService() {
        // Get the values from the UI
        CheckBox cb = findViewById(R.id.newThreadCheckBox);
        boolean newThread = cb.isChecked();
        EditText et = findViewById(R.id.secondsToSleepInput);
        float timeToSleep = Float.parseFloat(et.getText().toString());

        Intent intent = new Intent(this, BGServiceExampleService.class);
        intent.putExtra(BGServiceExampleService.SLEEP_TIME_KEY, timeToSleep);
        intent.putExtra(BGServiceExampleService.START_NEW_THREAD_KEY, newThread);
        startService(intent);
    }

    void stopExampleService() {
        BGServiceExampleService.stopService = true;
        stopService(new Intent(this, BGServiceExampleService.class));
    }

}
