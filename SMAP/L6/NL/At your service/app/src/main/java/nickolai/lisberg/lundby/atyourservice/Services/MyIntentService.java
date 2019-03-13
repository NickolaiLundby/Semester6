package nickolai.lisberg.lundby.atyourservice.Services;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

public class MyIntentService extends IntentService {
    public final static String ACTION_FOO = "Intent.Action.Foo";
    public final static String ACTION_BAZ = "Intent.Action.Baz";

    public MyIntentService(){
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction() == ACTION_FOO){
            Toast.makeText(this, "Foo!", Toast.LENGTH_SHORT).show();
        }
        else if(intent.getAction() == ACTION_BAZ){
            Toast.makeText(this, "Baz!", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Default intent service started!", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Intent service destroyed!", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
