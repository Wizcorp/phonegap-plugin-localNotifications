# phonegap-plugin-localNotifications

- PhoneGap Version : 3.x

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

### Root class

The root class for the localNotification plugin is: `localNotification` every function descibed below has been defined within the `localNotification` class.

For example the `add()` function can be called like this:
```
localNotification.add(103, {
	seconds: 30,
	message: "This is an example",
	badge: 1});
```

### `add(int id, JSONObject options)`

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

### `queue(int id, JSONObject options)`

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

### `launch(func stdLaunch, func ntfLaunch)`

Registers the function on the app launch event. When the user launches the application through a notification the event gets triggered.

```
	stdLaunch: The standard launch function.
	ntfLaunch: the function to be used when application has been launched through a notification.
```
