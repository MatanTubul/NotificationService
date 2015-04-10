package com.example.matant.notificationservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    Button notify,snotify;
    NotificationManager notificationManager;
    boolean isNotifyActive=false;
    int notifID = 33;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notify = (Button)findViewById(R.id.btnNotify);
        snotify = (Button)findViewById(R.id.btnSNotify);
        name = (EditText)findViewById(R.id.editTextName);



    }




    public void stopNotification(View view) {
        if(isNotifyActive == true){
            notificationManager.cancel(notifID);

        }

    }


    public void ShowNotification(View view) {

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this).setContentTitle("אירוע חדש").setContentText(name.getText()+" "+"מזמין אותך למשחק").setTicker("Alert")
                .setSmallIcon(R.drawable.messagepopup);
        Intent moreinfo = new Intent(this,MoreInfo.class);

        TaskStackBuilder tStackBuilder = TaskStackBuilder.create(this);
        tStackBuilder.addParentStack(MoreInfo.class);
        tStackBuilder.addNextIntent(moreinfo);

        PendingIntent pend = tStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        notifBuilder.setContentIntent(pend);

        notificationManager = (NotificationManager) getSystemService((Context.NOTIFICATION_SERVICE));
        notificationManager.notify(notifID ,notifBuilder.build());

        isNotifyActive = true;
    }
}
