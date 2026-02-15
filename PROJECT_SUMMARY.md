# Usage Tracker App - Project Summary

## 📦 What You Received

This complete Android application package includes everything you need to run a smartphone usage research study.

### Core Application Files

**Main Activities & Services:**
- `MainActivity.kt` - User registration screen
- `PermissionSetupActivity.kt` - Guides users through granting permissions
- `DashboardActivity.kt` - Displays usage statistics and charts
- `NotificationListener.kt` - Tracks incoming notifications (event-based)
- `UsageTrackingService.kt` - Monitors screen unlocks (event-based)

**Data Collection & Sync:**
- `UsageStatsCollector.kt` - Collects screen time and app usage data
- `GoogleSheetsSync.kt` - Syncs data to Google Sheets
- `MidnightScheduler.kt` - Triggers daily data collection at midnight
- `DailyReminderScheduler.kt` - Sends daily notifications to users

**Data Models & Storage:**
- `DataModels.kt` - Data structures for all tracked metrics
- `UsageDatabase.kt` - Room database implementation with DAOs
- `AppPreferences.kt` - Manages user settings and configuration

**System Integration:**
- `BootReceiver.kt` - Restarts services after device reboot
- `AndroidManifest.xml` - App permissions and component declarations
- `build.gradle` - Dependencies and build configuration

**UI Layouts:**
- `res/layout/activity_main.xml` - Welcome and registration screen
- `res/layout/activity_permission_setup.xml` - Permission guide
- `res/layout/activity_dashboard.xml` - Statistics dashboard with charts
- `res/values/strings.xml` - All UI text strings

### Documentation Files

**Quick Start (Read This First!):**
- `QUICK_START.md` - 5-minute setup guide for getting started immediately

**Comprehensive Guides:**
- `SETUP_GUIDE.md` - Complete technical documentation (50+ pages)
  - Google Cloud setup
  - Building the APK
  - Participant instructions
  - Troubleshooting
  - Customization options

**Research Materials:**
- `README.md` - Project overview and technical specifications
- `CONSENT_FORM_TEMPLATE.md` - IRB-ready consent form template
- `DEPLOYMENT_CHECKLIST.md` - Step-by-step deployment checklist

---

## 🎯 What This App Does

### Automatic Data Collection

**Daily Collection (Midnight):**
- Total screen time for the day
- Time spent in each app
- Aggregated unlock count
- Aggregated notification count
- Which apps sent notifications

**Real-Time Event Logging:**
- Every phone unlock is logged with timestamp
- Every notification is logged with app source and timestamp

**Daily User Engagement:**
- Dashboard shows yesterday's statistics
- Visual charts for screen time trends
- Breakdown of top apps used
- Notification sources listed
- Daily reminder notification at 10 AM

### Data Flow

```
Participant Uses Phone
         ↓
App Tracks Events → Local SQLite Database
         ↓
Midnight: Aggregates Daily Stats
         ↓
Automatic Sync → Your Google Sheet
         ↓
You Access Research Data
```

---

## 🚀 Next Steps

### Immediate Actions (30 minutes)

1. **Read `QUICK_START.md`** - This will guide you through the essential setup

2. **Google Cloud Setup**:
   - Create Google Cloud project
   - Enable Sheets API
   - Create OAuth credentials

3. **Create Google Sheet**:
   - Set up data collection spreadsheet
   - Add column headers
   - Get Sheet ID

4. **Configure App**:
   - Add your Sheet ID to the code
   - Update OAuth settings

5. **Build APK**:
   - Open in Android Studio
   - Build the APK file
   - Test on your own device first

### Before Participant Deployment (1-2 weeks)

1. **Testing**: Run the app on your own phone for 2-3 days
2. **IRB Approval**: Submit for ethical review if required
3. **Consent Forms**: Prepare participant consent documents
4. **Recruitment**: Identify and screen participants
5. **Support Plan**: Set up participant support channels

### During Study (10 days)

1. **Monitor**: Check Google Sheet daily
2. **Support**: Respond to participant questions
3. **Track**: Ensure all participants are syncing data
4. **Backup**: Regularly backup your data

### After Study

1. **Verify**: Check all participants completed 10 days
2. **Pay**: Process participant compensation
3. **Clean**: Prepare data for analysis
4. **Anonymize**: Remove participant names from analysis dataset

---

## 📊 Expected Data Output

Your Google Sheet will have rows like this:

| Date       | User Name  | Anon ID | Day | Screen(min) | Top Apps          | Unlocks | Notifs | Top Notif Apps    |
|------------|-----------|---------|-----|------------|-------------------|---------|--------|-------------------|
| 2024-02-15 | John Doe  | a1b2c3d4| 1   | 245        | Instagram: 120;   | 87      | 156    | WhatsApp: 45;     |
|            |           |         |     |            | Chrome: 65; ...   |         |        | Gmail: 32; ...    |

