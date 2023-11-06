package com.can301.gp.demos;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.can301.gp.R;

/**
 * The example bound service,
 * which is both created and bound so that the music will continue playing
 * even after the application quits.
 */
public class BoundServiceExampleService extends Service {

    MediaPlayer player;

    /**
     * The binder class used for this bound service.
     */
    public class ExampleBinder extends Binder {
        MediaPlayer player;
        BoundServiceExampleService svc;

        ExampleBinder(MediaPlayer player, BoundServiceExampleService svc) {
            this.player = player;
            this.svc = svc;
        }

        boolean isPlaying() {
            return player.isPlaying();
        }

        /**
         * Pauses or resumes the music play, depending on the current state.
         * @return if the is music being played AFTER the method takes effect
         */
        boolean pauseResume() {
            if (isPlaying()) {
                player.pause();
            }
            else {
                player.start();
            }

            return isPlaying();
        }

        /**
         * @return The progress, in [0.f, 1.f]
         */
        float getProgress() {
            return (float)player.getCurrentPosition() / (float)player.getDuration();
        }

        /**
         * Go to the specific place of the music
         * @param place in [0.f, 1.f]
         */
        void goTo(float place) {
            player.seekTo((int)(player.getDuration() * place));
        }

        /**
         * Stops the music player service
         */
        void stopService() {
            svc.stopSelf();
        }
    }

    ExampleBinder binder;

    public BoundServiceExampleService() {
    }

    @Override
    public void onCreate() {
        // The service is being created
        player = MediaPlayer.create(this, R.raw.boundExampleMusic);
        binder = new ExampleBinder(player, this);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        // Nothing to do here. Initialisation is done in onCreate().

        // The example service is a music player service,
        // so let the intent be redelivered, although in this example no intent is used.
        return START_REDELIVER_INTENT;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
        return binder;
    }

    /**
     * Called after all clients have unbound with unbindService()
     *
     * @param intent The Intent that was used to bind to this service,
     * as given to {@link android.content.Context#bindService
     * Context.bindService}.  Note that any extras that were included with
     * the Intent at that point will <em>not</em> be seen here.
     *
     * @return whether rebinding is allowed
     */
    @Override
    public boolean onUnbind(Intent intent) {
        // allows rebinding.
        return true;
    }

    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
    }
}