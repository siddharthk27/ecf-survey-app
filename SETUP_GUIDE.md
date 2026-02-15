# Usage Tracker App - Setup Guide

## Overview
This Android app collects smartphone usage data for research purposes over a 10-day study period. It tracks:
- Daily screen time and per-app usage (collected at midnight)
- Number of unlocks (event-based logging)
- Notifications and their sources (event-based logging)
- All data is automatically synced to Google Sheets daily

## Quick Start Summary

### For Researchers
1. Set up a Google Cloud Project with Sheets API
2. Create a Google Sheet for data collection
3. Configure the app with your Sheet ID
4. Build and distribute the APK to participants
5. Monitor data collection in Google Sheets

### For Participants
1. Install the app
2. Enter your name
3. Grant required permissions (Usage Access & Notification Access)
4. Check your dashboard daily when notified
5. Data syncs automatically

---

## Part 1: Google Sheets Setup

### Step 1: Create Google Cloud Project

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project: "Usage Tracker Research"
3. Enable the **Google Sheets API**:
   - Navigate to "APIs & Services" → "Library"
   - Search for "Google Sheets API"
   - Click "Enable"

### Step 2: Create OAuth 2.0 Credentials

1. Go to "APIs & Services" → "Credentials"
2. Click "Create Credentials" → "OAuth client ID"
3. Choose "Android" as application type
4. For Package name: `com.research.usagetracker`
5. Get SHA-1 fingerprint:
   ```bash
   keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
   ```
6. Save the OAuth client ID

### Step 3: Create Google Sheet

1. Create a new Google Sheet
2. Name it "Usage Tracker Research Data"
3. In Sheet1, add these column headers in row 1:
   ```
   Date | User Name | Anonymous ID | Study Day | Screen Time (min) | Top Apps | Total Unlocks | Total Notifications | Top Notification Apps
   ```
4. Get the Sheet ID from the URL:
   ```
   https://docs.google.com/spreadsheets/d/[SHEET_ID]/edit
   ```
5. Share the sheet with your Google account

### Step 4: Configure App

In the file `GoogleSheetsSync.kt`, you'll need to update the credential setup to use your OAuth configuration. The app will prompt users to sign in with Google on first sync.

---

## Part 2: Building the Android App

### Prerequisites
- Android Studio (latest version)
- Android SDK (API 24+)
- Java 8 or higher

### Build Instructions

1. **Open Project in Android Studio**
   - File → New → Import Project
   - Select the `UsageTrackerApp` folder
   - Wait for Gradle sync to complete

2. **Update Dependencies**
   - Make sure all dependencies in `build.gradle` are resolved
   - You may need to add the JitPack repository for MPAndroidChart:
     ```gradle
     allprojects {
         repositories {
             maven { url 'https://jitpack.io' }
         }
     }
     ```

3. **Configure Signing**
   - For debug builds, Android Studio handles this automatically
   - For release builds:
     - Generate a keystore: Build → Generate Signed Bundle/APK
     - Keep your keystore secure!

4. **Build APK**
   - For testing: Build → Build Bundle(s)/APK(s) → Build APK(s)
   - For distribution: Build → Generate Signed Bundle/APK → APK
   - Choose release variant
   - Sign with your keystore

5. **Locate APK**
   - Debug: `app/build/outputs/apk/debug/app-debug.apk`
   - Release: `app/build/outputs/apk/release/app-release.apk`

---

## Part 3: App Configuration

### Setting Sheet ID in the App

**Option 1: Hardcode (Simple)**
In `AppPreferences.kt`, modify the `getSheetId()` function:
```kotlin
fun getSheetId(): String = "YOUR_SHEET_ID_HERE"
```

**Option 2: Settings Screen (Recommended)**
Add a settings activity where researchers can input the Sheet ID after installation.

### Google Sign-In Setup

The app needs to authenticate with Google to access Sheets. Update the GoogleSheetsSync to handle authentication:

1. First sync will prompt user to sign in
2. Credentials are stored locally
3. Subsequent syncs happen automatically

