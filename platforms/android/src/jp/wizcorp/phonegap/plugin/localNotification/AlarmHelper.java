package jp.wizcorp.phonegap.plugin.localNotification;

import java.util.Calendar;
import java.util.Set;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * Helper class for the LocalNotification plugin. This class is reused by the
 * AlarmRestoreOnBoot.
 * 
 * @see LocalNotification
 * @see AlarmRestoreOnBoot
 * 
 * @author dvtoever
 */
public class AlarmHelper extends Activity {

    private Context ctx;

    // Called when our notification was clicked, will open the application to it's previous state
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        int notificationId = 0; // Default Id
        try {
            notificationId = bundle.getInt(AlarmReceiver.NOTIFICATION_ID);
            Log.d("AlarmHelper", "Opening Activity with: " + notificationId);
        } catch (Exception e) {
            try {
                notificationId = Integer.parseInt(bundle.getString(AlarmReceiver.NOTIFICATION_ID));
                Log.d("AlarmHelper", "Opening Activity with: " + notificationId);
            } catch (Exception e2) {
            }
        }
        launchMainIntent(notificationId);
    }

    public void setContext(Context applicationContext) {
        this.ctx = applicationContext;
    }

    /**
     * @see LocalNotification#add(boolean, String, String, String, int, Calendar)
     */
    public boolean addAlarm(String alarmTitle, String alarmSubTitle, String alarmTicker,
        String notificationId, int icon, Long seconds) {
        long triggerTime = seconds;

        Intent intent = new Intent(this.ctx, AlarmReceiver.class)
            .setAction(notificationId)
            .putExtra(AlarmReceiver.TITLE, alarmTitle)
            .putExtra(AlarmReceiver.SUBTITLE, alarmSubTitle)
            .putExtra(AlarmReceiver.TICKER_TEXT, alarmTicker)
            .putExtra(AlarmReceiver.ICON, icon)
            .putExtra(AlarmReceiver.NOTIFICATION_ID, notificationId);

        PendingIntent sender = PendingIntent.getBroadcast(this.ctx, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        getAlarmManager().set(AlarmManager.RTC_WAKEUP, triggerTime, sender);
        return true;
    }

    /**
     * @see LocalNotification#cancelNotification(String)
     */
    public boolean cancelAlarm(String notificationId) {
        /*
         * Create an intent that looks similar, to the one that was registered
         * using add. Making sure the notification id in the action is the same.
         * Now we can search for such an intent using the 'getService' method
         * and cancel it.
         */
        Intent intent = new Intent(this.ctx, AlarmReceiver.class)
            .setAction(notificationId);

        PendingIntent pi = PendingIntent.getBroadcast(this.ctx, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        try {
            getAlarmManager().cancel(pi);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * @see LocalNotification#cancelAllNotifications()
     */
    public boolean cancelAll(SharedPreferences alarmSettings) {
        Set<String> alarmIds = alarmSettings.getAll().keySet();

        for (String alarmId : alarmIds) {
            Log.d(LocalNotification.TAG, "Canceling notification with id: " + alarmId);
            cancelAlarm(alarmId);
        }

        return true;
    }

    private AlarmManager getAlarmManager() {
        return (AlarmManager) this.ctx.getSystemService(Context.ALARM_SERVICE);
    }

    private void launchMainIntent(int notificationId) {
        Context context     = getApplicationContext();
        String packageName  = context.getPackageName();
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(launchIntent);

        if (LocalNotification.getCordovaWebView() == null) {
            // Store in Shared Preferences and wait until application has finished launching
            context.getApplicationContext()
             .getSharedPreferences(LocalNotification.TAG, Context.MODE_PRIVATE)
             .edit()
             .putString("notificationTapped", "" + notificationId)
             .commit();
        }
    }
}
