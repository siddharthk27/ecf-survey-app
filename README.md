# Usage Tracker - Smartphone Usage Research App

A comprehensive Android application for collecting smartphone usage data in behavioral research studies.

## 📊 Overview

This app enables researchers to collect detailed smartphone usage metrics from study participants over a 10-day period. It automatically tracks screen time, app usage, phone unlocks, and notifications, syncing all data to Google Sheets for easy analysis.

## ✨ Features

### For Researchers
- ✅ **Automatic Data Collection**: No participant intervention required
- ✅ **Google Sheets Integration**: Real-time data sync
- ✅ **Anonymous IDs**: Privacy-preserving analysis
- ✅ **Reliable Tracking**: Survives reboots and background operation
- ✅ **Comprehensive Metrics**: Screen time, app usage, unlocks, notifications

### For Participants
- ✅ **Easy Setup**: Simple permission grants
- ✅ **Daily Dashboard**: View personal usage stats
- ✅ **Daily Reminders**: Notifications to check stats
- ✅ **Minimal Battery Impact**: Efficient background tracking
- ✅ **Privacy Conscious**: No content, location, or personal data collected

## 📱 What Gets Tracked

### Daily Collection (at Midnight)
- Total screen time
- Per-app usage time
- App names and usage minutes

### Event-Based Collection (Real-Time)
- Phone unlock events
- **Which app was opened after each unlock**
- Notification arrivals
- Source apps of notifications

### Not Collected
- ❌ Notification content
- ❌ App content or screenshots
- ❌ Location data
- ❌ Contacts or messages
- ❌ Browsing history

## 🚀 Quick Start

### For Researchers

1. **Setup Google Cloud & Sheets** (see `QUICK_START.md`)
2. **Build the APK** in Android Studio
3. **Distribute** to participants
4. **Monitor** Google Sheet for data

### For Participants

1. **Install** the APK
2. **Enter your name**
3. **Grant permissions** (Usage Access + Notification Access)
4. **Use phone normally** for 10 days
5. **Check dashboard** when notified daily

## 📁 Project Structure

```
UsageTrackerApp/
├── MainActivity.kt                 # User registration
├── PermissionSetupActivity.kt      # Permission guidance
├── DashboardActivity.kt            # Usage stats display
├── NotificationListener.kt         # Notification tracking
├── UsageTrackingService.kt         # Unlock tracking
├── UsageStatsCollector.kt          # Screen time collection
├── GoogleSheetsSync.kt             # Cloud sync
├── MidnightScheduler.kt            # Daily collection trigger
├── DailyReminderScheduler.kt       # Daily notifications
├── DataModels.kt                   # Data structures
├── UsageDatabase.kt                # Local storage
├── AppPreferences.kt               # Settings storage
├── BootReceiver.kt                 # Restart after reboot
└── res/layout/                     # UI layouts
```

## 🛠 Technical Details

**Platform**: Android 7.0+ (API 24+)  
**Language**: Kotlin  
**Database**: Room (SQLite)  
**Sync**: Google Sheets API v4  
**Charts**: MPAndroidChart  

**Permissions Required**:
- `PACKAGE_USAGE_STATS` - Screen time tracking
- `BIND_NOTIFICATION_LISTENER_SERVICE` - Notification tracking
- `INTERNET` - Data sync
- `RECEIVE_BOOT_COMPLETED` - Auto-restart

## 📖 Documentation

- **`QUICK_START.md`** - 5-minute setup guide for researchers
- **`SETUP_GUIDE.md`** - Comprehensive technical documentation
- **Participant Instructions** - See `QUICK_START.md` section

## 🔒 Privacy & Ethics

### Data Privacy
- Participant names used only for payment tracking
- Anonymous IDs generated for data analysis
- No personal content or messages collected
- Data encrypted in transit (HTTPS)

### Research Ethics
- Obtain IRB approval before deployment
- Inform participants about data collection
- Provide clear consent process
- Allow participants to withdraw

### Compliance
- Follow your institution's research guidelines
- Comply with GDPR/privacy regulations if applicable
- Secure data storage and access

## 🐛 Troubleshooting

### Common Issues

**Data not syncing?**
- Check Google Sheet ID configuration
- Verify internet connectivity
- Ensure Google Sheets API is enabled

**App stops after reboot?**
- Check "auto-start" permission on device
- Verify BOOT_COMPLETED permission

**No dashboard data?**
- Data appears day after first collection
- Check permissions are still granted
- Ensure midnight alarm triggered

See `SETUP_GUIDE.md` for detailed troubleshooting.

## 🔄 Data Flow

```
Phone Usage
    ↓
Event Detection (Unlocks/Notifications) → SQLite Database
    ↓
Midnight Collection (Screen Time) → SQLite Database
    ↓
Automatic Sync → Google Sheets
    ↓
Researcher Access
```

## 📊 Sample Data Output

Google Sheet format:
```
Date       | User Name | Anon ID | Day | Screen(min) | Top Apps        | Unlocks | Unlock Apps     | Notifs | Top Notif Apps
2024-02-15 | John Doe  | a1b2c3d4| 1   | 245         | Instagram: 120; | 87      | Instagram: 42x; | 156    | WhatsApp: 45;
                                                        Chrome: 65; ... |         | WhatsApp: 25x;  |        | Gmail: 32; ...
                                                                        |         | Chrome: 12x;... |        |
```

## 🤝 Contributing

This app was built for research purposes. Feel free to:
- Fork and customize for your study
- Add new metrics or features
- Improve documentation
- Report issues or bugs

## 📄 License

This project is provided as-is for research purposes. Please ensure compliance with your institution's policies and applicable privacy laws.

## 🙏 Acknowledgments

Built with:
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Google Sheets API](https://developers.google.com/sheets/api)
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
- Android Usage Stats API
- Android Notification Listener Service

## ⚠️ Disclaimer

This app is designed for legitimate research purposes only. Researchers must:
- Obtain proper ethical approval
- Inform participants fully
- Protect participant data
- Comply with all applicable laws

## 📧 Support

For technical issues:
1. Check `SETUP_GUIDE.md`
2. Review Android Studio build errors
3. Check Google Cloud Console logs
4. Verify all permissions are granted

---

**Built for behavioral research studies on smartphone usage patterns.**

*Version 1.0 - February 2026*
