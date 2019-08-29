package com.example.bifal.activity.firebaseNotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import com.example.bifal.R;
import com.example.bifal.activity.anaSayfa.AnaSayfaActivity;
import com.example.bifal.activity.fallarim.FallarimActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class FcmMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData().get("message"));
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);
    }
    private void showNotification(String message) {
        Intent ıntent = new Intent(this, FallarimActivity.class);
        ıntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ıntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setAutoCancel(true);
        builder.setContentTitle(getString(R.string.app_name));
        builder.setContentText(message);

        builder.setSmallIcon(R.drawable.ic_coffee);
        builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_coffee));
        builder.setContentIntent(pendingIntent);
        builder.setVibrate(new long[]{1000, 1000});
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
