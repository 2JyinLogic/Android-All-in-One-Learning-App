package com.can301.gp.demos;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.can301.gp.Demonstration;
import com.can301.gp.MainActivity;
import com.can301.gp.R;
import com.can301.gp.codepage.CodePage;
import android.app.DatePickerDialog;
import java.util.Calendar;

public class DatePickerDialogExample extends AppCompatActivity {

    private String demoTitle;
    // codeId is needed for the code page to load the corresponding code
    // and for this activity to load the documentation link
    private String codeId;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();

    public static final String EFFECT_ACTIVITY_NAME = ".demos.DatePickerDialogExample";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepickerdialog_example);

        // Initialize buttons for start and end date
        Button startDateButton = findViewById(R.id.startDateButton);
        Button endDateButton = findViewById(R.id.endDateButton);

        startDateButton.setOnClickListener(v -> showStartDatePickerDialog());
        endDateButton.setOnClickListener(v -> showEndDatePickerDialog());

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
    }
    // New method to show DatePickerDialog

    private void showStartDatePickerDialog() {
        DatePickerDialog startDatePicker = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            startDate.set(Calendar.YEAR, year);
            startDate.set(Calendar.MONTH, month);
            startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Format the date as you like, e.g., "dd/MM/yyyy"
            String dateString = dayOfMonth + "/" + (month + 1) + "/" + year; // Adding 1 to month as Calendar.MONTH is zero-based
            updateStartDateInView(dateString);
        }, startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));

        startDatePicker.show();
    }

    // Helper method to update the TextView with the selected start date
    private void updateStartDateInView(String dateString) {
        TextView selectedStartDateTextView = findViewById(R.id.selectedStartDateText);
        selectedStartDateTextView.setText("Selected Start Date: " + dateString);
    }

    private void showEndDatePickerDialog() {
        DatePickerDialog endDatePicker = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            endDate.set(Calendar.YEAR, year);
            endDate.set(Calendar.MONTH, month);
            endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Format the date as you like, e.g., "dd/MM/yyyy"
            String dateString = dayOfMonth + "/" + (month + 1) + "/" + year; // Adding 1 to month as Calendar.MONTH is zero-based
            updateEndDateInView(dateString);
        }, endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));

        endDatePicker.show();
    }

    // Helper method to update the TextView with the selected end date
    private void updateEndDateInView(String dateString) {
        TextView selectedEndDateTextView = findViewById(R.id.selectedEndDateText);
        selectedEndDateTextView.setText("Selected End Date: " + dateString);
    }

//    private void showDatePickerDialog() {
//        // Get Current Date
//        final Calendar c = Calendar.getInstance();
//        int mYear = c.get(Calendar.YEAR); // current year
//        int mMonth = c.get(Calendar.MONTH); // current month
//        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
//
//        // Date Picker Dialog
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year,
//                                          int monthOfYear, int dayOfMonth) {
//                        // Do something with the date chosen by the user
//                    }
//                }, mYear, mMonth, mDay);
//        datePickerDialog.show();
//    }

}