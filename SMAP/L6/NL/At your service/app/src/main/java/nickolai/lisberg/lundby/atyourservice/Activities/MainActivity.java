package nickolai.lisberg.lundby.atyourservice.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import nickolai.lisberg.lundby.atyourservice.R;
import nickolai.lisberg.lundby.atyourservice.Services.BackgroundService;
import nickolai.lisberg.lundby.atyourservice.Services.LocalService;
import nickolai.lisberg.lundby.atyourservice.Services.MyIntentService;

public class MainActivity extends AppCompatActivity {
    Button btnStartBackground,
            btnStopBackground,
            btnStartBound,
            btnStopBound,
            btnCallBound,
            btnFoo,
            btnBaz;

    boolean mBound;
    LocalService mService;

    public final static String BROADCAST = "Broadcast.string.receiver";
    public final static String COUNTER = "Counter.from.Service";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBound = false;

        btnStartBackground = findViewById(R.id.btn_startbackground);
        btnStopBackground = findViewById(R.id.btn_stopbackground);
        btnStartBound = findViewById(R.id.btn_startbound);
        btnStopBound = findViewById(R.id.btn_stopbound);
        btnCallBound = findViewById(R.id.btn_callbound);
        btnFoo = findViewById(R.id.btn_intentFoo);
        btnBaz = findViewById(R.id.btn_intentBaz);

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
        btnStartBound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnBindToService();
            }
        });
        btnStopBound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnUnbindToService();
            }
        });
        btnCallBound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnCallBoundService();
            }
        });
        btnFoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnFooClicked();
            }
        });
        btnBaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnBazClicked();
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

    private void BtnBindToService()
    {
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void BtnUnbindToService()
    {
        unbindService(connection);
        mBound = false;
    }

    private void BtnCallBoundService()
    {
        if(mBound == true){
            int num = mService.getFortyTwo();
            Toast.makeText(this, "Number from bound service: " + num, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Not bound to service", Toast.LENGTH_SHORT).show();
        }
    }

    private void BtnFooClicked()
    {
        Intent fooIntent = new Intent(this, MyIntentService.class);
        fooIntent.setAction(MyIntentService.ACTION_FOO);
        startService(fooIntent);
    }

    private void BtnBazClicked()
    {
        Intent bazIntent = new Intent(this, MyIntentService.class);
        bazIntent.setAction(MyIntentService.ACTION_BAZ);
        startService(bazIntent);
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

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };
}