---

## Part 4: Participant Instructions

### Installation Guide for Participants

**Provide participants with these steps:**

1. **Download and Install**
   - Download the APK file
   - Enable "Install from Unknown Sources" in Settings
   - Open the APK and tap "Install"

2. **Initial Setup**
   - Open the app
   - Enter your full name
   - Tap "Start Study"

3. **Grant Permissions**
   - **Usage Access Permission**:
     - Tap "Grant Usage Access"
     - Find "Usage Tracker" in the list
     - Toggle it ON
     - Confirm
   
   - **Notification Access Permission**:
     - Tap "Grant Notification Access"
     - Find "Usage Tracker" in the list
     - Toggle it ON
     - Confirm

4. **Complete Setup**
   - Return to the app
   - Tap "Continue to Dashboard"

5. **Daily Usage**
   - Check your phone normally
   - You'll receive a notification daily at 10 AM to check your stats
   - Open the app to see yesterday's usage data
   - Data syncs automatically to the research team

### What Participants Should Know

- **Duration**: 10-day study
- **Privacy**: Your name is recorded for payment purposes, but data analysis uses anonymous IDs
- **Battery**: Minimal impact on battery life
- **What's Tracked**:
  - Total screen time per day
  - Time spent in each app
  - Number of times you unlock your phone
  - Number of notifications you receive
  - Which apps send you notifications

### Troubleshooting for Participants

**App not tracking data?**
- Check that both permissions are still granted
- Restart the app
- Make sure the app wasn't force-stopped or battery-optimized

**No dashboard data?**
- Data appears the day after collection starts
- First day shows data from "yesterday"

**Forgot to check stats?**
- No problem! Historical data is stored locally
- Just open the app when you remember

---

## Part 5: Data Collection & Monitoring

### Data Format in Google Sheets

Each row contains:
- **Date**: YYYY-MM-DD format
- **User Name**: Participant's real name
- **Anonymous ID**: 8-character unique ID
- **Study Day**: 1-10
- **Screen Time (min)**: Total minutes
- **Top Apps**: "App1: Xmin; App2: Ymin; ..."
- **Total Unlocks**: Count
- **Total Notifications**: Count
- **Top Notification Apps**: "App1: X; App2: Y; ..."

### Monitoring Progress

Check your Google Sheet daily to ensure:
- All participants are syncing data
- Data looks reasonable (no zeros or missing values)
- Study day increments correctly

### Handling Missing Data

If a participant hasn't synced:
1. Contact them to ensure app is still running
2. Check if permissions are still granted
3. Ask them to manually tap "Sync to Google Sheets" in the app

---

## Part 6: Privacy & Ethics

### Data Privacy

- **Participant Names**: Stored only for payment tracking
- **Anonymous IDs**: Generated automatically, used for analysis
- **App Content**: NOT collected (only app names and usage times)
- **Notification Content**: NOT collected (only counts and app sources)

### Consent & Disclosure

Participants should be informed:
- What data is collected
- How long the study lasts
- How data will be used
- That they can withdraw anytime

### Data Security

- Data transmitted via HTTPS to Google Sheets
- Google Sheets should have restricted access
- After study completion, consider removing participant names from the sheet

---

## Part 7: Technical Details

### Data Collection Schedule

**Midnight Collection (00:00 daily)**:
- Triggers at midnight each day
- Collects previous day's screen time and app usage
- Counts unlocks and notifications from local database
- Saves to local database
- Triggers automatic sync to Google Sheets

**Event-Based Logging**:
- Screen unlocks: Logged immediately when detected
- Notifications: Logged as they arrive
- Stored in local SQLite database

**Daily Reminder**:
- Notification sent at 10:00 AM
- Encourages participants to check their stats
- Part of the study design (feedback loop)

### Database Schema

**daily_usage table**:
- Aggregated daily statistics
- Synced to Google Sheets
- Retained for 30 days

**unlock_events table**:
- Individual unlock timestamps
- Used to compute daily totals
- Cleaned after 7 days

