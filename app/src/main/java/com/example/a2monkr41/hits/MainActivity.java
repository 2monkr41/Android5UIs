package com.example.a2monkr41.hits;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.AsyncTask;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import android.app.AlertDialog;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    class MyTask extends AsyncTask<Void,Void,String>
    {
        public String doInBackground(Void... unused)
        {
            HttpURLConnection conn = null;
            try
            {
                URL url = new URL("http://www.free-map.org.uk/course/mad/ws/hits.php?artist=Oasis");
                conn = (HttpURLConnection) url.openConnection();

                String postData = "artist=Oasis";
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(postData.length());

                OutputStream out = null;
                out = conn.getOutputStream();
                out.write(postData.getBytes());
                if(conn.getResponseCode() == 200)
                {
                    InputStream in = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String all = "", line;
                    while((line = br.readLine()) !=null)
                        all += line;
                    return all;
                }
                else
                    return "HTTP ERROR: " + conn.getResponseCode();


            }
            catch(IOException e)
            {
                return e.toString();
            }
            finally
            {
                if(conn!=null)
                    conn.disconnect();
            }
        }

        public void onPostExecute(String result)
        {
            TextView tv1 = (TextView)findViewById(R.id.tv1);
            tv1.setText(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button go = (Button)findViewById(R.id.btn1);
        go.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        MyTask t = new MyTask();
        TextView tv1 = (TextView)findViewById(R.id.tv1);
        EditText et1 = (EditText)findViewById(R.id.et1);
        int input = Integer.parseInt(et1.getText().toString());
        t.execute();
    }
}

