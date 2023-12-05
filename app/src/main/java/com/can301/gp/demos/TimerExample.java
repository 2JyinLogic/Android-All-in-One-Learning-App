package com.can301.gp.demos;

// Don't change START

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import androidx.appcompat.app.AlertDialog;

import com.can301.gp.Demonstration;
import com.can301.gp.MainActivity;
import com.can301.gp.R;
import com.can301.gp.codepage.CodePage;

public class TimerExample extends AppCompatActivity {

    private String demoTitle;
    // codeId is needed for the code page to load the corresponding code
    // and for this activity to load the documentation link
    private String codeId;
    private TextView tvCountdown;
    private Button btnSet;
    private int count;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private SQLiteDatabase database;


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
    public static final String EFFECT_ACTIVITY_NAME = ".demos.TimerExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Change here to the layout name
        setContentView(R.layout.activity_timer_example);

        Button effectButton = findViewById(R.id.effectBottomButton);
        Button codeButton = findViewById(R.id.codeBottomButton);
        tvCountdown = findViewById(R.id.tv_countdown);
        btnSet = findViewById(R.id.btn_set);

        database = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath() + "/countdown.db", null);


        database.execSQL("CREATE TABLE IF NOT EXISTS countdown (id INTEGER PRIMARY KEY AUTOINCREMENT, time INTEGER)");


        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addAction("com.example.countdown");
        registerReceiver(receiver, intentFilter);
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



    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(TimerExample.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (count<1){
                    startCountdown(hourOfDay, minute);
                    count++;
                }

            }
        }, 0, 0, true);


        timePickerDialog.show();
    }


    private void startCountdown(int hour, int minute) {

        timeLeftInMillis = (hour * 60 + minute) * 60 * 1000;


        ContentValues values = new ContentValues();
        values.put("time", timeLeftInMillis);
        database.insert("countdown", null, values);


        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                showCountdownFinishedNotification();
                deleteCountdownFromDatabase();
                Intent intent = new Intent("com.example.countdown");
                sendBroadcast(intent);
                count--;
            }
        }.start();
    }


    private void updateCountdownText() {
        int hours = (int) (timeLeftInMillis / 1000 / 3600);
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600 / 60);
        int seconds = (int) (timeLeftInMillis / 1000 % 60);

        tvCountdown.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    // 显示倒计时结束通知
    private void showCountdownFinishedNotification() {
        Intent intent = new Intent(TimerExample.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(TimerExample.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(TimerExample.this, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("count reached zero")
                .setContentText("the time you set reached zero")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(TimerExample.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }
    // 广播接收器

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
            } else if ("com.example.countdown".equals(intent.getAction())) {
                Toast.makeText(TimerExample.this, "Countdown ends", Toast.LENGTH_SHORT).show();
            }
        }
    };


    private void deleteCountdownFromDatabase() {
        database.delete("countdown", null, null);
    }

    @SuppressLint("Range")
    @Override
    protected void onResume() {
        super.onResume();


        Cursor cursor = database.rawQuery("SELECT * FROM countdown", null);
        if (cursor.moveToFirst()) {
            timeLeftInMillis = cursor.getLong(cursor.getColumnIndex("time"));
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateCountdownText();
                }

                @Override
                public void onFinish() {
                    showCountdownFinishedNotification();
                    deleteCountdownFromDatabase();
                }
            }.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();


        ContentValues values = new ContentValues();
        values.put("time", timeLeftInMillis);
        database.update("countdown", values, null, null);


        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        database.close();
    }



}


