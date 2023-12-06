// Don't change START
package com.can301.gp.demos;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.can301.gp.Demonstration;
import com.can301.gp.MainActivity;
import com.can301.gp.R;
import com.can301.gp.codepage.CodePage;

import java.util.Objects;

public class EffectExampleNightMode extends AppCompatActivity {

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


// Don't change END

    // Change this to exactly the string as in the AndroidManifest.xml
    public static final String EFFECT_ACTIVITY_NAME = ".demos.EffectExampleNightMode";
    private SwitchCompat switchCompat;
    private TextView effectNameTextView, nightModeTextView, text1, text2, text3, text4, switchText;
    private CardView card1, card2, card3, card4;
    private ConstraintLayout screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change here to the layout name
        setContentView(R.layout.activity_effect_darkmode);

        switchText = findViewById(R.id.switchText);
        screen = findViewById(R.id.screen);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        nightModeTextView = findViewById(R.id.nightModeTextView);
        effectNameTextView = findViewById(R.id.effectExampleName);
        switchCompat = findViewById(R.id.modeSwitch);
        Button homeBtn = findViewById(R.id.homeButton);
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

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // switch on night mode
                    /*AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);*/
                    screen.setBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.darkgray));

                    switchText.setText("Night Mode");
                    switchText.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.white));

                    effectNameTextView.setBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this, R.color.lightpurple));
                    effectNameTextView.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.redpurple));

                    nightModeTextView.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.white));

                    card1.setCardBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.mediangray));
                    card2.setCardBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.mediangray));
                    card3.setCardBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.mediangray));
                    card4.setCardBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.mediangray));

                    text1.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.white));
                    text2.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.white));
                    text3.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.white));
                    text4.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.white));

                    homeBtn.setBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this, R.color.lightpurple));
                    homeBtn.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this, R.color.redpurple));
                    docLinkBtn.setBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this, R.color.lightpurple));
                    docLinkBtn.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this, R.color.redpurple));

                    effectButton.setBackgroundResource(R.drawable.button_night_effect);
                    effectButton.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this, R.color.darkgray));
                    codeButton.setBackgroundResource(R.drawable.button_night_code);
                    codeButton.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this, R.color.redpurple));

                } else {
                    // switch off night mode
                    /*AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);*/
                    screen.setBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.white));

                    switchText.setText("Light Mode");
                    switchText.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.black));

                    effectNameTextView.setBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this, R.color.darkpurple));
                    effectNameTextView.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.white));

                    nightModeTextView.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.black));

                    card1.setCardBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.white));
                    card2.setCardBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.white));
                    card3.setCardBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.white));
                    card4.setCardBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.white));

                    text1.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.black));
                    text2.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.black));
                    text3.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.black));
                    text4.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this,R.color.black));

                    homeBtn.setBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this, R.color.darkpurple));
                    homeBtn.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this, R.color.white));
                    docLinkBtn.setBackgroundColor(ContextCompat.getColor(EffectExampleNightMode.this, R.color.darkpurple));
                    docLinkBtn.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this, R.color.white));
                    effectButton.setBackgroundResource(R.drawable.button_light_effect);
                    effectButton.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this, R.color.mediangray));
                    codeButton.setBackgroundResource(R.drawable.button_light_code);
                    codeButton.setTextColor(ContextCompat.getColor(EffectExampleNightMode.this, R.color.white));

                }
            }
        });

        /*// Allow to stay in night mode
        boolean isNightModeOn = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        switchCompat.setChecked(isNightModeOn);
        if (isNightModeOn) {
            switchCompat.setText("Night Mode  ");
            switchCompat.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        } else {
            switchCompat.setText("Light Mode  ");
            switchCompat.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        }*/
    }


}