**notification_events table**:
- Individual notification records
- Used to compute daily totals and group by app
- Cleaned after 7 days

### Sync Mechanism

- **Automatic**: Triggered after midnight data collection
- **Manual**: Button in dashboard
- **Retry Logic**: Failed syncs remain marked as unsynced
- **Offline Support**: Data queued locally if no internet

---

## Part 8: Customization Options

### Changing Collection Time

In `MidnightScheduler.kt`, modify the calendar setup:
```kotlin
calendar.set(Calendar.HOUR_OF_DAY, 0)  // Change to desired hour
```

### Changing Reminder Time

In `DailyReminderScheduler.kt`:
```kotlin
calendar.set(Calendar.HOUR_OF_DAY, 10)  // Change to desired hour
```

### Modifying Study Duration

The app tracks "Study Day" automatically. To change from 10 days:
- Update dashboard text displays
- Optionally add auto-complete logic when day > 10

### Adding More Metrics

To collect additional data:
1. Add fields to `DailyUsage` data model
2. Update `UsageStatsCollector` to collect the new data
3. Update Google Sheets column headers
4. Update `GoogleSheetsSync` to include new fields

---

## Part 9: Testing

### Before Distribution

**Test on Real Device**:
1. Install the app
2. Complete setup with test name
3. Use phone normally for 24 hours
4. Check that midnight collection works (check logs)
5. Verify data appears in dashboard next day
6. Test manual sync to Google Sheets
7. Verify data appears correctly in Sheet

**Checklist**:
- [ ] App installs without errors
- [ ] Permissions can be granted
- [ ] Dashboard loads without crashes
- [ ] Midnight alarm triggers
- [ ] Daily notification appears
- [ ] Google Sheets sync works
- [ ] Data format is correct
- [ ] App survives device reboot

### During Study

- Check Sheet daily for incoming data
- Monitor for any gaps in data collection
- Be available to help participants with issues

---

## Part 10: Common Issues & Solutions

### Issue: Google Sheets Sync Fails

**Solutions**:
- Check internet connectivity
- Verify Google account has access to the Sheet
- Check Sheet ID is correct
- Ensure Sheets API is enabled in Cloud Console

### Issue: No Data After Midnight

**Solutions**:
- Check app has Usage Access permission
- Verify app wasn't killed by battery optimization
- Check device logs for errors
- Ensure midnight alarm was scheduled

### Issue: App Stops After Device Reboot

**Solutions**:
- Verify RECEIVE_BOOT_COMPLETED permission is granted
- Check BootReceiver is registered in manifest
- Some devices require manual "auto-start" permission

### Issue: Dashboard Shows "No Data"

**Solutions**:
- Remember: first data appears day 2 (yesterday's data)
- Check local database has entries
- Verify midnight collection ran

---

## Part 11: Payment Tracking

### Tracking Completion

To verify participants completed the 10-day study:

1. Check Google Sheet for each participant
2. Count number of rows per participant (should be 10)
3. Check Study Day column goes from 1 to 10
4. Verify dates are consecutive

### Export for Payment

Create a summary sheet with:
```
=QUERY(Sheet1!A:C, "SELECT B, COUNT(B) WHERE B IS NOT NULL GROUP BY B")
```

This shows each participant and their number of submitted days.

---

## Support & Maintenance

### App Updates

If you need to update the app:
1. Increment version in `build.gradle`
2. Build new APK
3. Distribute to participants
4. Data will continue from where it left off

### Uninstalling

Participants can uninstall after study completion. All data is already synced to your Google Sheet.

---

## Legal & Compliance

**Recommended Additions**:
- Privacy Policy (host on a webpage, link in app)
- Terms of Service
- IRB approval documentation
- Informed consent form

---

## Conclusion

This app provides a robust solution for collecting smartphone usage data. The combination of automatic data collection, local storage, and cloud sync ensures reliable data gathering with minimal participant burden.

For questions or issues, refer to the Android documentation or contact your development team.

**Happy Researching!** 📊📱
