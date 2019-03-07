package com.runekeena.atyourservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class MyBackgroundService extends Service {
    int counter;
    boolean running;

    public MyBackgroundService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
        /* TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented"); */
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        counter = 0;
        running = true;
        this.infiniteLoop();
        return super.onStartCommand(intent, flags, startId);
    }

    public void infiniteLoop(){
        if (running){
            AsyncTask myTask = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    infiniteLoop();
                }
            };

            counter++;
            myTask.execute();

            Intent stillLoopIntent = new Intent();
            stillLoopIntent.putExtra("msg",  "Loop: "+counter);
            stillLoopIntent.setAction("SOME_ACTION");
            sendBroadcast(stillLoopIntent);

        } else {
            Intent endLoopIntent = new Intent();
            endLoopIntent.putExtra("msg",  "Loop: "+counter+ " - Service stopped!");
            endLoopIntent.setAction("SOME_ACTION");
            sendBroadcast(endLoopIntent);
        }

    }

    @Override
    public void onDestroy() {
        running = false;
        super.onDestroy();
    }
}
