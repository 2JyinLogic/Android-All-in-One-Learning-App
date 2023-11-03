package com.can301.gp.demos;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BGServiceExampleService extends Service {
    public BGServiceExampleService() {
    }

    // Because the thread is running infinitely,
    // the service will be stuck in onStartCommand()
    // and can't be stopped. To stop it, has to stop the thread somehow.
    public static boolean stopService = false;
    // The integer to show on the UI
    public static int theInteger = 0;
    // Time to sleep between increasing the integer
    private float timeToSleep = .5f;
    // Do the job in a new thread or not?
    private boolean startNewThread = true;

    // Keys used to pass extras through the intent
    public static final String SLEEP_TIME_KEY = "Sleep time";
    public static final String START_NEW_THREAD_KEY = "New thread";

    /**
     * The work of increasing the integer.
     */
    private void work() {
        while(!stopService) {
            ++theInteger;
            try {
                Thread.sleep((long)(1000 * timeToSleep));
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Reset stop service on exit
        stopService = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Reset the integer
        theInteger = 0;

        timeToSleep = intent.getFloatExtra(SLEEP_TIME_KEY, .5f);
        startNewThread = intent.getBooleanExtra(START_NEW_THREAD_KEY, true);

        if(startNewThread) {
            // start a new thread to do the work
            new Thread(new Runnable() {
                public void run() {
                    // Do the work in the thread
                    work();
                }
            }).start();
        }
        else {
            // do the work in the thread
            work();
        }

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}