cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
	{
        "file": "phonegap/plugin/localNotification/localNotification.js",
        "id": "jp.wizcorp.phonegap.plugin.localNotificationPlugin",
        "clobbers": [
            "window.localNotification"
        ]
    }];
module.exports.metadata = 
// TOP OF METADATA
{}
// BOTTOM OF METADATA
});