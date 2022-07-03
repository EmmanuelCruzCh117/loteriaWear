package com.example.listacursos;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CardActivity extends Activity {
    private TextView titulo;
    private TextView descripcion;
    private Button cerrar;
    private Button registro;
    private int imagen;

    NotificationCompat.Builder notification;
    private static final int idUnico = 1234;
    String CHANNEL_ID = "Loteria";
    private NotificationManagerCompat notificationManager;
    private Intent intent;
    private PendingIntent pendingIntent;
    private NotificationCompat.WearableExtender wearableExtender;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);

        titulo = findViewById(R.id.txtDescripcion);
        descripcion = findViewById(R.id.txtDescripcion);
        registro = findViewById(R.id.btnRegistro);
        cerrar = findViewById(R.id.btnCerrar);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            titulo.setText(extras.getString("titulo"));
            descripcion.setText(extras.getString("descripcion"));
            imagen = extras.getInt("imagen");
        }

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNotification();
            }
        });
    }

    public void launchNotification(){
        notification = new NotificationCompat.Builder(CardActivity.this, CHANNEL_ID);
        notificationManager = NotificationManagerCompat.from(CardActivity.this);
        intent = new Intent(CardActivity.this, CardActivity.class);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            int importance = NotificationManager.IMPORTANCE_HIGH;
            String name = "Loteria";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(notificationChannel);
            pendingIntent = PendingIntent.getActivity(CardActivity.this, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);

            wearableExtender = new NotificationCompat.WearableExtender();

            notification.setSmallIcon(R.mipmap.ic_stat_name)
                    .setTicker("Reclamación Exitosa")
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle("Ganador")
                    .setContentText("Usted ha reclamado el premio " + titulo.getText())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setAutoCancel(true)
                    .extend(wearableExtender);
            notificationManager.notify(idUnico, notification.build());
        }
    }

}
