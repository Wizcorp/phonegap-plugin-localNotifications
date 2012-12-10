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
#ifdef CORDOVA_FRAMEWORK
#import <Cordova/CDVPlugin.h>
#else
#import "CDVPlugin.h"
#endif

@interface LocalNotification : CDVPlugin {
    
}

/*
 + (NSMutableDictionary *)notificationQueue;
*/
- (void)addNotification:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options;
- (void)cancelNotification:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options;
- (void)cancelAllNotifications:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options;
- (void)queueNotification:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options;

- (void)getApplicationBadge:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options;
- (void)setApplicationBadge:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options;

@end
