package lee.scut.edu.notificationmonitor;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (!isNotificationListenEnabled()) {
                startActivityForResult(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS),100);
                Toast.makeText(getApplicationContext(), "请授权APP使用通知栏", Toast.LENGTH_SHORT).show();
            }else {
                startService(new Intent(this, NotificationMonitor.class));
            }
        }else{
            startService(new Intent(this,AccessibilityMonitor.class));
        }
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
                            .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 1, new Intent(getApplicationContext(),MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                            .build();
                }else{
                    notification = new Notification();
                    notification.contentIntent = PendingIntent.getActivity(getApplicationContext(), 1, new Intent(getApplicationContext(),MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.defaults = Notification.DEFAULT_ALL;
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    notification.icon = android.R.drawable.ic_dialog_alert;
                    notification.tickerText = "测试通知";
                }
                notificationManager.notify(100,notification);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isNotificationListenEnabled(){
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
