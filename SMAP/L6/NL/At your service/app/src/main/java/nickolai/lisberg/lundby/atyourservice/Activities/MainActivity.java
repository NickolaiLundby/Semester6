package nickolai.lisberg.lundby.atyourservice.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import nickolai.lisberg.lundby.atyourservice.R;
import nickolai.lisberg.lundby.atyourservice.Services.BackgroundService;

public class MainActivity extends AppCompatActivity {
    Button btnStartBackground, btnStopBackground;

    public final static String BROADCAST = "Broadcast.string.receiver";
    public final static String COUNTER = "Counter.from.Service";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartBackground = findViewById(R.id.btn_startbackground);
        btnStopBackground = findViewById(R.id.btn_stopbackground);

        RegisterMyReceiver();

        btnStartBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnStartBackground();
            }
        });
        btnStopBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnStopBackground();
            }
        });
    }

    private void BtnStartBackground()
    {
        Intent serviceIntent = new Intent(this, BackgroundService.class);
        startService(serviceIntent);
    }

    private void BtnStopBackground()
    {
        Intent serviceIntent = new Intent(this, BackgroundService.class);
        stopService(serviceIntent);
    }

    private void RegisterMyReceiver(){
        try
        {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BROADCAST);
            registerReceiver(receiver, intentFilter);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getBaseContext(), String.valueOf(intent.getExtras().getInt(COUNTER)), Toast.LENGTH_SHORT).show();
        }
    };
}
