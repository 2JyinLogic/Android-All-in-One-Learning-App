package com.can301.gp.codepage;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import com.can301.gp.Demonstration;
import com.can301.gp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * For the code webview to work,
 * Load Google's code-prettify that should be put into the "assets" folder.
 * (To create the assets folder, right-click the project root, New -> Folder -> Assets Folder)
 * Then, add this line:
 */
public class CodePage extends AppCompatActivity {

    // ID of the source code resource.
    private String codeId;
    // Title of the demostration
    private String demoTitle;

    // Class name of the activity of the thing that is displayed
    private String activityClassName;

    String codeTextString = "";
    String codeGitHubLinkString = "";

    private static String codeIdToTextFileName(String id) {
        // No extension name required.
        return "code_" + id;
    }
    private static String codeIdToGitHubLinkStringId(String id) {
        return "code_link_" + id;
    }

    void resetTexts() {
        codeTextString = "";
        codeGitHubLinkString = "";
    }

    public static final String CODE_ID_KEY = "code ID";
    public static final String CODE_CLASS_NAME_KEY = "code class name";

    /**
     * Opens the browser with the link to the source code
     * (execute when the user clicks the view button)
     * @param link to the source code
     */
    private void viewSourceCode(String link) {
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
            builder.setMessage("Cannot redirect you to the source code website " +
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
     * Switch to the corresponding effect activity whose
     * name declared in the manifest as @android:name should be activityClassName.
     */
    private void switchToEffectActivity() {
        Intent startEffectActivity = new Intent();
        startEffectActivity.setComponent(
                new ComponentName("com.can301.gp", activityClassName)
        );

        startEffectActivity.putExtra(Demonstration.EFFECT_DEMO_CODE_ID_KEY, codeId);
        startEffectActivity.putExtra(Demonstration.EFFECT_DEMO_TITLE_KEY, demoTitle);

        startActivity(startEffectActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the state from the intent, if it exists
        Intent intent = getIntent();
        if(intent.hasExtra(CODE_ID_KEY) && intent.hasExtra(CODE_CLASS_NAME_KEY)
                && intent.hasExtra(Demonstration.EFFECT_DEMO_TITLE_KEY)) {
            Intent inIntent = getIntent();

            codeId = inIntent.getStringExtra(CODE_ID_KEY);
            activityClassName = inIntent.getStringExtra(CODE_CLASS_NAME_KEY);
            demoTitle = inIntent.getStringExtra(Demonstration.EFFECT_DEMO_TITLE_KEY);
        }
        // If it does not exist, try to load from a previously destroyed instance,
        // if it was previously saved.
        else if (savedInstanceState != null) {
            codeId = savedInstanceState.getString(CODE_ID_KEY);
            activityClassName = savedInstanceState.getString(CODE_CLASS_NAME_KEY);
            demoTitle = savedInstanceState.getString(Demonstration.EFFECT_DEMO_TITLE_KEY);
        }
        // Otherwise, unable to establish the state.
        else {
            throw new RuntimeException("Please start my activity with both the id and the class name.");
        }

        // Load the source code and GitHub link for the code ID
        // First reset the texts
        {
            Resources resources = getResources();
            // equivalent to R.raw.codetextfilename
            int codeTextFileId = resources.getIdentifier(codeIdToTextFileName(codeId), "raw", getPackageName());
            // equivalent to R.string.codelinkstringid
            int codeLinkStringId = resources.getIdentifier(codeIdToGitHubLinkStringId(codeId), "string", getPackageName());

            // Read the source text from file
            InputStream is = resources.openRawResource(codeTextFileId);
            // An InputStream only reads bytes. Use this to read characters (a string)
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            do {
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (line != null) codeTextString += line + "\n";
            }
            while (line != null);

            codeGitHubLinkString = getString(codeLinkStringId);
        }

        setContentView(R.layout.activity_code_page);

        // Disable the "code" button and enable the "effect" button
        // The color of the buttons will change according to my bottom_button_style.xml
        // Also let the effect button redirect the user to the effect page
        {
            Button effectButton = findViewById(R.id.effectBottomButton);
            Button codeButton = findViewById(R.id.codeBottomButton);

            effectButton.setEnabled(true);
            codeButton.setEnabled(false);

            effectButton.setOnClickListener(v -> switchToEffectActivity());
        }

        // Supply the views with the data
        {
            Button linkTextButton = findViewById(R.id.codeLink);
            linkTextButton.setOnClickListener(v -> viewSourceCode(codeGitHubLinkString));

            // Do not use the code-prettify javascript and the stupid WebView
            // that is unable to load javascript to display the code...
            // Instead, use my CodeHighlighter and display it with a TextView
            TextView codeTextView = findViewById(R.id.codeText);

            String highlightedCode = new CodeHighlighter().highlight(codeTextString);
            // In HTML, \n doesn't mean a new line. Replace them with <br/>
            highlightedCode = highlightedCode.replace("\n", "<br/>");
            // In HTML, consequtive spaces are turned into one.
            // Do this to preserve all original spaces that are used to form a tab (four spaces)
            // Note: Do not replace all spaces with &nbsp's, or the coloring will fail to work!
            highlightedCode = highlightedCode.replace(
                    "    ", "&nbsp;&nbsp;&nbsp;&nbsp;"
            );
            codeTextView.setText(Html.fromHtml(highlightedCode, FROM_HTML_MODE_LEGACY));
        }
    }

    // Invoked when the activity might be temporarily destroyed;
    // save the instance state here.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(CODE_ID_KEY, codeId);
        outState.putString(CODE_CLASS_NAME_KEY, activityClassName);
        outState.putString(Demonstration.EFFECT_DEMO_TITLE_KEY, demoTitle);

        // Call superclass to save any view hierarchy.
        super.onSaveInstanceState(outState);
    }
}