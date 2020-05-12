package com.goranm.moranchatapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String notification_title = remoteMessage.getNotification().getTitle();
        String notification_message = remoteMessage.getNotification().getBody();

        String clickAction = remoteMessage.getNotification().getClickAction();
        String fromUserID = remoteMessage.getData().get("from_user_id");


        Intent resultIntent = new Intent(clickAction);
        resultIntent.putExtra("user_id",fromUserID);

        PendingIntent resultPending = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notifyBuilder = new Notification.Builder(this)
                .setContentIntent(resultPending)
                .setContentTitle(notification_title)
                .setContentText(notification_message)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
                .build();



        int mNotificationId = (int) System.currentTimeMillis();
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        assert mNotifyMgr != null;
        mNotifyMgr.notify(mNotificationId, notifyBuilder);
    }
}