Each participant generates **10 rows** (one per day).

---

## 🔒 Privacy & Security

### What's Collected:
✅ Screen time totals  
✅ App names and usage time  
✅ Unlock counts  
✅ Notification counts and sources  

### What's NOT Collected:
❌ Notification content  
❌ Messages or calls  
❌ Location data  
❌ Photos or files  
❌ What users do inside apps  
❌ Passwords or sensitive data  

### Security Measures:
- Data encrypted in transit (HTTPS)
- Secure Google Cloud infrastructure
- Access limited to research team
- Anonymous IDs for analysis
- Participant names only for payment tracking

---

## 💰 Study Economics

**Typical Setup:**
- Study duration: 10 days
- Compensation: $20-50 per participant (your choice)
- Dropout rate: Plan for ~20% attrition
- Sample size: Recruit 25% more than needed

**Example:**
- Need 40 complete datasets
- Recruit 50 participants
- Budget: 50 × $30 = $1,500

---

## ⚙️ Technical Requirements

**For Building:**
- Android Studio (latest version)
- Android SDK (API 24+)
- Java 8+
- Google Cloud account

**For Participants:**
- Android 7.0 or higher
- Internet connection for syncing
- ~50MB storage space
- Normal battery usage

---

## 📖 Documentation Structure

1. **QUICK_START.md** ← START HERE
   - 5-minute setup
   - Essential steps only
   - Participant installation guide

2. **SETUP_GUIDE.md** ← Comprehensive Reference
   - Complete technical details
   - Google Cloud configuration
   - Building instructions
   - Troubleshooting guide
   - Customization options

3. **README.md** ← Project Overview
   - Feature list
   - Architecture overview
   - Privacy information
   - Technical specifications

4. **CONSENT_FORM_TEMPLATE.md** ← IRB Materials
   - Ready-to-use consent form
   - Participant rights
   - Data privacy disclosure

5. **DEPLOYMENT_CHECKLIST.md** ← Project Management
   - Pre-deployment checklist
   - Monitoring procedures
   - Post-study tasks

---

## 🆘 Common Questions

**Q: Do I need programming experience?**
A: Basic understanding helps, but the QUICK_START guide walks you through everything. Android Studio handles most complexity.

**Q: How much does this cost to run?**
A: Google Cloud/Sheets is free for research use at this scale. Main cost is participant compensation.

**Q: Can I customize the app?**
A: Yes! The SETUP_GUIDE shows how to change collection times, study duration, metrics, etc.

**Q: What if a participant's data doesn't sync?**
A: They can manually tap "Sync to Google Sheets" in the app. The SETUP_GUIDE has a troubleshooting section.

**Q: How do I analyze the data?**
A: Export from Google Sheets to CSV, then use R, Python, SPSS, or even Excel. The data is pre-formatted for analysis.

**Q: Is this legal/ethical?**
A: The app is designed for legitimate research. You must obtain IRB approval and informed consent. See CONSENT_FORM_TEMPLATE.md.

---

## 🎓 Research Use Cases

This app is perfect for studies on:
- Digital wellbeing interventions
- Self-monitoring and behavior change
- App addiction and usage patterns
- Notification impacts on attention
- Screen time reduction strategies
- Technology use in different demographics

---

## 📞 Support Resources

**In the Documentation:**
- Technical setup: SETUP_GUIDE.md
- Quick answers: QUICK_START.md
- Consent templates: CONSENT_FORM_TEMPLATE.md
- Project management: DEPLOYMENT_CHECKLIST.md

**External Resources:**
- Android Studio: https://developer.android.com/studio
- Google Sheets API: https://developers.google.com/sheets/api
- Google Cloud: https://console.cloud.google.com

---

## ✅ Pre-Flight Checklist

Before distributing to participants, verify:

- [ ] Google Cloud project created and API enabled
- [ ] Google Sheet created with correct headers
- [ ] Sheet ID added to app code
- [ ] APK builds without errors
- [ ] Tested on your own device for 24+ hours
- [ ] Data appears correctly in Google Sheet
- [ ] IRB approval obtained (if required)
- [ ] Consent forms prepared
- [ ] Participant instructions ready
- [ ] Support contact method established

---

## 🎉 You're Ready!

You now have everything needed to run a professional smartphone usage research study. The app has been designed with both researchers and participants in mind - making data collection automatic while providing engaging feedback to users.

**Recommended First Steps:**
1. Read QUICK_START.md (15 minutes)
2. Set up Google Cloud & Sheets (15 minutes)
3. Build and test the app (1-2 hours)
4. Refine based on your specific needs
5. Deploy to participants!

**Good luck with your research!** 📱📊🔬

---

*Built with care for behavioral research.*  
*Version 1.0 - February 2026*
