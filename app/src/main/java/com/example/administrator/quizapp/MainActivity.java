package com.example.administrator.quizapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.quizapp.lib.ApiHelper;
import com.example.administrator.quizapp.lib.AppHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Intent i;
    EditText etEmail;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i = new Intent(this, HomeActivity.class);

        if (AppHelper.userEmail != null) {
            startActivity(i);
        }

        etEmail = findViewById(R.id.etEmail);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = etEmail.getText().toString();
                if (!text.equalsIgnoreCase("")) {
                    startActivity(i);
                    AppHelper.saveUser(text);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new getUser().execute(ApiHelper.SERVER + ApiHelper.USERS + "/get_by_email");
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Please enter your email", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class getUser extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                JSONObject obj = new JSONObject();
                obj.put("email", AppHelper.userEmail);

                return ApiHelper.POST_URL(url, obj);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("ttt", s);
            try {
                JSONObject obj = new JSONObject(s);
                AppHelper.userName = obj.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
