package nickolai.lisberg.lundby.atyourservice.Services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import nickolai.lisberg.lundby.atyourservice.Activities.MainActivity;

public class BackgroundService extends Service {
    int counter;
    boolean running;
    public BackgroundService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getBaseContext(), "BackgroundService started", Toast.LENGTH_SHORT).show();
        running = true;
        InfiniteLoopOfDoom();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getBaseContext(), "BackgroundService stopped", Toast.LENGTH_SHORT).show();
        running = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void InfiniteLoopOfDoom(){
        AsyncTask at = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try{
                    Thread.sleep(5000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                InfiniteLoopOfDoom();
            }
        };
        if(running){
            counter++;
            at.execute();
            sendMyBroadCast();
        }
    }

    private void sendMyBroadCast()
    {
        try
        {
            Intent broadCastIntent = new Intent();
            broadCastIntent.setAction(MainActivity.BROADCAST);

            broadCastIntent.putExtra(MainActivity.COUNTER, counter);

            sendBroadcast(broadCastIntent);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
