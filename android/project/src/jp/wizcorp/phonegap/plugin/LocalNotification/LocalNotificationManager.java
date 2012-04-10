package jp.wizcorp.phonegap.plugin.LocalNotification;


import jp.wizcorp.android.shell.AndroidShellActivity;
import jp.wizcorp.android.shell.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author Ally Ogilvie
 * @copyright WizCorp Inc. [ Incorporated Wizards ] 2011
 * @file LocalNotificationManager.java
 * @about BroadcastReceiver for local notifications
 *
 */

public class LocalNotificationManager extends BroadcastReceiver {
	
	
	
	NotificationManager nm;

	@Override
	public void onReceive(Context context, Intent intent) {
		 
		 
		 try {
			 
			 // get Application name from app_name string
			 Resources appR = context.getApplicationContext().getResources();
			 CharSequence appName = appR.getText(appR.getIdentifier("app_name", "string", context.getApplicationContext().getPackageName()));

			 // give message
			 Bundle bundle = intent.getExtras();
			 String message = bundle.getString("alarm_message");
			 Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			 
			 // send to notification bar
			 nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			 
			 // do this intent when notification is selected (start main class)
			 Intent notificationIntent = new Intent(context, AndroidShellActivity.class);
			 PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
			 
			 Notification notif = new Notification(R.drawable.icon_notification, message, System.currentTimeMillis());
			 notif.setLatestEventInfo(context, appName, message, contentIntent);
			 notif.defaults |= Notification.DEFAULT_LIGHTS;
			 notif.defaults |= Notification.DEFAULT_VIBRATE;
			 notif.defaults |= Notification.DEFAULT_SOUND;
			 notif.defaults |= Notification.FLAG_AUTO_CANCEL;
			 
			 nm.notify(1, notif);

			 /*
			 File sndFile = new File("file:///android_asset/www/assets/audio/SE07-v1-U.mp3");
			 if (sndFile.exists()) {
				 notif.sound = Uri.parse("file:///android_asset/www/assets/audio/SE07-v1-U.mp3");
			 } else {
				 notif.sound = Uri.parse("file:///android_asset/www/assets/audio/SE07-v1-U.mp3");
				 // notif.defaults |= Notification.DEFAULT_SOUND;
			 }
			 */
			 
		 } catch (Exception e) {
			 Log.e("LocalNotificationManager", "[onReceive] ******* error "+e);
			 // Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
		 }
		 
		 
		 
	}
}