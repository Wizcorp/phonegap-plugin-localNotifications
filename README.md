


# PLUGIN: 

phonegap-plugin-localNotifications<br />
version : 1.9<br />
last update : 11/02/2012<br />


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

3.1) For observing and responding to notifications in JS, add code to your AppDelegate.m and your index.html

AppDelegate.m
<pre><code>
// this happens when we are running and receive a local notification
- (void)application:(UIApplication *)application didReceiveLocalNotification:(UILocalNotification *)notification
{
	// calls into javascript global function 'handleReceivedLocalNotification'
    NSDictionary *userInfo = [notification userInfo];
    
    NSString *active;
    if ( [application applicationState] == UIApplicationStateActive ) {
        active = @"true";
    } else {
        active = @"false";        
    }
    
    NSString* jsString = [NSString stringWithFormat:@"handleReceivedLocalNotification(\"%@\", %@);", [userInfo objectForKey:@"notificationId"], active];
    [self.viewController.webView stringByEvaluatingJavaScriptFromString:jsString];
}
</pre></code>

index.html
<pre><code>
    function decrementBadgeValue(badge)
    {
        if ( badge > 0 ) {
            badge = badge - 1;
        }
        window.localNotification.setApplicationBadge(badge);
    }
        
    function handleReceivedLocalNotification(notificationId, active)
    {
        // Update the badge value.
        window.localNotification.getApplicationBadge(decrementBadgeValue);
        
        var message = "Received local notificationId: " + JSON.stringify(notificationId);
        console.log( message );
        if ( active ) {
			// Your code to handle notification (app was running in foreground).
            console.log("Application was ACTIVE");
        } else {
			// Your code to handle notification (app was running in background).
            console.log("Application was NOT ACTIVE");
        }
    }
</pre></code>

3.2) For observing and responding to notifications in JS, when the app is
     launched due to a local notification some special code is required.
     Note: This code addresses the case where the app was *NOT* running in the
     background but the app was launched in response to the user responding to a
	 local notification.  For the case where the app was already running, see
     the example code in 3.1).

index.html
<pre><code>
    function startApp()
    {
        // Do something normal here.
        alert("App started normally.");
    }
        
    function startAppDueToNotification( notificationId )
    {
        // Do something special here using the notificationId.
        alert("App started due to notification: " + JSON.stringify(notificationId));
    }

	function onDeviceReady()
	{
        if (!window.localNotification) {
            alert("Could not find localNotification");
        }
        
        window.localNotification.launch(startApp, startAppDueToNotification);
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
