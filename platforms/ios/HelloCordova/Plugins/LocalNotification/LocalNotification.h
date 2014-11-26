//
//  LocalNotification.h
//	Phonegap LocalNotification Plugin
//	Copyright (c) Greg Allen 2011
//	MIT Licensed

/**
 * Updates
 * @author Ally Ogilvie
 * @Wizcorp Inc. [ Incorporated Wizards ] 2014
 * @file LocalNotification.h for Cordova
 *
 */

#import <Foundation/Foundation.h>
#import <Cordova/CDVPlugin.h>

@interface LocalNotification : CDVPlugin

@property (nonatomic, retain) NSMutableDictionary *notificationQueue;

+ (void)load;
+ (void)didFinishLaunching:(NSNotification *)notification;
+ (void)willTerminate:(NSNotification *)notification;
- (void)emptyNotificationQueue:(NSNotification *)notification;

// Cordova APIs
- (void)addNotification:(CDVInvokedUrlCommand*)command;
- (void)cancelNotification:(CDVInvokedUrlCommand*)command;
- (void)cancelAllNotifications:(CDVInvokedUrlCommand*)command;
- (void)queueNotification:(CDVInvokedUrlCommand*)command;
- (void)getApplicationBadge:(CDVInvokedUrlCommand*)command;
- (void)setApplicationBadge:(CDVInvokedUrlCommand*)command;

@end
