package nickolai.lisberg.lundby.atyourservice.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class LocalService extends Service {
    private final IBinder binder = new LocalBinder();
    private final int mNumber = 42;

    public class LocalBinder extends Binder {
        public LocalService getService() {
            return LocalService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public int getFortyTwo(){
        return mNumber;
    }
}
