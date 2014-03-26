cordova.define("jp.wizcorp.phonegap.plugin.localNotificationPlugin", function(require, exports, module) {
/**
 * 
 * @author Ally Ogilvie
 * @copyright Wizcorp Inc. [ Incorporated Wizards ] 2013
 * @file localNotification.js for PhoneGap
 *
 */

var exec = require("cordova/exec");

if (window.cordova) {
    window.document.addEventListener("deviceready", function () {
        exec(null, null, "LocalNotification", "ready", []);
    }, false);
}

var LocalNotification = function () {};


LocalNotification.prototype.add = function (id, options) {
		exec(null, null, "LocalNotification", "addNotification", [id, options]);
	};

LocalNotification.prototype.cancel = function (id) {
		exec(null, null, "LocalNotification", "cancelNotification", [id]);
	};
	
LocalNotification.prototype.cancelAll = function () {
        exec(null, null,"LocalNotification", "cancelAllNotifications", []);
    };
    
LocalNotification.prototype.queue = function (id, options) {
        
		exec(null, null, "LocalNotification", "queueNotification", [id, options]);
	};

LocalNotification.prototype.getApplicationBadge = function (s) {
        exec(s, null, "LocalNotification", "getApplicationBadge", []);
    };
        
LocalNotification.prototype.setApplicationBadge = function (intValue, s) {
        exec(s, null, "LocalNotification", "setApplicationBadge", [intValue]);
    };

// instantiate wizCanvas (passing "mainView" which is Cordova's window)
window.localNotification = new LocalNotification();
module.exports = localNotification;
});
