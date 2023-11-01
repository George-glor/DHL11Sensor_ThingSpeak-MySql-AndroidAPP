package com.example.sensor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView dataDisplay;
    private Button ViewButton;
    private Handler handler;
    private Runnable refreshRunnable;
    private int refreshInterval = 20000; //autmaticly refresh every 2000 ms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataDisplay = findViewById(R.id.dataDisplay);
        ViewButton = findViewById(R.id.fetchButton);

        ViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchDataFromThingSpeak();
            }
        });

        handler = new Handler();
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                fetchDataFromThingSpeak();
                handler.postDelayed(this, refreshInterval);
            }
        };

        // Start periodic updates
        handler.post(refreshRunnable);
    }

    private void fetchDataFromThingSpeak() {
        new FetchDataFromThingSpeak().execute();
    }

    private class FetchDataFromThingSpeak extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://api.thingspeak.com/channels/2320519/feeds/last.json?api_key=R8R0DO6QOJYY4M9A");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                return response.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "Error fetching data from ThingSpeak: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                // Parse the JSON response
                JSONObject jsonObject = new JSONObject(result);
                int temperature = jsonObject.getInt("field1");
                int humidity = jsonObject.getInt("field2");
                String timestamp = jsonObject.getString("created_at");

                // Visa temp och fuktighet
                String data = "Time: " + timestamp + "\nTemperature: " + temperature + "°C\nHumidity: " + humidity + "%";
                dataDisplay.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();
                dataDisplay.setText("Error parsing JSON: " + e.getMessage());// om det finns något fel ska denna kod visa vad är felet
            }
        }
    }
}
