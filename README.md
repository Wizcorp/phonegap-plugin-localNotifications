# phonegap-plugin-localNotifications

- PhoneGap Version : 3.0
- last update : 01/10/2013

# Description

PhoneGap plugin for providing native local notification system to JavaScript.

** NOTE: Notification queuing is iOS ONLY.**

# Credits

Respect to Greg Allen 2011 -  Copyright (c) MIT Licensed
On which the (iOS & JS methods) plugin is based.
Original can be found here [https://github.com/purplecabbage/phonegap-plugins/tree/master/iPhone/LocalNotification](https://github.com/purplecabbage/phonegap-plugins/tree/master/iPhone/LocalNotification)

## Install (with Plugman) 

iOS
	
	cordova plugin add https://github.com/Wizcorp/phonegap-plugin-localNotifications
	build ios
	
	< or >
	
	phonegap local plugin add https://github.com/Wizcorp/phonegap-plugin-localNotifications
	phonegap build ios

Android
	
	cordova plugin add https://github.com/Wizcorp/phonegap-plugin-localNotifications
	build android
	
	< or >
	
	phonegap local plugin add https://github.com/Wizcorp/phonegap-plugin-localNotifications
	phonegap build android


## APIs

### Add a notification

	localNotification.add(Int id, JSONObject options);

** NOTE: Adding a notification with the same id stops the notification and adds another. **

	{
    	seconds: 30, 
    	message: "chaaaarrrliieeeee", 
    	badge: 1 
	}; 

### Queue a notification

	localNotification.queue(Int id, JSONObject options);

** NOTE: Queuing is currently iOS ONLY. **

	{
    	seconds: 30, 
    	message: "chaaaarrrliieeeee", 
    	badge: 1 
	}; 
	
### Cancel a notification

	localNotification.cancel(Int id); 


### Cancel all notifications

	localNotification.cancelAll(); 


### Set application badge value

	localNotification.setApplicationBadge(Int value); 

### Get the application badge value

	localNotification.getApplicationBadge(getSuccesFunction); 

### Handle application launch due to notification

	localNotification.launch(standardLaunchFunction, launchDueToNotificationFunction); 
