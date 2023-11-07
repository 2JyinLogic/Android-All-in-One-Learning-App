// Don't change START
package com.can301.gp.demos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.can301.gp.Demonstration;
import com.can301.gp.R;
import com.can301.gp.codepage.CodePage;

public class AnimationsExample extends AppCompatActivity {

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
    public static final String EFFECT_ACTIVITY_NAME = ".demos.AnimationsExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change here to the layout name
        setContentView(R.layout.activity_animations_example);

        Button effectButton = findViewById(R.id.effectBottomButton);
        Button codeButton = findViewById(R.id.codeBottomButton);

        ImageView imageview=findViewById(R.id.imageview);

        Button alpha =findViewById(R.id.alpha_button);
        Button rotate =findViewById(R.id.rotate_button);
        Button scale =findViewById(R.id.scale_button);
        Button translate =findViewById(R.id.translate_button);

        alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation=AnimationUtils.loadAnimation(AnimationsExample.this,R.anim.alpha);
                imageview.startAnimation(animation);
            }
        });
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation=AnimationUtils.loadAnimation(AnimationsExample.this,R.anim.rotate);
                imageview.startAnimation(animation);
            }
        });
        scale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation=AnimationUtils.loadAnimation(AnimationsExample.this,R.anim.scale);
                imageview.startAnimation(animation);
            }
        });
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation=AnimationUtils.loadAnimation(AnimationsExample.this,R.anim.translate);
                imageview.startAnimation(animation);
            }
        });

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Animation animation=AnimationUtils.loadAnimation(AnimationsExample.this,R.anim.alpha);
//                Animation animation=AnimationUtils.loadAnimation(AnimationsExample.this,R.anim.rotate);
//                Animation animation=AnimationUtils.loadAnimation(AnimationsExample.this,R.anim.scale);
                Animation animation=AnimationUtils.loadAnimation(AnimationsExample.this,R.anim.translate);
                imageview.startAnimation(animation);
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
        demoTitle = inIntent.getStringExtra(Demonstration.EFFECT_DEMO_TITLE_KEY);
        codeId = inIntent.getStringExtra(Demonstration.EFFECT_DEMO_CODE_ID_KEY);

        // Set information about this effect
        TextView effectTextView = findViewById(R.id.effectExampleName);
        effectTextView.setText(demoTitle);

        // Go to the corresponding code page
        codeButton.setOnClickListener(v -> goToCodePage());
    }

}
