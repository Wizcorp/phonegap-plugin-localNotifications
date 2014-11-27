cordova.define("jp.wizcorp.phonegap.plugin.localNotificationPlugin", function(require, exports, module) {
/**
 * 
 * @author Ally Ogilvie
 * @copyright Wizcorp Inc. [ Incorporated Wizards ] 2014
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


LocalNotification.prototype.add = function (id, options, success, failure) {
		exec(success, failure, "LocalNotification", "addNotification", [id, options]);
	};

LocalNotification.prototype.cancel = function (id) {
		exec(null, null, "LocalNotification", "cancelNotification", [id]);
	};
	
LocalNotification.prototype.cancelAll = function () {
        exec(null, null,"LocalNotification", "cancelAllNotifications", []);
    };
    
LocalNotification.prototype.queue = function (id, options, success, failure) {
		exec(success, failure, "LocalNotification", "queueNotification", [id, options]);
	};

LocalNotification.prototype.getApplicationBadge = function (s) {
        exec(s, null, "LocalNotification", "getApplicationBadge", []);
    };
        
LocalNotification.prototype.setApplicationBadge = function (intValue, s) {
        exec(s, null, "LocalNotification", "setApplicationBadge", [intValue]);
    };

// Instantiate LocalNotification
window.localNotification = new LocalNotification();
module.exports = localNotification;
});
