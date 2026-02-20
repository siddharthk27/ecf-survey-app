# Firebase Backend Setup Guide

## 🎯 Overview

This guide will walk you through setting up the Firebase backend for your Usage Tracker app. The Firebase backend provides:

✅ **Secure data sync** - Service account writes to Google Sheets  
✅ **No participant Google accounts needed** - Anonymous tokens  
✅ **Complete privacy** - Participants can't see others' data  
✅ **Automatic background sync** - No OAuth popups  
✅ **Audit trail** - All syncs logged in Firestore  
✅ **Free tier** - No cost for research studies  

## 📋 Prerequisites

- Google account
- Node.js 18+ installed (for local testing)
- Basic command line knowledge

## 🚀 Step 1: Create Firebase Project (5 minutes)

1. **Go to Firebase Console**
   - Visit https://console.firebase.google.com/
   - Click "Add project"

2. **Create Project**
   - Project name: "usage-tracker-research"
   - Accept terms and click "Continue"
   - Disable Google Analytics (not needed for research)
   - Click "Create project"
   - Wait for project creation
   - Click "Continue"

3. **Upgrade to Blaze Plan (Pay-as-you-go)**
   - Click "⚙️" (settings) in left sidebar
   - Select "Usage and billing"
   - Click "Upgrade"
   - **Why?** Cloud Functions require Blaze plan
   - **Cost?** Free for your usage (<125K calls/month)
   - Enter billing information
   - **Important:** Set budget alerts at $5 and $10 for safety

## 🔧 Step 2: Enable Required Services (3 minutes)

### Enable Firestore

1. In Firebase Console, click "Firestore Database" in left menu
2. Click "Create database"
3. Choose "Start in production mode"
4. Select location closest to you (e.g., "us-central")
5. Click "Enable"

### Enable Cloud Functions

1. Click "Functions" in left menu
2. Click "Get started"
3. This enables Cloud Functions API

## 🔑 Step 3: Create Google Service Account (10 minutes)

This service account will write to your Google Sheets securely.

### Create Service Account

1. **Go to Google Cloud Console**
   - Visit https://console.cloud.google.com/
   - Select your Firebase project from dropdown

2. **Navigate to Service Accounts**
   - Click "≡" menu → "IAM & Admin" → "Service Accounts"

3. **Create Service Account**
   - Click "+ CREATE SERVICE ACCOUNT"
   - Service account name: `usage-tracker-backend`
   - Service account ID: (auto-filled)
   - Description: "Backend service for Usage Tracker research app"
   - Click "CREATE AND CONTINUE"

4. **Grant Permissions**
   - Skip role assignment (not needed)
   - Click "CONTINUE"
   - Click "DONE"

5. **Create Key**
   - Find your new service account in the list
   - Click the three dots (⋮) → "Manage keys"
   - Click "ADD KEY" → "Create new key"
   - Choose "JSON"
   - Click "CREATE"
   - **Important:** A JSON file downloads - KEEP THIS SECURE!

6. **Copy Service Account Email**
   - The email looks like: `usage-tracker-backend@PROJECT-ID.iam.gserviceaccount.com`
   - **Copy this email** - you'll need it for Google Sheets

### Enable Google Sheets API

1. In Google Cloud Console, click "≡" → "APIs & Services" → "Library"
2. Search for "Google Sheets API"
3. Click on it
4. Click "ENABLE"

## 📊 Step 4: Create Google Sheet (5 minutes)

1. **Create New Sheet**
   - Go to https://sheets.google.com/
   - Click "+ Blank" to create new sheet
   - Name it: "Usage Tracker Research Data"

2. **Add Headers** (Row 1):
   ```
   Date | User Name | Anonymous ID | Study Day | Screen Time (min) | Top Apps | Total Unlocks | Top Unlock Apps | Total Notifications | Top Notification Apps
   ```

3. **Share with Service Account**
   - Click "Share" button (top right)
   - Paste your service account email
   - Change role to "Editor"
   - **UNCHECK** "Notify people"
   - Click "Share"

4. **Copy Sheet ID**
   - Look at URL: `https://docs.google.com/spreadsheets/d/[SHEET_ID]/edit`
   - Copy the `[SHEET_ID]` part
   - Save it for later

