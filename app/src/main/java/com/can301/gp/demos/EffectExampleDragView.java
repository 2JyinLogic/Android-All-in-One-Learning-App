package com.can301.gp.demos;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.can301.gp.Demonstration;
import com.can301.gp.MainActivity;
import com.can301.gp.R;
import com.can301.gp.codepage.CodePage;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skyfishjy.library.RippleBackground;

public class EffectExampleDragView extends AppCompatActivity {

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
     *
     * @param view
     */
    public void goHome(View view) {
        MainActivity.goBackToHomePage(this);
    }


// Don't change END

    // Change this to exactly the string as in the AndroidManifest.xml
    public static final String EFFECT_ACTIVITY_NAME = ".demos.EffectExampleDragView";
    private ImageView imageView1, imageView2, imageView3, imageView4;
    private float xDown = 0, yDown = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change here to the layout name
        setContentView(R.layout.activity_dragging);

        Button effectButton = findViewById(R.id.effectBottomButton);
        Button codeButton = findViewById(R.id.codeBottomButton);
        effectButton.setEnabled(false);
        codeButton.setEnabled(true);

        imageView1 = findViewById(R.id.draggingImage1);
        imageView2 = findViewById(R.id.draggingImage2);
        imageView3 = findViewById(R.id.draggingImage3);
        imageView4 = findViewById(R.id.draggingImage4);

        setDragListener(imageView1);
        setDragListener(imageView2);
        setDragListener(imageView3);
        setDragListener(imageView4);

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
    }

    private void setDragListener(ImageView imageView) {
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float movedX, movedY;
                        movedX = event.getX();
                        movedY = event.getY();

                        // calculate how much the user moved his finger
                        float distanceX = movedX - xDown;
                        float distanceY = movedY - yDown;

                        // Calculate the new position
                        float newX = imageView.getX() + distanceX;
                        float newY = imageView.getY() + distanceY;

                        // Get the parent layout's dimensions
                        float parentWidth = ((View) imageView.getParent()).getWidth();
                        float parentHeight = ((View) imageView.getParent()).getHeight();

                        // Ensure the image stays within the parent bounds
                        if (newX >= 0 && newX + imageView.getWidth() <= parentWidth) {
                            imageView.setX(newX);
                        }
                        if (newY >= 0 && newY + imageView.getHeight() <= parentHeight) {
                            imageView.setY(newY);
                        }
                        break;
                }
                return true;
            }
        });

    }
}
