# SETUP FOR BUILDING

To build this example, PhoneGap requires that the plugin be installed in the
correct location relative to this example's www directory.  So we must copy or
link the plugin javascript code to the www directory.

Currently, the XCode project performs the copy method (described below) as part
of it's build process.

Copy Method:
	mkdir -p ./www/phonegap
	cp -r ../www/phonegap ./www

Link Method:
	mkdir -p ./www/phonegap/plugin/localNotification
	ln -f ../www/phonegap/plugin/localNotification/localNotification.js ./www/phonegap/plugin/localNotification/localNotification.js

