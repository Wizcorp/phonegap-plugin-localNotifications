# phonegap-plugin-localNotifications

- PhoneGap Version : 3.x

# Description

PhoneGap plugin for providing native local notification system to JavaScript.

** NOTE: Notification queuing is iOS ONLY.**

#### Android 2.x Support

To run on Android 2.x you will need to add the following yourself:

In `AlarmReceiver.java` add;

- Add `android-support-v13.java` to your project. This can be found in your Android SDK.
- `import android.support.v4.app.NotificationCompat;`
- Replace `Notification notification = new Notification.Builder(context)` with `Notification notification = new NotificationCompat.Builder(context)`

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

### Root class

The root class for the localNotification plugin is: `localNotification` every function descibed below has been defined within the `localNotification` class.

For example the `add()` function can be called like this:

```
localNotification.add(103, {
	seconds: 30,
	message: "This is an example",
	badge: 1});
```

### `add(int id, JSONObject options, Function success, Function failure)`

The `add()` function adds a notification to the notification area of the phone. Do note that adding a notification with the exact same notification id twice will replace the first notification.

```
	id: Integer which represents the notification id
	options: A JSONObject holding the notification options
```

Setting up the options Object:

```
var options = {
	seconds: int,
	ticker: string, //Android only & Optional
	title: string, //Android only & Optional
	icon: string //Android only & Optional
	message: string, 
	badge: int
};
```

The `icon` property has to reference to the name of a drawable resource in your Android project. If you leave the `title`, `ticker` or `icon` empty they will become default values.

`success` and `failure` refer to function callbacks. A failure will trigger if the user has not enabled local notifications - **iOS 8 ONLY**. Otherwise a success is returned.

### `queue(int id, JSONObject options, Function success, Function failure)`

**NOTE: Queuing is currently iOS ONLY.**

The `queue()` function queue's a notification to be sent to the notification area of an iDevice.

```
	id: Integer which represents the notification id.
	options: A JSONObject holding the notification options.
```

Setting up the options Object:

```
	var options = {
    		seconds: 30, 
    		message: "chaaaarrrliieeeee", 
    		badge: 1 
	};
```

`success` and `failure` refer to function callbacks. A failure will trigger if the user has not enabled local notifications - **iOS 8 ONLY**. Otherwise a success is returned.

### `cancel(int id)`

Cancels the notification the given notification id.

```
	id: The notification id.
```


### `cancelAll()`

Cancels all notifications.


### `setApplicationBadge(int value)`

Sets the application badge value, for later use with the `add()` or `queue()` functions.

```
	value: The application badge value	
```

### `getApplicationBadge(func success)`

Get the badge value. For use with the `add()` or `queue()` functions.

```
	success: The success callback. It gets called when the operation executes successfully.
```

Example usage:
```
localNotification.getApplicationBadge(function(badgeValue){
	alert("Our application badge value is: " + badgeValue);
});
```

### Receiving Notification Events 

Registers the function on document. When the user launches the application through a notification the event gets triggered (Android does not receive this event on cold application launch, but does on background launch).

```
	document.addEventListener("receivedLocalNotification", onReceivedLocalNotification, false);
```
