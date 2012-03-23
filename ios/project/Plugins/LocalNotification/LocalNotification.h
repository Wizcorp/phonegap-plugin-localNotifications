//
//  LocalNotification.h
//	Phonegap LocalNotification Plugin
//	Copyright (c) Greg Allen 2011
//	MIT Licensed

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
