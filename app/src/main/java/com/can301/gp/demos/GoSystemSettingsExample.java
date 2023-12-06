package com.can301.gp.demos;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.can301.gp.Demonstration;
import com.can301.gp.MainActivity;
import com.can301.gp.R;
import com.can301.gp.codepage.CodePage;

public class GoSystemSettingsExample extends AppCompatActivity {

    private int demoTitle;
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


    // Start Service Button
    Button startSvcBtn;
    // Stop Service Button
    Button stopSvcBtn;
    // Show the user's response to the notification.
    TextView msgResponseText;

// Don't change END

    // Change this to exactly the string as in the AndroidManifest.xml
    public static final String EFFECT_ACTIVITY_NAME = ".demos.GoSystemSettingsExample";

    // Used to display the permission request result.
    // (Because the requestPermissionLauncher has to be created unconditionally as a field,
    // it can only be used once per activity. Recreate the activity when finished.
    public static final String REQUEST_RESULT_KEY = "request result";

    Spinner connectionSpinner;
    Spinner appsSpinner;
    Spinner miscSpinner;
    Spinner storageSpinner;
    Spinner thisAppSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change here to the layout name
        setContentView(R.layout.activity_go_system_settings_example);

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
        demoTitle = inIntent.getIntExtra(Demonstration.EFFECT_DEMO_TITLE_KEY, 0);
        assert demoTitle != 0;
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

        // The example related
        {
            Button connectionBtn = findViewById(R.id.connectionBtn);
            Button appsBtn = findViewById(R.id.appsBtn);
            Button miscBtn = findViewById(R.id.miscBtn);
            Button storageBtn = findViewById(R.id.storageBtn);
            Button thisAppBtn = findViewById(R.id.thisAppBtn);

            connectionSpinner = findViewById(R.id.connectionSpinner);
            appsSpinner = findViewById(R.id.appsSpinner);
            miscSpinner = findViewById(R.id.miscSpinner);
            storageSpinner = findViewById(R.id.storageSpinner);
            thisAppSpinner = findViewById(R.id.thisAppSpinner);

            // Populate the Spinners
            populateSpinner(connectionSpinner, R.array.connection_settings_array);
            populateSpinner(appsSpinner, R.array.apps_settings_array);
            populateSpinner(miscSpinner, R.array.misc_settings_array);
            populateSpinner(storageSpinner, R.array.storage_settings_array);
            populateSpinner(thisAppSpinner, R.array.this_app_settings_array);

            connectionBtn.setOnClickListener(
                v -> {
                     String selectedText = connectionSpinner.getSelectedItem().toString();
                     switch(selectedText) {
                         case "WiFi":
                             goToSystemSettingsPage(Settings.ACTION_WIFI_SETTINGS);
                             break;
                         case "WiFi IP":
                             goToSystemSettingsPage(Settings.ACTION_WIFI_IP_SETTINGS);
                             break;
                         case "Wireless":
                             goToSystemSettingsPage(Settings.ACTION_WIRELESS_SETTINGS);
                             break;
                         case "Bluetooth":
                             goToSystemSettingsPage(Settings.ACTION_BLUETOOTH_SETTINGS);
                             break;
                         case "Airplane Mode":
                             goToSystemSettingsPage(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                             break;
                     }
                }
            );
            appsBtn.setOnClickListener(
                v -> {
                    String selectedText = appsSpinner.getSelectedItem().toString();
                    switch(selectedText) {
                        case "Main":
                            goToSystemSettingsPage(Settings.ACTION_APPLICATION_SETTINGS);
                            break;
                        case "Apps Search":
                            goToSystemSettingsPage(Settings.ACTION_APP_SEARCH_SETTINGS);
                            break;
                        case "Battery Saver":
                            goToSystemSettingsPage(Settings.ACTION_BATTERY_SAVER_SETTINGS);
                            break;
                        case "Default Apps":
                            goToSystemSettingsPage(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS);
                            break;
                        case "Development":
                            goToSystemSettingsPage(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                            break;
                    }
                }
            );
            miscBtn.setOnClickListener(
                v -> {
                    String selectedText = miscSpinner.getSelectedItem().toString();
                    switch(selectedText) {
                        case "Date":
                            goToSystemSettingsPage(Settings.ACTION_DATE_SETTINGS);
                            break;
                        case "Locale":
                            goToSystemSettingsPage(Settings.ACTION_LOCALE_SETTINGS);
                            break;
                        case "Input Method":
                            goToSystemSettingsPage(Settings.ACTION_INPUT_METHOD_SETTINGS);
                            break;
                        case "Display":
                            goToSystemSettingsPage(Settings.ACTION_DISPLAY_SETTINGS);
                            break;
                        case "Security":
                            goToSystemSettingsPage(Settings.ACTION_SECURITY_SETTINGS);
                            break;
                        case "Location Source":
                            goToSystemSettingsPage(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            break;
                    }
                }
            );
            storageBtn.setOnClickListener(
                v -> {
                    String selectedText = storageSpinner.getSelectedItem().toString();
                    switch(selectedText) {
                        case "Interal Storage":
                            goToSystemSettingsPage(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
                            break;
                        case "Memory Card":
                            goToSystemSettingsPage(Settings.ACTION_MEMORY_CARD_SETTINGS);
                            break;
                    }
                }
            );
            thisAppBtn.setOnClickListener(
                v -> {
                    String selectedText = thisAppSpinner.getSelectedItem().toString();
                    switch(selectedText) {
                        case "This App Main":
                            goToThisAppSettingsPage(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            break;
                        case "This App File Access":
                            goToThisAppSettingsPage(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                            break;
                        case "This App Fullscreen":
                            goToThisAppSettingsPage(Settings.ACTION_MANAGE_APP_USE_FULL_SCREEN_INTENT);
                            break;
                        case "This App Open By Default":
                            goToThisAppSettingsPage(Settings.ACTION_APP_OPEN_BY_DEFAULT_SETTINGS);
                            break;
                    }
                }
            );
        }
    }

    /**
     * Populate whichSpinner with the string array identified by stringArrayId
     * See https://developer.android.com/develop/ui/views/components/spinner
     * @param whichSpinner
     * @param stringArrayId
     */
    void populateSpinner(Spinner whichSpinner, int stringArrayId) {
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                stringArrayId,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        whichSpinner.setAdapter(adapter);
        // Select the first item by default.
        whichSpinner.setSelection(0);
    }

    /**
     * Go to a general (in contrast to specific app's)
     * system settings page
     * @param whichPage
     */
    void goToSystemSettingsPage(String whichPage)
    {
        Intent intent = new Intent(whichPage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            // Show the user that this page is not there
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cannot open this setting page");
            builder.setMessage("Cannot redirect you to this setting page " +
                    "because on this device the settings app does not have this page");
            builder.setPositiveButton("OK", (diag, which) -> {});
            Dialog diag = builder.create();
            diag.show();
        }
    }

    /**
     * Go to a specific page of this app in the system settings
     * @param whichPage
     */
    void goToThisAppSettingsPage(String whichPage)
    {
        Intent intent = new Intent(whichPage);
        // Fill this app's package in the intent's data
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            // Show the user that this page is not there
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cannot open this setting page");
            builder.setMessage("Cannot redirect you to this setting page " +
                    "because on this device the settings app does not have this page");
            builder.setPositiveButton("OK", (diag, which) -> {});
            Dialog diag = builder.create();
            diag.show();
        }
    }

}