//
//  LocalNotification.h
//	Phonegap LocalNotification Plugin
//	Copyright (c) Greg Allen 2011
//	MIT Licensed

/**
 * 
 * @modding author Ally Ogilvie
 * @WizCorp Inc. [ Incorporated Wizards ] 2011
 * @file LocalNotification.h for Cordova
 *
 */

#import <Foundation/Foundation.h>
#import <Cordova/CDVPlugin.h>

@interface LocalNotification : CDVPlugin {
    
}

/*
 + (NSMutableDictionary *)notificationQueue;
*/
- (void)addNotification:(CDVInvokedUrlCommand*)command;
- (void)cancelNotification:(CDVInvokedUrlCommand*)command;
- (void)cancelAllNotifications:(CDVInvokedUrlCommand*)command;
- (void)queueNotification:(CDVInvokedUrlCommand*)command;

- (void)getApplicationBadge:(CDVInvokedUrlCommand*)command;
- (void)setApplicationBadge:(CDVInvokedUrlCommand*)command;

@end
