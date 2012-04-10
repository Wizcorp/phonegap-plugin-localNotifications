package jp.wizcorp.phonegap.plugin.LocalNotification;

import jp.wizcorp.android.shell.AndroidShellActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;
import com.phonegap.api.PluginResult.Status;

/**
 * 
 * @author Ally Ogilvie
 * @copyright WizCorp Inc. [ Incorporated Wizards ] 2011
 * @file LocalNotificationPlugin for PhoneGap
 *
 */
public class LocalNotificationPlugin extends Plugin {

	/*
	 * 
	 * JavaScript Usage ->
	 * a is an array (send empty array if nothing to declare)
	 * s is success callback
	 * f is fail callback
	 * 
	 * var LocalNotification { 
	 * 
	 * 		add : function (id , options) {
	 *			return PhoneGap.exec( null, null, 'LocalNotification', 'addNotification', [id, options] );	
	 *		}
	 * 
	 * }
	 * 
	 * 
	 * 
	 */
	
	
	static Plugin thisPlugin;
	
	@Override
	public PluginResult execute(String action, JSONArray data, String callbackId)  {
		
		PluginResult result = null;
		thisPlugin = this;
		
		Activity mAct = AndroidShellActivity.getActivity();
		AlarmManager am = (AlarmManager) mAct.getSystemService(Context.ALARM_SERVICE);
		
		if (action.equals("addNotification")) {
			
		
			// Add a new notification - params : id, options
			Log.d("LocalNotificationPlugin", "[addNotification] ******* "+data.toString() );
			int msgId;
			JSONObject msgObject;
			
			
			try {
				// msgId = data.getInt(0);					// for int
				String msgIdString = data.getString(0);		// for string
				msgId = getIntLookupFor(msgIdString);
				if (msgId == 999999) { return null; };
				msgObject = data.getJSONObject(1);
				
				// extract message content from game
				int seconds = msgObject.getInt("seconds");
				String message = msgObject.getString("message");
				
				// prepare intent
				Intent intent = new Intent(mAct, LocalNotificationManager.class);
				intent.putExtra("alarm_message", message);
				
				
				// create full intent with msgId
				PendingIntent sender = PendingIntent.getBroadcast(mAct, msgId, intent, PendingIntent.FLAG_ONE_SHOT);
							
				// get and set the AlarmManager service
				am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (seconds * 1000), sender);
				
				result = new PluginResult(Status.OK);
				
			} catch (JSONException e) {
				Log.e("LocalNotificationPlugin", "JSON error ******* "+e);
				result = new PluginResult(Status.ERROR);
			}
			 
			
		} else if (action.equals("cancelNotification")) {
			// Cancel a notification - params : id
			Log.d("LocalNotificationPlugin", "[cancelNotification] ******* ");
			
			// get id from data
			int msgId;
			try {
				
				// msgId = data.getInt(0);					// for int
				String msgIdString = data.getString(0);		// for string
				msgId = getIntLookupFor(msgIdString);
				if (msgId == 999999) { return null; };
				
				// get the intent from id
				Intent intent = new Intent(mAct, LocalNotificationManager.class);
				PendingIntent sender = PendingIntent.getBroadcast(mAct, msgId, intent, PendingIntent.FLAG_ONE_SHOT);

				am.cancel(sender);
				
			} catch (JSONException e) {
				Log.e("LocalNotificationPlugin", "JSON error ******* "+e);
				result = new PluginResult(Status.ERROR);
			}
			
			
			
			
			
		} else if (action.equals("cancelAllNotifications")) {
			// Cancel all notifications
			Log.d("LocalNotificationPlugin", "[cancelAllNotifications] ******* ");
			
		}
		
		
		return result;
	}

	private int getIntLookupFor(String msgIdString) {
		// FOR COMPATIBILITY WITH IOS, IOS SETS STRING TYPE FOR NOTIFICATION ID
		// hard set lookup if you want strings
		int giveInt = 999999;
		if (msgIdString.equals("stamina")) {
			giveInt = 1;
		} else if (msgIdString.equals("atk")) {
			giveInt = 2;
		} else if (msgIdString.equals("energy")) {
			giveInt = 3;
		} else if (msgIdString.equals("def")) {
			giveInt = 4;
		} else if (msgIdString.equals("other")) {
			giveInt = 4;
		}
		
		
		return giveInt;
	}
	
	
	
	
	
	
	
}
