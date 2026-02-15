# Quick Start Guide for Researchers

## 🚀 Get Started in 5 Steps

### Step 1: Google Cloud Setup (15 minutes)
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create new project: "Usage Tracker Research"
3. Enable "Google Sheets API"
4. Create OAuth 2.0 credentials for Android app
   - Package: `com.research.usagetracker`
   - Get SHA-1: `keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey`

### Step 2: Create Data Collection Sheet (5 minutes)
1. Create new Google Sheet
2. Add headers in row 1:
   ```
   Date | User Name | Anonymous ID | Study Day | Screen Time (min) | Top Apps | Total Unlocks | Top Unlock Apps | Total Notifications | Top Notification Apps
   ```
3. Copy the Sheet ID from URL
4. Share with your Google account

### Step 3: Configure App (2 minutes)
In `AppPreferences.kt`, line 28:
```kotlin
fun getSheetId(): String = "PASTE_YOUR_SHEET_ID_HERE"
```

### Step 4: Build APK (5 minutes)
1. Open project in Android Studio
2. Build → Generate Signed Bundle/APK → APK
3. Choose debug or release variant
4. APK location: `app/build/outputs/apk/`

### Step 5: Distribute to Participants
1. Share APK file with participants
2. Provide installation instructions (see below)
3. Monitor Google Sheet for incoming data

---

## 📱 Participant Installation Instructions

**Send this to your participants:**

### Installing the App

1. **Download** the APK file I sent you
2. **Enable Unknown Sources**: 
   - Go to Settings → Security
   - Enable "Unknown Sources" or "Install Unknown Apps"
3. **Install**: Open the APK file and tap Install

### First-Time Setup

1. **Enter Your Name**: Type your full name (needed for payment)
2. **Grant Usage Access**:
   - Tap "Grant Usage Access"
   - Find "Usage Tracker" in the list
   - Turn it ON
3. **Grant Notification Access**:
   - Tap "Grant Notification Access"  
   - Find "Usage Tracker" in the list
   - Turn it ON
4. **Done!** Tap "Continue to Dashboard"

### Daily Usage

- **Just use your phone normally**
- **Every morning at 10 AM**, you'll get a reminder to check your stats
- **Open the app** to see how much you used your phone yesterday
- **Data syncs automatically** - nothing else needed!

### What We're Tracking

✅ Total screen time per day  
✅ Which apps you use and for how long  
✅ How many times you unlock your phone  
✅ How many notifications you get  

❌ We don't see notification content  
❌ We don't see what you do in apps  
❌ We don't track your location  

### Study Duration

- **10 days total**
- You'll see "Study Day 1 of 10" in the app
- After Day 10, you're done and will receive payment

---

## 📊 Monitoring Data Collection

### Check Your Google Sheet Daily

Look for:
- New rows appearing each day
- All participants represented
- Study Day incrementing correctly
- No blank or zero values

### If Data is Missing

Contact the participant and ask them to:
1. Check that permissions are still granted
2. Open the app and tap "Sync to Google Sheets"
3. Make sure the app hasn't been force-closed

---

## 🔧 Troubleshooting

### "App Won't Install"
- Participant needs to enable Unknown Sources
- Check Android version is 7.0 or higher

### "No Permissions Button"
- Participant may need to restart app
- Check device settings manually

### "No Data in Sheet"
- Verify Sheet ID in code is correct
- Check internet connectivity
- Verify Google account access to Sheet

### "App Stops Working After Reboot"
- Some devices require "auto-start" permission
- Find in Settings → Apps → Usage Tracker → Auto-start

---

## 📋 Data Analysis Tips

### Exporting Data

Your Google Sheet has all the data. You can:
- Download as CSV
- Import into SPSS, R, Python
- Analyze directly in Google Sheets

### Key Metrics

- **Screen Time Trend**: Are participants reducing usage?
- **App Patterns**: Which apps dominate usage?
- **Unlock Behavior**: Correlation with screen time?
- **Notification Load**: Impact on engagement?

### Using Anonymous IDs

For analysis:
- Use Anonymous ID column (removes participant identities)
- Keep User Name column only for payment tracking
- Consider removing names after payment

---

## ✅ Completion Checklist

After 10 days:

- [ ] All participants have 10 days of data
- [ ] No major gaps in collection
- [ ] Data looks reasonable
- [ ] Payment amounts calculated
- [ ] Thank participants
- [ ] Consider anonymizing data (remove names)

---

## 🆘 Need Help?

- **Full documentation**: See `SETUP_GUIDE.md`
- **Code issues**: Check Android Studio build errors
- **Google API issues**: Check Cloud Console logs
- **Participant issues**: Send clear, simple instructions

---

## 💡 Pro Tips

1. **Test yourself first**: Install the app and test for 24 hours
2. **Start on a Monday**: Ensures full work weeks are captured
3. **Over-recruit**: Account for ~20% dropout
4. **Daily check-ins**: Brief participant check-ins improve completion
5. **Clear payment terms**: "Complete all 10 days to receive $XX"

**Good luck with your research!** 🎉
