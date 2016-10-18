package lee.scut.edu.notificationmonitor;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationMonitor extends NotificationListenerService {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("lee..","Monitor starting");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Log.i("lee..","Notification posted");
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.i("lee..","Notification removed");
    }
}
