package jp.wizcorp.phonegap.plugin.localNotification;

import android.content.Context;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class that helps to store the options that can be specified per alarm.
 * 
 * @author dvtoever
 */
public class AlarmOptions {

    /*
     * Options that can be set when this plugin is invoked
     */
    private Calendar cal = Calendar.getInstance();
    private String alarmTitle = "";
    private String alarmSubTitle = "";
    private String alarmTicker = "";
    private boolean repeatDaily = false;
    private String notificationId = "";
    private int icon = 0;

    /**
     * Instantiates object based on the options object
     *
     * @param options JSON Array containing the list options.
     */
    public AlarmOptions(JSONArray options, Context context) {
        this.parseOptions(options, context);
    }

    /**
     * Parse options passed from javascript part of this plugin.
     * 
     * @param optionsArr
     *            JSON Array containing the list options.
     */
    public void parseOptions(JSONArray optionsArr, Context context) {
	    final JSONObject options = optionsArr.optJSONObject(0);

	    if (options != null) {

	        // Parse string representing the date
	        String textDate = options.optString("date");
	        if (!"".equals(textDate)) {
		        String[] datePart = textDate.split("/");
		        int month = Integer.parseInt(datePart[0]);
		        int day = Integer.parseInt(datePart[1]);
		        int year = Integer.parseInt(datePart[2]);
		        int hour = Integer.parseInt(datePart[3]);
		        int min = Integer.parseInt(datePart[4]);

		        cal.set(year, month, day, hour, min);
	        }

	        String optString = options.optString("message");
	        if (!"".equals(optString)) {
		        String lines[] = optString.split("\\r?\\n");
	    	    this.alarmTitle = lines[0];
		        if (lines.length > 1)
		            this.alarmSubTitle = lines[1];
	        }

            if(options.optString("icon") != "") {
                this.icon = context.getResources().getIdentifier(
                        options.optString("icon"), "drawable", context.getPackageName());
            } else  {
                this.icon = android.R.drawable.btn_star_big_on;
            }

	        this.alarmTicker = options.optString("ticker");
	        this.repeatDaily = options.optBoolean("repeatDaily");
	        this.notificationId = options.optString("id");
	    }
    }

    public Calendar getCal() {
	return cal;
    }

    public void setCal(Calendar cal) {
	this.cal = cal;
    }

    public String getAlarmTitle() {
	return alarmTitle;
    }

    public void setAlarmTitle(String alarmTitle) {
	this.alarmTitle = alarmTitle;
    }

    public String getAlarmSubTitle() {
	return alarmSubTitle;
    }

    public void setAlarmSubTitle(String alarmSubTitle) {
	this.alarmSubTitle = alarmSubTitle;
    }

    public String getAlarmTicker() {
	return alarmTicker;
    }

    public void setAlarmTicker(String alarmTicker) {
	this.alarmTicker = alarmTicker;
    }

    public boolean isRepeatDaily() {
	return repeatDaily;
    }

    public int getIcon() { return this.icon; }

    public void setIcon(int icon) { this.icon = icon; }

    public void setRepeatDaily(boolean repeatDaily) {
	this.repeatDaily = repeatDaily;
    }

    public String getNotificationId() {
	return notificationId;
    }

    public void setNotificationId(String notificationId) {
	this.notificationId = notificationId;
    }

}
