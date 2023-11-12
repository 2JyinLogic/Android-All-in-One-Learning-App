// Don't change START
package com.can301.gp.demos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.can301.gp.Demonstration;
import com.can301.gp.R;
import com.can301.gp.codepage.CodePage;

public class RadioGroupWidget extends AppCompatActivity {

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
    public static final String EFFECT_ACTIVITY_NAME = ".demos.RadioGroupWidget";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change here to the layout name
        setContentView(R.layout.activity_radio_group_widget);

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

        RadioGroup AradioGroup = findViewById(R.id.radioGroup);
        TextView display = findViewById(R.id.textView8);
        AradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton){
                    display.setText("You have chosen radio button 1");
                }
                else if(checkedId == R.id.radioButton2){
                    display.setText("You have chosen radio button 2");
                }
                else if(checkedId == R.id.radioButton5){
                    display.setText("You have chosen radio button 3");
                }
            }
        });
    }


}