//
//  LocalNotification.h
//	Phonegap LocalNotification Plugin
//	Copyright (c) Greg Allen 2011
//	MIT Licensed

/**
 * 
 * @modding author Ally Ogilvie
 * @WizCorp Inc. [ Incorporated Wizards ] 2011
 * @file LocalNotification.h for PhoneGap
 *
 */

#import <Foundation/Foundation.h>
#import <PhoneGap/PGPlugin.h>

@interface LocalNotification : PGPlugin {
    
}

/*
 + (NSMutableDictionary *)notificationQueue;
*/
- (void)addNotification:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options;
- (void)cancelNotification:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options;
- (void)cancelAllNotifications:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options;
- (void)queueNotification:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options;
+ (void)emptyNotificationQueue;

@end
