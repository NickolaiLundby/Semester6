package nickolai.lisberg.lundby.networkworkwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import nickolai.lisberg.lundby.networkworkwork.WeatherResponse.WeatherResponse;

public class VolleyActivity extends AppCompatActivity {
    Button btnToMain, btnGetWeather, btnParseJson;
    TextView txtWeatherResult, txtWeatherJson;
    String downloadResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        btnToMain = findViewById(R.id.btn_toMain);
        btnGetWeather = findViewById(R.id.btn_volleyGetWeather);
        btnParseJson = findViewById(R.id.btn_volleyParseJson);
        txtWeatherResult = findViewById(R.id.tv_volleyWeatherResult);
        txtWeatherJson = findViewById(R.id.tv_volleyWeatherJson);
        btnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnToMainClicked();
            }
        });
        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnGetWeatherClicked();
            }
        });
        btnParseJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnParseJsonClicked();
            }
        });
    }

    private void BtnToMainClicked()
    {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void BtnGetWeatherClicked()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = MainActivity.UrlBuilder();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        txtWeatherResult.setText(response);
                        downloadResult = response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtWeatherResult.setText(getString(R.string.connection_error));
            }
        });

        queue.add(stringRequest);
    }

    private void BtnParseJsonClicked()
    {
        try {
            Gson gson = new Gson();
            WeatherResponse wr = gson.fromJson(downloadResult, WeatherResponse.class);
            txtWeatherJson.setText(String.format("%.2f", wr.getMain().getTemp() - 272.15));
        } catch (Exception e) {
            txtWeatherJson.setText(getString(R.string.parse_error));
        }

    }
}
