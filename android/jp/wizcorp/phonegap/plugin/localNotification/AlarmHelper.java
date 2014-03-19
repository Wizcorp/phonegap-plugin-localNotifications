package jp.wizcorp.phonegap.plugin.localNotification;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import android.R.dimen;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.os.*;

/**
 * Helper class for the LocalNotification plugin. This class is reused by the
 * AlarmRestoreOnBoot.
 * 
 * @see LocalNotification
 * @see AlarmRestoreOnBoot
 * 
 * @author dvtoever
 */
public class AlarmHelper {

    private Context ctx;

    public AlarmHelper(Context context) {
    	this.ctx = context;
    }

    /**
     * @see LocalNotification#add(boolean, String, String, String, int,
     *      Calendar)
     */
    public boolean addAlarm(String alarmTitle, String alarmSubTitle, String alarmTicker,
	    String notificationId, Long seconds) {
    	
		long triggerTime = seconds;
		
		Intent intent = new Intent(this.ctx, AlarmReceiver.class)
			.setAction(notificationId)
			.putExtra(AlarmReceiver.TITLE, alarmTitle)
			.putExtra(AlarmReceiver.SUBTITLE, alarmSubTitle)
			.putExtra(AlarmReceiver.TICKER_TEXT, alarmTicker)
			.putExtra(AlarmReceiver.NOTIFICATION_ID, notificationId);
	
		PendingIntent sender = PendingIntent.getBroadcast(this.ctx, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
	
		getAlarmManager().set(AlarmManager.RTC_WAKEUP, triggerTime, sender);
	
		return true;
    }

    /**
     * @see LocalNotification#cancelNotification(int)
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
}
