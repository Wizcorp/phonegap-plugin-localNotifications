package jp.wizcorp.phonegap.plugin.localNotification;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * This plugin utilizes the Android AlarmManager in combination with StatusBar
 * notifications. When a local notification is scheduled the alarm manager takes
 * care of firing the event. When the event is processed, a notification is put
 * in the Android status bar.
 * 
 * @author Daniel van 't Oever
 * @author Updated By Ally Ogilvie
 */
public class LocalNotification extends CordovaPlugin {

    public static final String TAG = "LocalNotification";

    /**
     * Delegate object that does the actual alarm registration. Is reused by the
     * AlarmRestoreOnBoot class.
     */
    private AlarmHelper alarm = null;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
	    // public PluginResult execute(String action, JSONArray optionsArr, String callBackId) {
		alarm = new AlarmHelper(cordova.getActivity().getApplicationContext());
		Log.d(TAG, "Plugin execute called with action: " + action);
	
		/*
		 * Determine which action of the plugin needs to be invoked
		 */
		int alarmId;
		try {
			alarmId = Integer.parseInt(args.getString(0));
		} catch (Exception e) {
			Log.d("AlarmReceiver", "Unable to process alarm with string id: " + args.getString(0));
			callbackContext.error("Cannot use string for notification id.");
			return true;
		}

		alarmId = 1;
		if (action.equalsIgnoreCase("addNotification")) {

			try {
				final String title = "Alert";
			    final String subTitle = args.getJSONObject(1).getString("message");
			    final String ticker = args.getJSONObject(1).getString("message");
			    
			    long seconds = System.currentTimeMillis();
			    Log.d(TAG, "Current time: " + seconds);
			    seconds = seconds + (args.getJSONObject(1).getLong("seconds") * 1000);
			    Log.d(TAG, "Alarm time: " + seconds);
			    
			    persistAlarm(alarmId, args);
			    return this.add(callbackContext, title, subTitle, ticker, "" + alarmId, seconds);
			} catch (Exception e) {
				Log.e(TAG, "Exception: " + e);
			}
		    
		} else if (action.equalsIgnoreCase("cancelNotification")) {
		    unpersistAlarm("" + alarmId);
		    return this.cancelNotification(callbackContext, "" + alarmId);
		} else if (action.equalsIgnoreCase("cancelAllNotifications")) {
		    unpersistAlarmAll();
		    return this.cancelAllNotifications(callbackContext);
		}

		return false;
    }

    /**
     * Set an alarm
     * 
     * @param callbackContext
     * 			  Callback context of the request from Cordova
     * @param repeatDaily
     *            If true, the alarm interval will be set to one day.
     * @param alarmTitle
     *            The title of the alarm as shown in the Android notification
     *            panel
     * @param alarmSubTitle
     *            The subtitle of the alarm
     * @param alarmId
     *            The unique ID of the notification
     * @param seconds
     *            A calendar object that represents the time at which the alarm
     *            should first be started
     */
    public Boolean add(CallbackContext callbackContext, String alarmTitle, String alarmSubTitle, String alarmTicker,
	    String alarmId, long seconds) {
    	final long triggerTime = seconds;

    	Log.d(TAG, "Adding notification: '" + alarmTitle + alarmSubTitle + "' with id: "
    			+ alarmId + " at timestamp: " + triggerTime);

		boolean result = alarm.addAlarm(alarmTitle, alarmSubTitle, alarmTicker, alarmId, seconds);
		if (result) {
			callbackContext.success();
			return true;
		} else {
			callbackContext.error("Add notification failed.");
			return false;
		}
    }

    /**
     * Cancel a specific notification that was previously registered.
     * 
     * @param callbackContext
     * 			  Callback context of the request from Cordova
     * @param notificationId
     *            The original ID of the notification that was used when it was
     *            registered using addNotification()
     */
    public Boolean cancelNotification(CallbackContext callbackContext, String notificationId) {
    	Log.d(TAG, "cancelNotification: Canceling event with id: " + notificationId);

		boolean result = alarm.cancelAlarm(notificationId);
		if (result) {
			callbackContext.success();
			return true;
		} else {
			callbackContext.error("Cancel notification failed.");
		    return false;
		}
    }

    /**
     * @param callbackContext
     * 			  Callback context of the request from Cordova
     * Cancel all notifications that were created by this plugin.
     */
    public Boolean cancelAllNotifications(CallbackContext callbackContext) {
		Log.d(TAG, "cancelAllNotifications: cancelling all events for this application");
		/*
		 * Android can only unregister a specific alarm. There is no such thing
		 * as cancelAll. Therefore we rely on the Shared Preferences which holds
		 * all our alarms to loop through these alarms and unregister them one
		 * by one.
		 */
		final SharedPreferences alarmSettings = cordova.getActivity().getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
		final boolean result = alarm.cancelAll(alarmSettings);
	
		if (result) {
			callbackContext.success();
		    return true;
		} else {
			callbackContext.error("Cancel all notifications failed.");
		    return false;
		}
    }

    /**
     * Persist the information of this alarm to the Android Shared Preferences.
     * This will allow the application to restore the alarm upon device reboot.
     * Also this is used by the cancelAllNotifications method.
     * 
     * @see #cancelAllNotifications()
     * 
     * @param optionsArr
     *            The assumption is that parseOptions has been called already.
     * 
     * @return true when successful, otherwise false
     */
    private boolean persistAlarm(int alarmId, JSONArray optionsArr) {
		final Editor alarmSettingsEditor = cordova.getActivity().getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE).edit();
	
		alarmSettingsEditor.putString("" + alarmId, optionsArr.toString());
	
		return alarmSettingsEditor.commit();
    }

    /**
     * Remove a specific alarm from the Android shared Preferences
     * 
     * @param alarmId
     *            The Id of the notification that must be removed.
     * 
     * @return true when successful, otherwise false
     */
    private boolean unpersistAlarm(String alarmId) {
		final Editor alarmSettingsEditor = cordova.getActivity().getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE).edit();
	
		alarmSettingsEditor.remove(alarmId);
	
		return alarmSettingsEditor.commit();
    }

    /**
     * Clear all alarms from the Android shared Preferences
     * 
     * @return true when successful, otherwise false
     */
    private boolean unpersistAlarmAll() {
		final Editor alarmSettingsEditor = cordova.getActivity().getApplicationContext().getSharedPreferences(TAG, Context.MODE_PRIVATE).edit();
	
		alarmSettingsEditor.clear();
	
		return alarmSettingsEditor.commit();
    }
}
