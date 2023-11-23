package com.can301.gp.demos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.can301.gp.Demonstration;
import com.can301.gp.MainActivity;
import com.can301.gp.R;
import com.can301.gp.codepage.CodePage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppSpecificFilesExample extends AppCompatActivity {

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
    public static final String EFFECT_ACTIVITY_NAME = ".demos.AppSpecificFilesExample";

    EditText fileNameText;
    EditText fileContentText;
    CheckBox cacheCheckBox;
    TextView messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change here to the layout name
        setContentView(R.layout.activity_app_specific_files_example);

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
            fileNameText = findViewById(R.id.fileNameText);
            fileContentText = findViewById(R.id.fileContentText);
            cacheCheckBox = findViewById(R.id.cacheCheckbox);
            messageText = findViewById(R.id.messageText);

            Button createInternalBtn = findViewById(R.id.createInternalBtn);
            Button createExternalBtn = findViewById(R.id.createExternalBtn);
            Button readInternalBtn = findViewById(R.id.readInternalBtn);
            Button readExternalBtn = findViewById(R.id.readExternalBtn);

            createInternalBtn.setOnClickListener(v -> createInternalFile());
            createExternalBtn.setOnClickListener(v -> createExternalFile());
            readInternalBtn.setOnClickListener(v -> readInternalFile());
            readExternalBtn.setOnClickListener(v -> readExternalFile());
        }

    }

    /**
     * Creates a file in the Internal directories,
     * with the filename read from the text input and whether or not cache depending on the checkbox.
     * Sets corresponding messages in the message textview.
     */
    void createInternalFile() {
        // true -> persistent; false -> cache.
        boolean persistentOrCache = !cacheCheckBox.isChecked();
        String fileName = fileNameText.getText().toString();
        // The name of a cached file must be larger than 3.
        if(fileName.length() <= 3) {
            messageText.setText("The file name must be longer than 3 characters!");
            return;
        }
        String fileContent = fileContentText.getText().toString();
        if(fileName.isEmpty()) {
            fileContent = "You entered an empty text as content.";
        }

        // Create and open the file
        File file = null;
        if(persistentOrCache) {
            // Create a persistent file.
            file = new File(this.getFilesDir(), fileName);
        }
        else {
            // Create a cache file
            try {
                File.createTempFile(fileName, null, this.getCacheDir());
            } catch (IOException e) {
                messageText.setText("Error on creating file. Can be because of an invalid file name.");
                return;
            }
            file = new File(this.getCacheDir(), fileName);
        }

        // Write the content to the file.
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(fileContent.getBytes());
        } catch (IOException e) {
            messageText.setText("Error on writing file. Can be because of an invalid file name.");
            return;
        }

        String finalMessage = (persistentOrCache ? "Persistent" : "Cache") +
                " internal file named " + fileName + " created.";
        messageText.setText(finalMessage);

    }

    /**
     * Creates a file in the External directories,
     * with the filename read from the text input and whether or not cache depending on the checkbox.
     * Sets corresponding messages in the message textview.
     */
    void createExternalFile() {
        // Check if an external drive is available for read and write.
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            messageText.setText("No external storage is available for read and write.");
            return;
        }

        // true -> persistent; false -> cache.
        boolean persistentOrCache = !cacheCheckBox.isChecked();
        String fileName = fileNameText.getText().toString();
        // The name of a cached file must be larger than 3.
        if(fileName.length() <= 3) {
            messageText.setText("The file name must be longer than 3 characters!");
            return;
        }
        String fileContent = fileContentText.getText().toString();
        if(fileName.isEmpty()) {
            fileContent = "You entered an empty text as content.";
        }

//        // Select the first physical external storage location
//        File[] externalStorageVolumes =
//                ContextCompat.getExternalFilesDirs(getApplicationContext(), null);
//        File primaryExternalStorage = externalStorageVolumes[0];

        // Create and open the file
        File file = null;
        if(persistentOrCache) {
            // Create a persistent file.
            file = new File(this.getExternalFilesDir(null), fileName);
        }
        else {
            // Create a cache file
            file = new File(this.getExternalCacheDir(), fileName);
        }

        // Write the content to the file.
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(fileContent.getBytes());
        } catch (IOException e) {
            messageText.setText("Error on writing file. Can be because of an invalid file name.");
            return;
        }

        String finalMessage = (persistentOrCache ? "Persistent" : "Cache") +
                " external file named " + fileName + " created.";
        messageText.setText(finalMessage);
    }

    /**
     * Checks if a file exists in the internal directories and reads it,
     * with the filename read from the text input and whether or not cache depending on the checkbox.
     * Sets corresponding messages in the message textview.
     */
    void readInternalFile() {
        // true -> persistent; false -> cache.
        boolean persistentOrCache = !cacheCheckBox.isChecked();
        String fileName = fileNameText.getText().toString();

        // Open the file
        File file = null;
        if(persistentOrCache) {
            // Open a persistent file.
            file = new File(this.getFilesDir(), fileName);
        }
        else {
            // Open a cache file
            file = new File(this.getCacheDir(), fileName);
        }

        // Read from the file.
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            messageText.setText("Cannot open the file. Perhaps it does not exist.");
            return;
        }
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis);
        StringBuilder stringBuilder = new StringBuilder();
        // Read line by line.
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            messageText.setText("Cannot read from the file. Perhaps the file is suddenly removed.");
            return;
        } finally {
            String content = stringBuilder.toString();
            String finalMsg = "Opened internal " + (persistentOrCache ? "persistent " : "cache ") +
                    "file named " + fileName + " with content: " + content;
            messageText.setText(finalMsg);
        }
    }

    /**
     * Checks if a file exists in the External directories and reads it,
     * with the filename read from the text input and whether or not cache depending on the checkbox.
     * Sets corresponding messages in the message textview.
     */
    void readExternalFile() {
        // Check if an external drive is available at least for read
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) &&
           !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            messageText.setText("No external storage is available for read.");
            return;
        }

        // true -> persistent; false -> cache.
        boolean persistentOrCache = !cacheCheckBox.isChecked();
        String fileName = fileNameText.getText().toString();

        // Open the file
        File file = null;
        if(persistentOrCache) {
            // Open a persistent file.
            file = new File(this.getExternalFilesDir(null), fileName);
        }
        else {
            // Open a cache file
            file = new File(this.getExternalCacheDir(), fileName);
        }

        // Read from the file.
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            messageText.setText("Cannot open the file. Perhaps it does not exist.");
            return;
        }
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis);
        StringBuilder stringBuilder = new StringBuilder();
        // Read line by line.
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            messageText.setText("Cannot read from the file. Perhaps the file is suddenly removed.");
            return;
        } finally {
            String content = stringBuilder.toString();
            String finalMsg = "Opened external " + (persistentOrCache ? "persistent " : "cache ") +
                    "file named " + fileName + " with content: " + content;
            messageText.setText(finalMsg);
        }
    }
}