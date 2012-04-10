


# PLUGIN: 

phonegap-plugin-localNotifications



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

3.5) For sending all your queued notifications when app is killed or going to background add this code to your AppDelegate.m

<pre><code>
#import "LocalNotification.h"


- (void)applicationWillResignActive:(UIApplication *)application
{
	// apply any queued notifications
    [LocalNotification emptyNotificationQueue];
}

- (void)applicationWillResignActive:(UIApplication *)application
{
	// apply any queued notifications
    [LocalNotification emptyNotificationQueue];
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
	// apply any queued notifications
    [LocalNotification emptyNotificationQueue];
}
</pre></code>


4 ) Follow example code below.

<br />
<br />



# INSTALL (Android): #

Project tree<br />

<pre><code>
project
	/ assets
		/ www
			-index.html
			/ assets [store your app assets here]
			/ phonegap
				/js
					/ phonegap.js
				/ plugin
					/ localNotification
						/ localNotification.js	
	/ src
		/ jp/wizcorp/phonegap/plugin/LocalNotification
			/ LocalNotificationManager.java
			/ LocalNotificationPlugin.java
</code></pre>



1 ) Arrange files to structure seen above.


2 ) Remember to add the plugin to plugins.xml


3 ) Add \<script\> tag to your index.html<br />
\<script type="text/javascript" charset="utf-8" src="phonegap/plugin/localNotification/localNotification.js"\>\</script\><br />
(assuming your index.html is setup like tree above)


5) Configure your AndroidManefest.xml<br />
Add<br />
<pre><code>
/<!-- Local notification reciever --/>
/<receiver android:name="jp.wizcorp.phonegap.plugin.LocalNotification.LocalNotificationManager"//>
</pre></code>
<br />
Somewhere inside your <pre><code>/<application/>/</application/></pre></code> tags


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