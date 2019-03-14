package nickolai.lisberg.lundby.networkworkwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import nickolai.lisberg.lundby.networkworkwork.Utils.DownloadCallback;
import nickolai.lisberg.lundby.networkworkwork.Utils.DownloadTask;

public class MainActivity extends AppCompatActivity  implements DownloadCallback {
    Button btnCheckConnection, btnGetWeather;
    TextView weatherDetails;

    public static final String WEATHER_URL_KEY = "k";
    public static final String WEATHER_API_KEY = "32b48f79191e458466c3f517369b0d79";
    public static final String WEATHER_CITY_KEY = "2624652";

    private boolean downloading = false;
    private DownloadCallback<String> mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCallback = (DownloadCallback<String>) getBaseContext();

        btnCheckConnection = findViewById(R.id.btn_checkConnection);
        btnGetWeather = findViewById(R.id.btn_getAarhusWeather);
        btnCheckConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnCheckConnection();
            }
        });
        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnGetWeather();
            }
        });
        weatherDetails = findViewById(R.id.tv_weatherDetails);
    }

    private void BtnCheckConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info.isConnected()) {
                Toast.makeText(this, "Connected: " + info.toString(), Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Not connected!", Toast.LENGTH_SHORT).show();
        }
    }

    private void BtnGetWeather()
    {
        if (!downloading){
            DownloadTask dt = new DownloadTask(mCallback);
            dt.execute(UrlBuilder());
        }
        else
            Toast.makeText(this, "Already downloading", Toast.LENGTH_SHORT).show();
    }

    private String UrlBuilder(){
        String url = "api.openweathermap.org/data/2.5/weather?id=" + WEATHER_CITY_KEY + "&appid=" + WEATHER_API_KEY;
        return url;
    }


    @Override
    public void updateFromDownload(Object result) {
        weatherDetails.setText(result.toString());
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch(progressCode) {
            // You can add UI behavior for progress updates here.
            case Progress.ERROR:
                break;
            case Progress.CONNECT_SUCCESS:
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                break;
        }

    }

    @Override
    public void finishDownloading() {
        downloading = false;
    }
}
