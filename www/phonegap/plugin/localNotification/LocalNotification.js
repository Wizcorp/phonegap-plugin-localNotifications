/**
	Phonegap LocalNotification Plugin
	Copyright (c) Greg Allen 2011
	MIT Licensed

	Usage:
	plugins.localNotification.add({ date: new Date(), message: 'This is a notification', badge: 1, id: 123 });
	plugins.localNotification.cancel(123);
	plugins.localNotification.cancelAll();
**/

var localNotification = {

	add : function(id, options) {

		return PhoneGap.exec(null, null, "LocalNotification", "addNotification", [id, options]);
	},

	cancel : function(id) {
		return PhoneGap.exec(null, null, "LocalNotification", "cancelNotification", [id]);
	},
	
	cancelAll : function() {
        return PhoneGap.exec(null, null,"LocalNotification", "cancelAllNotifications", []);
    },
    
    queue : function(id, options) {
        
		return PhoneGap.exec(null, null, "LocalNotification", "queueNotification", [id, options]);
	}
    

}



/*
*
*   Example functions
*/
function sendNotif() {
    myOptions = {
        seconds: 30,
        message: 'chaaaarrrliieeeee',
        badge: 1
    }
    localNotification.add( "0", myOptions );
    
};

function cancelLastNotif() {
    localNotification.cancel( "0" );
    
};

function cancelAllNotif() {
    
    localNotification.cancelAll();
    
};