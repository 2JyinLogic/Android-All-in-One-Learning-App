// Don't change START
package com.can301.gp.demos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.can301.gp.Demonstration;
import com.can301.gp.MainActivity;
import com.can301.gp.R;
import com.can301.gp.codepage.CodePage;

public class FrameAnimationsExample extends AppCompatActivity {

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
     *
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
        } else {
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
    public static final String EFFECT_ACTIVITY_NAME = ".demos.FrameAnimationsExample";

    private boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change here to the layout name
        setContentView(R.layout.activity_frameanimations_example);

        Button effectButton = findViewById(R.id.effectBottomButton);
        Button codeButton = findViewById(R.id.codeBottomButton);
        Button frameAnimation = findViewById(R.id.frame_animation_button);
        View frameAnimationView = findViewById(R.id.frame_animation);
        AnimationDrawable frameDrawable=(AnimationDrawable) frameAnimationView.getBackground();
        frameAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    frameDrawable.start();
                    flag=false;
                }else {
                    frameDrawable.stop();
                    flag=true;
                }
            }
        });


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

        // Get the documentation link
        Resources resources = getResources();
        // equivalent to R.string.doclinkstringid

        int docLinkStringId = resources.getIdentifier(Demonstration.codeIdToDocLinkStringId(codeId), "string", getPackageName());
        String docLinkString = getString(docLinkStringId);

        Button docLinkBtn = findViewById(R.id.docLink);
        docLinkBtn.setOnClickListener(v -> viewDocumentationPage(docLinkString));

        // Set information about this effect
        TextView effectTextView = findViewById(R.id.effectExampleName);
        effectTextView.setText(demoTitle);

        // Go to the corresponding code page
        codeButton.setOnClickListener(v -> goToCodePage());
    }

}
