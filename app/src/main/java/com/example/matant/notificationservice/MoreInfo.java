package com.example.matant.notificationservice;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.String;

/**
 * Created by matant on 4/8/2015.
 */
public class MoreInfo extends Activity {
    String result = "";
    TableLayout tl;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        StrictMode.enableDefaults();
        setContentView(R.layout.notifc_message);
        tl = (TableLayout)findViewById(R.id.table);
        getData();

    }
    public void getData(){
        String res = "";
        InputStream isr = null;
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2:80/android/users.php");
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();
        }
        catch (Exception e){
            Log.e("log_tag", "Error in http connection " + e.toString());
        }
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine())!= null){
                Log.i("print:",line);
                sb.append(line+ "\n");
            }
            isr.close();
            result = sb.toString();
        } catch (Exception  e) {
            Log.e("log_tag", "Error in Convert " + e.toString());
        }
        try {
            String s = "";
            JSONArray jarray = new JSONArray(result);

            Log.i("size",String.valueOf(jarray.length()) );

            for(int i=0; i< jarray.length();i++)
            {
                Log.i("Check1","parsing the json");
                JSONObject json = jarray.getJSONObject(i);
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                lp.setMargins(2,2,2,2);
                row.setLayoutParams(lp);
                Log.i("Check2","parsing the json");
                row.setBackgroundColor(0xFFFFFFFF);
                TextView id = new TextView(this);
                TextView name = new TextView(this);
                TextView age = new TextView(this);
                id.setBackground(getResources().getDrawable(R.drawable.border));
                id.setTextColor(0xFFFFFFFF);
                id.setText(json.getString("id"));
                name.setBackground(getResources().getDrawable(R.drawable.border));
                name.setTextColor(0xFFFFFFFF);
                name.setText(json.getString("name"));
                age.setBackground(getResources().getDrawable(R.drawable.border));
                age.setTextColor(0xFFFFFFFF);
                age.setText(json.getString("age"));
                Log.i("Check3","parsing the json");
                row.addView(id);
                row.addView(name);
                row.addView(age);
                //add the new row to table
                tl.addView(row,new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
                //the new row was added
            }
            Log.i("Check4","parsing the json");
        } catch (JSONException e) {
            Log.e("Parsing","Error Parsing Data"+e.toString());
        }
    }

}
