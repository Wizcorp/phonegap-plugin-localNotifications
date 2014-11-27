package jp.wizcorp.phonegap.plugin.localNotification;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * This class is triggered upon reboot of the device. It needs to re-register
 * the alarms with the AlarmManager since these alarms are lost in case of
 * reboot.
 * 
 * @author dvtoever
 */
public class AlarmRestoreOnBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Obtain alarm details form Shared Preferences
        SharedPreferences alarmSettings = context.getSharedPreferences(LocalNotification.TAG, Context.MODE_PRIVATE);
        final AlarmHelper alarm = new AlarmHelper();
        alarm.setContext(context);
        final Map<String, ?> allAlarms = alarmSettings.getAll();

        /*
         * For each alarm, parse its alarm options and register it again with
         * the Alarm Manager
         */
        for (String alarmId : allAlarms.keySet()) {
            try {
                JSONArray alarmDetails = new JSONArray(alarmSettings.getString(alarmId, ""));
                AlarmOptions options = new AlarmOptions(alarmDetails, context);

                alarm.addAlarm(options.getAlarmTitle(),
                        options.getAlarmSubTitle(),
                        options.getAlarmTicker(),
                        options.getNotificationId(),
                        options.getIcon(),
                        options.getCal().getTimeInMillis());

            } catch (JSONException e) {
                Log.d(LocalNotification.TAG,
                    "AlarmRestoreOnBoot: Error while restoring alarm details after reboot: " + e.toString());
            }

            Log.d(LocalNotification.TAG, "AlarmRestoreOnBoot: Successfully restored alarms upon reboot");
        }
    }
}
