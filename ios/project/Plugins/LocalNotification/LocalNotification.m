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

@interface LocalNotification ()
@property (nonatomic, retain) NSMutableDictionary *notificationQueue;
+ (void)load;
+ (void)didFinishLaunching:(NSNotification *)notification;
+ (void)willTerminate:(NSNotification *)notification;
- (void)emptyNotificationQueue:(NSNotification *)notification;
@end

@implementation LocalNotification

static BOOL launchedWithNotification = NO;
static UILocalNotification *localNotification = nil;

#pragma - Class Methods

+ (void)load
{
    // Register for didFinishLaunching notifications in class load method so that
    // this class can observe launch events.  Do this here because this needs to be
    // registered before the AppDelegate's application:didFinishLaunchingWithOptions:
    // method finishes executing.  A class's load method gets invoked before
    // application:didFinishLaunchingWithOptions is invoked (even if the plugin is
    // not loaded/invoked in the JavaScript).
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(didFinishLaunching:)
                                                 name:UIApplicationDidFinishLaunchingNotification
                                               object:nil];
    
    // Register for willTerminate notifications here so that we can observer terminate
    // events and unregister observing launch notifications.  This isn't strictly
    // required (and may not be called according to the docs).
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(willTerminate:)
                                                 name:UIApplicationWillTerminateNotification
                                               object:nil];
}

+ (void)didFinishLaunching:(NSNotification *)notification
{
    // This code will be called immediately after application:didFinishLaunchingWithOptions:.
    NSDictionary *launchOptions = [notification userInfo];
    
    UILocalNotification *localNotif = [launchOptions objectForKey: @"UIApplicationLaunchOptionsLocalNotificationKey"];
    if (localNotif) {
        launchedWithNotification = YES;
        localNotification = localNotif;
        [localNotification retain];
    } else {
        launchedWithNotification = NO;
    }
}

+ (void)willTerminate:(NSNotification *)notification
{
    // Stop the class from observing all notification center notifications.
    [[NSNotificationCenter defaultCenter] removeObserver:self];
    
    // Release the notification
    [localNotification release];
}

#pragma - Instance Methods

- (void)dealloc
{
    self.notificationQueue = nil;
    
    // Stop the instance from observing all notification center notifications.
    [[NSNotificationCenter defaultCenter] removeObserver:self];
    
    [super dealloc];
}

-(CDVPlugin*) initWithWebView:(UIWebView*)theWebView
{
    
    self = (LocalNotification*)[super initWithWebView:theWebView];
    
    // initiate empty Notification Queue
    self.notificationQueue = [[NSMutableDictionary alloc ] init];
    
    // Register the instance to observe didEnterBackground notifications
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(emptyNotificationQueue:)
                                                 name:UIApplicationDidEnterBackgroundNotification
                                               object:nil];

    // Register the instance to observe willResignActive notifications
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(emptyNotificationQueue:)
                                                 name:UIApplicationWillResignActiveNotification
                                               object:nil];
    
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
    [self.notificationQueue setObject:options forKey:notificationId];
    

}

- (void)emptyNotificationQueue:(NSNotification *)notification {
    
    LocalNotification* _localNotification = [[LocalNotification alloc] init];
    
    // Add all notifications from the notificationQueue dictionary and empty it
    for (NSString* key in self.notificationQueue) {
        
        // grab values from notification queue, remember to add 'padding' as addNotification method reads Id from array[1]
        NSMutableArray* notificationArray = [[NSMutableArray alloc] initWithObjects:@"padding", key, nil];
        NSMutableDictionary* notificationDict = [[NSMutableDictionary alloc] initWithDictionary:[self.notificationQueue objectForKey:key]];
        WizLog(@"Notification in queue adding : %@ with options : %@", notificationArray, notificationDict);

        
        [_localNotification addNotification:notificationArray withDict:notificationDict];
        [notificationArray release];
        [notificationDict release];
        
    }
    
    // empty it
    [self.notificationQueue removeAllObjects];
    
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

// Currently, JS cannot be aware of when the app is launched
- (void)launch:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options
{
    // If app started due to notification, return that to the corresponding JS handler (which is actually
    // stored as the error callback). Otherwise app started normally, so return to the JS function (which
    // is stored as the success callback).
    NSString *callbackId;
    CDVPluginResult* pluginResult;
    if ( launchedWithNotification ) {
        callbackId = [arguments objectAtIndex:0];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                                         messageAsString:[[localNotification userInfo] objectForKey:@"notificationId"]];
        [self writeJavascript: [pluginResult toErrorCallbackString:callbackId]];
    } else {
        callbackId = [arguments objectAtIndex:0];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [self writeJavascript: [pluginResult toSuccessCallbackString:callbackId]];
    }
}

- (void)getApplicationBadge:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options
{
    NSString *callbackId = [arguments objectAtIndex:0];

    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                                                         messageAsInt:[UIApplication sharedApplication].applicationIconBadgeNumber];
    
    [self writeJavascript: [pluginResult toSuccessCallbackString:callbackId]];
}

- (void)setApplicationBadge:(NSMutableArray*)arguments withDict:(NSMutableDictionary*)options
{
    NSNumber *value = [arguments objectAtIndex:1];
    [UIApplication sharedApplication].applicationIconBadgeNumber = [value integerValue];

    // Invoke callback method if it was specified.
    NSString *callbackId = [arguments objectAtIndex:0];
    if ( ![callbackId isEqualToString:@"INVALID"] ) {
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [self writeJavascript: [pluginResult toSuccessCallbackString:callbackId]];
    }
}

@end