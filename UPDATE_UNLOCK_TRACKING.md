# Update: Unlock App Tracking Feature Added

## What's New ✨

The app now tracks **which app was opened immediately after unlocking the phone**. This provides valuable insight into unlock behavior and intent.

## How It Works

1. **Screen Unlock Detected**: When the user unlocks their phone (ACTION_USER_PRESENT)
2. **Brief Wait**: App waits 1 second for user to open an app
3. **App Detection**: Uses UsageStats API to detect which app moved to foreground
4. **Event Logging**: Records the unlock timestamp + the app package name
5. **Daily Aggregation**: Groups unlock apps by count (e.g., "Instagram: 42 unlocks")

## New Data Collected

### Individual Unlock Events
Each unlock event now includes:
- Timestamp
- Date
- **App package name** (the app opened after unlock)

### Daily Aggregation
Your Google Sheet now has a new column: **"Top Unlock Apps"**

Example data:
```
Instagram: 42x; WhatsApp: 25x; Chrome: 12x; Gmail: 8x; Twitter: 5x
```

This shows:
- Instagram was the app opened after unlock 42 times
- WhatsApp: 25 times
- Chrome: 12 times
- Etc.

## Dashboard Display

The participant dashboard now shows a new card:

**"Apps Opened After Unlock:"**
- Instagram: 42 unlocks
- WhatsApp: 25 unlocks
- Chrome: 12 unlocks
- Gmail: 8 unlocks
- Twitter: 5 unlocks

## Research Implications

This data helps answer questions like:

1. **Unlock Intent**: What are people *trying* to do when they unlock?
   - High Instagram unlocks = checking social media
   - High messaging app unlocks = responding to conversations

2. **Habitual Checking**: 
   - If someone unlocks 87 times but opens Instagram 42 times, that's ~48% of unlocks for one app
   - Indicates potential habitual checking behavior

3. **Unlock vs Usage Time**:
   - Compare unlock frequency with actual usage time
   - Many unlocks + low usage = brief checking behavior
   - Few unlocks + high usage = intentional, sustained use

4. **Notification Response**:
   - Cross-reference with notification data
   - Did they unlock *because* of a notification?
   - Which apps trigger unlock behavior?

## Technical Details

### Code Changes

**UsageTrackingService.kt**:
- Added `getForegroundApp()` method using UsageEvents API
- Waits 1 second after unlock to capture app
- Filters out system UI and launcher apps

**DataModels.kt**:
- Added `unlockAppsJson` field to `DailyUsage`
- Added `UnlockAppCount` data class

**UsageStatsCollector.kt**:
- Aggregates unlock events by app
- Sorts by count (most frequent first)

**GoogleSheetsSync.kt**:
- Added unlock apps column to sheet export
- Format: "AppName: Nx; AppName: Nx; ..."

**DashboardActivity.kt**:
- Displays top 5 unlock apps
- Shows count for each app

**Database**:
- Schema version updated to v2
- Uses fallbackToDestructiveMigration (safe for research app)

### Google Sheet Update

**Old columns:**
```
Date | User Name | Anonymous ID | Study Day | Screen Time (min) | Top Apps | Total Unlocks | Total Notifications | Top Notification Apps
```

**New columns (added column H):**
```
Date | User Name | Anonymous ID | Study Day | Screen Time (min) | Top Apps | Total Unlocks | Top Unlock Apps | Total Notifications | Top Notification Apps
```

⚠️ **Important**: Update your Google Sheet headers to include the new column!

## Setup Requirements

### For Existing Sheets
Add this column header in **column H**: `Top Unlock Apps`

### For New Deployments
Use the updated column headers from QUICK_START.md

## Example Analysis

### Sample Participant Data:

**Day 1:**
- Total Unlocks: 87
- Top Unlock Apps:
  - Instagram: 42 (48% of unlocks)
  - WhatsApp: 25 (29%)
  - Chrome: 12 (14%)
  - Gmail: 8 (9%)

**Insights:**
- Instagram dominates unlock behavior
- Combined social/messaging (Instagram + WhatsApp) = 77% of unlocks
- Suggests phone primarily used for social interaction
- High unlock frequency may indicate compulsive checking

### Cross-Analysis with Screen Time:

If this same participant had:
- Instagram: 120 minutes screen time
- WhatsApp: 45 minutes screen time

**Calculation:**
- Instagram: 42 unlocks, 120 minutes = ~2.9 min per session
- WhatsApp: 25 unlocks, 45 minutes = ~1.8 min per session

**Interpretation:**
- Short Instagram sessions suggest scrolling/checking behavior
- Very brief WhatsApp sessions = message checking

## Privacy Note

The app only logs:
- ✅ App package name (e.g., "com.instagram.android")
- ✅ App display name (e.g., "Instagram")
- ✅ Count of how many times it was opened after unlock

It does NOT log:
- ❌ What the user did in the app
- ❌ Any content, messages, or data
- ❌ Screen captures or screenshots

## Compatibility

- Works on Android 7.0+ (same as before)
- Requires Usage Access permission (already required)
- No additional permissions needed
- Minimal performance impact (1-second delay per unlock)

## Known Limitations

1. **System Apps Filtered**: Launcher and system UI are excluded
2. **1-Second Delay**: If user opens app faster than 1 second, might miss it
3. **Background Apps**: Only captures foreground app, not background intent
4. **No Unlock Source**: Can't distinguish unlock method (fingerprint, PIN, face, etc.)

## Migration Guide

### If You've Already Deployed:

1. **Update the App Code**: Use the updated files
2. **Rebuild APK**: New database schema (v2)
3. **Update Google Sheet**: Add "Top Unlock Apps" column
4. **Notify Participants**: Ask them to update the app
5. **Note**: Old data won't have unlock apps (empty column), new data will

### If Starting Fresh:

Just use the updated files - everything is ready to go!

## Questions This Data Can Answer

1. What apps drive most phone unlocks?
2. Is there a mismatch between unlock intent and actual usage?
3. Do notifications predict which app gets opened after unlock?
4. How does unlock app distribution change over the study?
5. Do users show habitual unlock patterns (same app repeatedly)?
6. What's the relationship between unlock frequency and app diversity?

## Summary

This enhancement provides much richer behavioral data without requiring any additional participant effort. The unlock app is captured automatically and transparently, giving you insight into:

- **User intent** when picking up the phone
- **Habitual patterns** in app checking
- **Notification response** behavior
- **App prioritization** in daily routine

Perfect for studies on:
- Digital wellbeing
- App addiction
- Notification management
- Attention patterns
- Behavior change interventions

---

**Version**: 1.1  
**Feature Added**: February 2026  
**Database Schema**: v2  
**Backward Compatible**: No (requires app update if already deployed)
