/**
 * 
 * @author Ally Ogilvie
 * @copyright WizCorp Inc. [ Incorporated Wizards ] 2011
 * @file LocalNotification.m for PhoneGap
 *
 */

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
*

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



*/