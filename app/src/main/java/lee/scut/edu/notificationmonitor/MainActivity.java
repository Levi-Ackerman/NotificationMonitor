package lee.scut.edu.notificationmonitor;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this,NotificationMonitor.class));
        findViewById(R.id.send_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    notification = new Notification.Builder(MainActivity.this)
                            .setSmallIcon(android.R.drawable.ic_dialog_alert)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setContentText("测试通知")
                            .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 1, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT))
                            .build();
                }else{
                    notification = new Notification();
                    notification.contentIntent = PendingIntent.getActivity(getApplicationContext(), 1, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.defaults = Notification.DEFAULT_ALL;
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    notification.icon = android.R.drawable.ic_dialog_alert;
                    notification.tickerText = "测试通知";
                }
                notificationManager.notify(100,notification);
            }
        });
    }
}
