//
//  LocalNotification.m
//	Phonegap LocalNotification Plugin
//	Copyright (c) Greg Allen 2011
//	MIT Licensed


/**
 * 
 * @modding author Ally Ogilvie
 * @WizCorp Inc. [ Incorporated Wizards ] 2011
 * @file LocalNotification.m for Cordova
 *
 */

#import "LocalNotification.h"
#import "WizDebugLog.h"


@implementation LocalNotification

static NSMutableDictionary *notificationQueue = nil;

-(CDVPlugin*) initWithWebView:(UIWebView*)theWebView
{
    
    self = (LocalNotification*)[super initWithWebView:theWebView];
    // initiate empty Notification Queue
    notificationQueue = [[NSMutableDictionary alloc ] init];
    
    return self;
}


- (void)addNotification:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options {
	
    // NO callbacks
    
    
    int seconds                 = [[options objectForKey:@"seconds"] intValue];
	NSString *msg               = [options objectForKey:@"message"];
	// NSString *action            = [options objectForKey:@"action"];
    NSString *action            = @"View";
	NSString *notificationId    = [NSString stringWithFormat:@"%@", [arguments objectAtIndex:1]];
	NSInteger badge             = [[options objectForKey:@"badge"] intValue];
	bool hasAction              = TRUE;
	
    
    NSTimeInterval secondedDate = ([[NSDate date] timeIntervalSince1970] + seconds);
    
	NSDate  *date   = [NSDate dateWithTimeIntervalSince1970:secondedDate];

	
	UILocalNotification *notif = [[UILocalNotification alloc] init];
    notif.fireDate  = date;
	notif.hasAction = hasAction;
	notif.timeZone  = [NSTimeZone defaultTimeZone];
	
	notif.alertBody = ([msg isEqualToString:@""])?nil:msg;
	notif.alertAction = action;
	notif.soundName = UILocalNotificationDefaultSoundName;
	notif.applicationIconBadgeNumber = badge;
	
	NSDictionary *userDict = [NSDictionary dictionaryWithObject:notificationId 
														 forKey:@"notificationId"];
	notif.userInfo = userDict;
	
    
    // check for existing notification with same id?
    NSArray *notifications      = [[UIApplication sharedApplication] scheduledLocalNotifications];
    
    for (UILocalNotification *notification in notifications) {
        NSString *notId = [notification.userInfo objectForKey:@"notificationId"];
        if ([notificationId isEqualToString:notId]) {
            // it is the same so cancel it
            WizLog(@"Notification Canceled: %@", notificationId);
            [[UIApplication sharedApplication] cancelLocalNotification:notification];
            
        } 
    }
    
    // now schedule new one
    NSLog(@"Notification Set: %@ (ID: %@, Badge: %i)", date , notificationId, badge);
    [[UIApplication sharedApplication] scheduleLocalNotification:notif];
    
	[notif release];
    
}


- (void)queueNotification:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options {
    WizLog(@"[queueNotification] ------- adding notification to queue ");
    // store notifications in notificationQueue dictionary
    NSString *notificationId    = [NSString stringWithFormat:@"%@", [arguments objectAtIndex:1]];
    [notificationQueue setObject:options forKey:notificationId];
    

}

+ (void)emptyNotificationQueue {
    
    LocalNotification* _localNotification = [[LocalNotification alloc] init];
    
    // Add all notifications from the notificationQueue dictionary and empty it
    for (NSString* key in notificationQueue) {
        
        // grab values from notification queue, remember to add 'padding' as addNotification method reads Id from array[1]
        NSMutableArray* notificationArray = [[NSMutableArray alloc] initWithObjects:@"padding", key, nil];
        NSMutableDictionary* notificationDict = [[NSMutableDictionary alloc] initWithDictionary:[notificationQueue objectForKey:key]];
        WizLog(@"Notification in queue adding : %@ with options : %@", notificationArray, notificationDict);

        
        [_localNotification addNotification:notificationArray withDict:notificationDict];
        [notificationArray release];
        [notificationDict release];
        
    }
    
    // empty it
    [notificationQueue removeAllObjects];
    
    [_localNotification release];
    
    
    
}

- (void)cancelNotification:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options {
	
    if ([arguments count] >1) {
        NSString *notificationId    = [NSString stringWithFormat:@"%@", [arguments objectAtIndex:1]];
        NSArray *notifications      = [[UIApplication sharedApplication] scheduledLocalNotifications];
        
        for (UILocalNotification *notification in notifications) {
            NSString *notId = [notification.userInfo objectForKey:@"notificationId"];
            if ([notificationId isEqualToString:notId]) {
                WizLog(@"Notification Canceled: %@", notificationId);
                [[UIApplication sharedApplication] cancelLocalNotification:notification];
            } else {
                WizLog(@"Notification id: %@ - NOT FOUND in: %@",notificationId, notification.userInfo);
            }
        }
    } else {
        WizLog(@"Notification Canceled not enough params. Missing ID");
        
    }
    
    
}

- (void)cancelAllNotifications:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options {
	
    WizLog(@"All Notifications cancelled");
	[[UIApplication sharedApplication] cancelAllLocalNotifications];
    
}
@end