## 💻 Step 5: Install Firebase CLI (2 minutes)

```bash
# Install Firebase CLI globally
npm install -g firebase-tools

# Login to Firebase
firebase login

# This opens a browser - login with your Google account
```

## 📦 Step 6: Deploy Firebase Functions (10 minutes)

### Navigate to Backend Directory

```bash
cd firebase-backend
```

### Initialize Firebase (if not already done)

```bash
firebase init

# Select your project
# Use arrow keys to select: Firestore, Functions
# Press Space to select, Enter to confirm

# Firestore Setup:
# - Use default firestore.rules
# - Use default firestore.indexes.json

# Functions Setup:
# - Language: JavaScript
# - ESLint: No
# - Install dependencies: Yes
```

### Configure Environment Variables

```bash
# Set Google Sheet ID
firebase functions:config:set sheets.id="YOUR_SHEET_ID_HERE"

# Set service account credentials
firebase functions:config:set \
  service.email="YOUR_SERVICE_ACCOUNT_EMAIL" \
  service.key="$(cat path/to/your-service-account-key.json | jq -c . | jq -R .)"
```

**Alternative method** (if above doesn't work):

```bash
# Open the service account JSON file
# Copy the entire "private_key" value (including \n characters)

firebase functions:config:set service.email="your-service-account@PROJECT.iam.gserviceaccount.com"
firebase functions:config:set service.key="-----BEGIN PRIVATE KEY-----\nYourKeyHere\n-----END PRIVATE KEY-----\n"
```

### Deploy Functions

```bash
cd functions
npm install

cd ..
firebase deploy --only functions
```

Wait for deployment (2-3 minutes). You'll see output like:

```
✔ functions[syncUsageData]: Successful create operation.
✔ functions[registerParticipant]: Successful create operation.
✔ functions[healthCheck]: Successful create operation.
✔ functions[getStats]: Successful create operation.

Function URL (syncUsageData): https://YOUR-REGION-YOUR-PROJECT.cloudfunctions.net/syncUsageData
```

**Copy the function URLs** - you'll need them for the Android app!

## 📱 Step 7: Configure Android App (5 minutes)

### Update FirebaseSync.kt

1. Open `FirebaseSync.kt` in your Android project
2. Find line: `private const val FIREBASE_FUNCTION_URL = "YOUR_FIREBASE_FUNCTION_URL/syncUsageData"`
3. Replace with your actual URL (from deploy output)

Example:
```kotlin
private const val FIREBASE_FUNCTION_URL = "https://us-central1-usage-tracker-research.cloudfunctions.net/syncUsageData"
```

### Build the App

```bash
# In Android Studio:
# Build → Generate Signed Bundle / APK
# Choose APK
# Select release variant
# Sign with your keystore
```

## ✅ Step 8: Test the Setup (10 minutes)

### Test Participant Registration

```bash
# Test the registration endpoint
curl -X POST \
  YOUR_FIREBASE_FUNCTION_URL/registerParticipant \
  -H "Content-Type: application/json" \
  -d '{
    "userName": "Test User",
    "anonymousId": "test1234"
  }'

# Expected response:
# {
#   "success": true,
#   "participantToken": "abc123...",
#   "message": "Participant registered successfully"
# }
```

### Test Data Sync

```bash
# Save the token from above, then test sync:
curl -X POST \
  YOUR_FIREBASE_FUNCTION_URL/syncUsageData \
  -H "Content-Type: application/json" \
  -d '{
    "participantToken": "TOKEN_FROM_ABOVE",
    "data": [{
      "date": "2024-02-20",
      "userName": "Test User",
      "anonymousId": "test1234",
      "studyDay": 1,
      "totalScreenTimeMin": 120,
      "topApps": "TestApp: 60min",
      "totalUnlocks": 50,
      "topUnlockApps": "TestApp: 30x",
      "totalNotifications": 100,
      "topNotificationApps": "TestApp: 50"
    }]
  }'

# Expected response:
# {
#   "success": true,
#   "rowsAdded": 1,
#   "message": "Data synced successfully"
# }
```

### Check Google Sheet

- Open your Google Sheet
- You should see a new row with the test data!
- If you see it, **everything is working!** 🎉

### Clean Up Test Data

- Delete the test row from your sheet
- You're ready for real participants!

## 🔒 Step 9: Security Checklist

- [ ] Service account JSON key is stored securely (NOT in git)
- [ ] Firebase Functions config is set correctly
- [ ] Google Sheet is shared ONLY with service account email
- [ ] Firestore security rules are deployed
- [ ] You tested registration and sync
- [ ] Budget alerts are set in Firebase

## 📊 Step 10: Monitoring Your Backend

### View Logs

```bash
# Real-time function logs
firebase functions:log --only syncUsageData

# Or in Firebase Console:
# Functions → Select function → Logs tab
```

### Check Sync Statistics

Visit: `YOUR_FIREBASE_FUNCTION_URL/getStats`

Shows:
- Total participants registered
- Active participants
- Recent sync attempts

### Monitor Costs

- Firebase Console → ⚙️ → Usage and billing
- Should be $0 for research studies
- 125K function calls/month on free tier

## 🐛 Troubleshooting

### Function Deployment Fails

**Error:** "Billing account not configured"
- **Solution:** Upgrade to Blaze plan (Step 1)

**Error:** "Service account key is invalid"
- **Solution:** Regenerate key and update config:
  ```bash
  firebase functions:config:set service.key="YOUR_NEW_KEY"
  firebase deploy --only functions
  ```

### Sync Returns 403 Error

**Problem:** Invalid participant token
- **Solution:** Check token was registered correctly
- Test with the registration endpoint first

### Sync Returns 500 Error

**Check these:**
1. Service account has Editor access to sheet
2. Google Sheets API is enabled
3. Sheet ID is correct in config
4. Service account credentials are valid

**View detailed error:**
```bash
firebase functions:log --only syncUsageData
```

### App Can't Connect

**Error:** "Failed to sync data"
- **Check:** Internet connection on device
- **Check:** Firebase Function URL is correct in `FirebaseSync.kt`
- **Check:** Function is deployed and running

**Test with curl:**
```bash
curl YOUR_FIREBASE_FUNCTION_URL/healthCheck
# Should return: {"status":"healthy",...}
```

## 💡 Tips & Best Practices

### During Study

1. **Monitor daily**
   - Check Google Sheet for incoming data
   - Review function logs for errors
   - Watch Firestore for participant count

2. **Backup regularly**
   - Download Google Sheet as CSV weekly
   - Firebase handles backend backups automatically

3. **Budget safety**
   - Free tier: 125K invocations/month
   - Your study: ~10 participants × 10 days × 2 calls/day = 200 calls
   - You have 600x headroom!

### After Study

1. **Export data**
   - Download Google Sheet as CSV
   - Save to secure location

2. **Clean up** (optional)
   - Delete Firestore participants collection
   - Delete Firebase Functions
   - Keep Google Sheet for records

## 🆘 Getting Help

### Common Questions

**Q: Can participants see the Google Sheet?**
A: No! Only the service account has access. Participants have no way to access it.

**Q: What if my service account key leaks?**
A: Immediately:
1. Go to Google Cloud Console → Service Accounts
2. Delete the compromised key
3. Create new key
4. Update Firebase config
5. Redeploy functions

**Q: How do I add more researchers to access data?**
A: Share the Google Sheet with their emails (give them Viewer or Editor access)

**Q: Can I use a database instead of Google Sheets?**
A: Yes! Modify the Cloud Function to write to Firestore instead. The architecture supports this.

### Support Resources

- **Firebase Docs:** https://firebase.google.com/docs/functions
- **Google Sheets API:** https://developers.google.com/sheets/api
- **Firebase Status:** https://status.firebase.google.com/

---

## 🎉 You're Done!

Your secure Firebase backend is now:
- ✅ Protecting participant privacy
- ✅ Syncing data automatically
- ✅ Logging all activity
- ✅ Scaling automatically
- ✅ Costing $0 for research

Participants can:
- ✅ Register without Google accounts
- ✅ Sync in background automatically
- ✅ Never see others' data
- ✅ Have complete privacy

**Deploy your app and start collecting data!**

---

**Version:** 1.0 - Firebase Backend  
**Updated:** February 2026  
**Deployment Time:** ~45 minutes total
