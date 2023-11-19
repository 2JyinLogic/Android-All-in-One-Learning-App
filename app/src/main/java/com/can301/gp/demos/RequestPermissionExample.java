package com.can301.gp.demos;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;

import com.can301.gp.Demonstration;
import com.can301.gp.MainActivity;
import com.can301.gp.R;
import com.can301.gp.codepage.CodePage;

public class RequestPermissionExample extends AppCompatActivity {

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


    // Start Service Button
    Button startSvcBtn;
    // Stop Service Button
    Button stopSvcBtn;
    // Show the user's response to the notification.
    TextView msgResponseText;

// Don't change END

    // Change this to exactly the string as in the AndroidManifest.xml
    public static final String EFFECT_ACTIVITY_NAME = ".demos.RequestPermissionExample";

    // Used to display the permission request result.
    // (Because the requestPermissionLauncher has to be created unconditionally as a field,
    // it can only be used once per activity. Recreate the activity when finished.
    public static final String REQUEST_RESULT_KEY = "request result";

    TextView resultTxtView;

    // Made a class field so that the field after it can access it.
    String permissionRequestResult = "";
    // This has to be a class field and cannot be a variable in the onclick listener.
    ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change here to the layout name
        setContentView(R.layout.activity_request_permission_example);

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

        // The example related
        {
            requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    String resultStr;
                    if (isGranted) {
                        resultStr = "You have granted the permission.";
                    } else {
                        resultStr = "You have refused to grant the permission.";
                    }

                    Intent intent = new Intent(this, RequestPermissionExample.class);
                    intent.putExtra(Demonstration.EFFECT_DEMO_TITLE_KEY, demoTitle);
                    intent.putExtra(Demonstration.EFFECT_DEMO_CODE_ID_KEY, codeId);
                    intent.putExtra(REQUEST_RESULT_KEY, resultStr);
                    startActivity(intent);
                });

            if (!inIntent.hasExtra(REQUEST_RESULT_KEY)) {
                permissionRequestResult = "";
            }
            else {
                permissionRequestResult = inIntent.getStringExtra(REQUEST_RESULT_KEY);
            }

            resultTxtView = findViewById(R.id.resultView);
            resultTxtView.setText(permissionRequestResult);

            Button locationBtn = findViewById(R.id.connectionBtn);
            Button fileBtn = findViewById(R.id.appsBtn);
            Button notificationBtn = findViewById(R.id.permission3Btn);

            locationBtn.setOnClickListener(
                v -> requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
            fileBtn.setOnClickListener(
                v -> requestPermission(Manifest.permission.READ_CONTACTS));
            notificationBtn.setOnClickListener(
                v -> requestPermission(Manifest.permission.POST_NOTIFICATIONS));

        }
    }

    /**
     * Request for the corresponding permission when the user clicks the related button.
     *
     * 1. Check if the app already has the permission. If it does, then notify the user about that.
     * 2. If the app does not have the permission and a rationale as to why the permission is needed
     * is suggested by the system, then display it.
     * 3. Otherwise, directly request the permission.
     *
     * @param permission Manifest.permission.xxx
     */
    void requestPermission(String permission) {
        // 1. Check if the app already has the permission.
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
                this, permission)) {
            // Granted
            // Show the user that the app already has the permission
            // See https://developer.android.com/develop/ui/views/components/dialogs
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Already has the permission");
            builder.setMessage("The app already has the permission granted. " +
                    "To see the demonstration of asking for it, " +
                    "withdraw the permission in Settings first");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing, which dismisses the dialog
                }
            });
            Dialog diag = builder.create();
            diag.show();
            return;
        }
        else {
            // Not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, permission)) {
                // The system thinks we should show a rationale.

                // Show the user the reason for the permission
                // See https://developer.android.com/develop/ui/views/components/dialogs
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Why the permission is needed");
                builder.setMessage("Explain to your user why you need this permission " +
                        "for your app to work." +
                        "You should give the user a way to refuse. In this example, " +
                        "if your refuse, the permission will not be requested.");
                builder.setPositiveButton("Agree",  (diag, which) -> {
                    // Ask for the permission.
                    requestPermissionLauncher.launch(permission);
                    });
                builder.setNegativeButton("Refuse", (diag, which) -> {
                    resultTxtView.setText("You have refused the rationale.");
                    });
                Dialog diag = builder.create();
                diag.show();

            }
            else {
                // Ask for the permission.
                requestPermissionLauncher.launch(permission);
            }
        }
    }
}