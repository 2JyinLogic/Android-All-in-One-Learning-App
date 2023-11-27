package com.can301.gp.demos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.can301.gp.Demonstration;
import com.can301.gp.MainActivity;
import com.can301.gp.R;
import com.can301.gp.codepage.CodePage;

import java.util.List;

public class SensorsExampleActivity extends AppCompatActivity implements SensorEventListener {

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

    /**
     * Opens the browser with the link to the documentation
     * (execute when the user clicks the view button)
     * @param link to the documentation
     */
    private void viewDocumentationPage(String link) {
        // See https://developer.android.com/guide/components/intents-common#ViewUrl
        Uri linkUri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, linkUri);
        // Note: getPackageManager queries packages installed, which is filtered and limited since
        // API level 30. To make the kind of packages visible, declare the queries in the manifest.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            // Show the user that it cannot be done.
            // See https://developer.android.com/develop/ui/views/components/dialogs
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cannot open website");
            builder.setMessage("Cannot redirect you to the documentation website " +
                    "because a browser cannot be found.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing, which dismisses the dialog\
                }
            });
            Dialog diag = builder.create();
            diag.show();
        }
    }

    /**
     * Go home when the user clicks the home button.
     * @param view
     */
    public void goHome(View view) {
        MainActivity.goBackToHomePage(this);
    }

// Don't change END

    // Change this to exactly the string as in the AndroidManifest.xml
    public static final String EFFECT_ACTIVITY_NAME = ".demos.SensorsExampleActivity";

    SensorManager sensorManager;
    List<Sensor> deviceSensors;

    Sensor lightSensor;
    Sensor accelerometerSensor;
    Sensor proximitySensor;
    Sensor tempSensor;

    TextView sensor1Text;
    TextView sensor2Text;
    TextView sensor3Text;
    TextView sensor4Text;
    TextView sensor1AccText;
    TextView sensor2AccText;
    TextView sensor3AccText;
    TextView sensor4AccText;

    TextView numSensorsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change here to the layout name
        setContentView(R.layout.activity_sensors_example);

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

        // Get the documentation link
        Resources resources = getResources();
        // equivalent to R.string.doclinkstringid
        int docLinkStringId = resources.getIdentifier(Demonstration.codeIdToDocLinkStringId(codeId), "string", getPackageName());
        String docLinkString = getString(docLinkStringId);

        Button docLinkBtn = findViewById(R.id.docLink);
        docLinkBtn.setOnClickListener(v -> viewDocumentationPage(docLinkString));

        // This example related
        {
            sensor1Text = findViewById(R.id.sensor1Text);
            sensor2Text = findViewById(R.id.sensor2Text);
            sensor3Text = findViewById(R.id.sensor3Text);
            sensor4Text = findViewById(R.id.sensor4Text);
            sensor1AccText = findViewById(R.id.sensor1AccText);
            sensor2AccText = findViewById(R.id.sensor2AccText);
            sensor3AccText = findViewById(R.id.sensor3AccText);
            sensor4AccText = findViewById(R.id.sensor4AccText);
            numSensorsText = findViewById(R.id.numSensorsText);

            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            listAllSensors();
            getSensors();
        }
    }

    /**
     * List all sensors available on the device and store them in
     * deviceSensors.
     */
    void listAllSensors() {
        deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        numSensorsText.setText("Number of sensors: " + deviceSensors.size());
    }

    /**
     * Get the sensors that are to be used in this example.
     */
    void getSensors() {
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if(lightSensor == null) {
            sensor1Text.setText("Not available");
            sensor1AccText.setText("Not available");
        }
        if(accelerometerSensor == null) {
            sensor2Text.setText("Not available");
            sensor2AccText.setText("Not available");
        }
        if(proximitySensor == null) {
            sensor3Text.setText("Not available");
            sensor3AccText.setText("Not available");
        }
        if(tempSensor == null) {
            sensor4Text.setText("Not available");
            sensor4AccText.setText("Not available");
        }
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        switch(sensor.getType()) {
            case Sensor.TYPE_LIGHT:
                sensor1AccText.setText("Acc: " + accuracy);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                sensor2AccText.setText("Acc: " + accuracy);
                break;
            case Sensor.TYPE_PROXIMITY:
                sensor3AccText.setText("Acc: " + accuracy);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                sensor4AccText.setText("Acc: " + accuracy);
                break;
        }
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        switch(event.sensor.getType()) {
            case Sensor.TYPE_LIGHT:
                float lux = event.values[0];
                sensor1Text.setText("Value: " + lux);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                float gx = event.values[0];
                float gy = event.values[1];
                float gz = event.values[2];
                sensor2Text.setText("Value: " + gx + ", " + gy + ", " + gz);
                break;
            case Sensor.TYPE_PROXIMITY:
                float dist = event.values[0];
                sensor3Text.setText("Value: " + dist);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                float temp = event.values[0];
                sensor4Text.setText("Value: " + temp);
                break;
        }
    }

    @Override
    protected void onResume() {
        // It's called after onCreate() so by now the sensors are created.
        super.onResume();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        // Specifying the delay in microseconds only works from Android 2.3 (API level 9) onwards.
        // For earlier releases, you must use one of the SENSOR_DELAY_* constants.
        // You must also declare the HIGH_SAMPLING_RATE_SENSORS permission
//        sensorManager.registerListener(this, lightSensor, 1000);
//        sensorManager.registerListener(this, accelerometerSensor, 1000);
//        sensorManager.registerListener(this, proximitySensor, 1000);
//        sensorManager.registerListener(this, tempSensor, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}