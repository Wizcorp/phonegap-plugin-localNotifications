


# PLUGIN: 

phonegap-plugin-localNotifications<br />
version : 1.9<br />
last update : 03/12/2012<br />


# CHANGELOG: 
<br />
- Updated for cordova 1.9 (iOS only)


# DESCRIPTION :

PhoneGap plugin for providing native local notification system to JavaScript.

*NOTE* Notification queuing is iOS ONLY.


# PROPS :
Respect to Greg Allen 2011 -  Copyright (c) MIT Licensed
On which the (iOS & JS methods) plugin is based.
Original can be found here https://github.com/purplecabbage/phonegap-plugins/tree/master/iPhone/LocalNotification

# INSTALL (iOS): #

Project tree<br />

<pre><code>
project
	/ www
		-index.html
		/ assets [store your app assets here]
		/ phonegap
			/ plugin
				/ localNotification
					/ localNotification.js	
	/ Classes
	/ Plugins
		/ LocalNotification
			/ LocalNotification.h
			/ LocalNotification.m
	-project.xcodeproj
</code></pre>



1 ) Arrange files to structure seen above.

2 ) Add to phonegap.plist in the plugins array;<br />
Key : LocalNotification<br />
Type : String<br />
Value : LocalNotification<br />

3 ) Add \<script\> tag to your index.html<br />
\<script type="text/javascript" charset="utf-8" src="phonegap/plugin/localNotification/localNotification.js"\>\</script\><br />
(assuming your index.html is setup like tree above)

3) For observing and responding to notifications in JS, add code to your AppDelegate.m and your index.html

AppDelegate.m
<pre><code>
// this happens when we are running and receive a local notification
- (void)application:(UIApplication *)application didReceiveLocalNotification:(UILocalNotification *)notification
{
    NSDictionary *userInfo = [notification userInfo];
    
    [[NSNotificationCenter defaultCenter] postNotification:[NSNotification notificationWithName:@"WizLocalNoficationReceived"
                                                                                         object:self
                                                                                       userInfo:userInfo]];
}
</pre></code>

index.html
<pre><code>
	function onBodyLoad() {
		document.addEventListener("receivedLocalNotification", onReceivedLocalNotification, false);
	}

    function onReceivedLocalNotification(event) {
        var activeMessage;
        if ( event.active === true ) {
            activeMessage = ' while app was active';
        } else {
            activeMessage = ' while app was inactive';
        }
        var message = "Received local notificationId: " + event.notificationId + activeMessage;
        console.log(message);
        navigator.notification.alert(message);
    }
</pre></code>

4 ) Follow example code below.

<br />
<br />





<br />
<br />
<br />

# EXAMPLE CODE #

<br />
<br />
Add a notification<br />

localNotification.add(Int id, JSONObject options);
<br />
    * NOTE* Adding a notification stops the notification and adds another. 
<br />
<pre><code>
{

    seconds: 30, 
    message: "chaaaarrrliieeeee", 
    badge: 1 

}; 
</code></pre>
<br />
Queue a notification.<br />
localNotification.queue(Int id, JSONObject options);
<br />
    * NOTE* Queuing is currently iOS ONLY. 
<br />
<pre><code>
{

    seconds: 30, 
    message: "chaaaarrrliieeeee", 
    badge: 1 

}; 
</code></pre>
<br />

Cancel a notification <br />
localNotification.cancel(Int id); 
<br />

Cancel all notifications<br />
localNotification.cancelAll(); 
<br />

Set application badge value <br />
localNotification.setApplicationBadge(Int value); 
<br />

Get the application badge value<br />
localNotification.getApplicationBadge(getSuccesFunction); 
<br />

Handle application launch due to notification<br />
localNotification.launch(standardLaunchFunction, launchDueToNotificationFunction); 
<br />
