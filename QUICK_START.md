# Quick Start Guide - Firebase Backend Version

## 🚀 Get Started in 6 Steps (45 minutes total)

### Step 1: Firebase Project Setup (10 minutes)

1. Go to https://console.firebase.google.com/
2. Click "Add project"
3. Name: "usage-tracker-research"
4. Disable Google Analytics
5. **Upgrade to Blaze plan** (pay-as-you-go, FREE for research)
6. Enable **Firestore Database** (production mode)
7. Enable **Cloud Functions**

### Step 2: Service Account Setup (10 minutes)

1. Go to https://console.cloud.google.com/
2. Select your Firebase project
3. Menu → "IAM & Admin" → "Service Accounts"
4. Click "CREATE SERVICE ACCOUNT"
   - Name: `usage-tracker-backend`
   - Click CREATE → CONTINUE → DONE
5. Click the three dots → "Manage keys" → "ADD KEY" → JSON
6. **Download JSON** file (KEEP SECURE!)
7. **Copy the service account email** (looks like: `...@PROJECT.iam.gserviceaccount.com`)
8. Enable **Google Sheets API**: Menu → "APIs & Services" → "Library" → Search "Sheets" → Enable

### Step 3: Google Sheet Setup (5 minutes)

1. Create new Google Sheet at https://sheets.google.com
2. Name it: "Usage Tracker Research Data"
3. Add column headers in row 1:
   ```
   Date | User Name | Anonymous ID | Study Day | Screen Time (min) | Top Apps | Total Unlocks | Top Unlock Apps | Total Notifications | Top Notification Apps
   ```
4. Click "Share" → Paste service account email → Set as "Editor" → UNCHECK "Notify" → Share
5. Copy Sheet ID from URL: `https://docs.google.com/spreadsheets/d/[COPY_THIS_PART]/edit`

### Step 4: Deploy Firebase Backend (15 minutes)

```bash
# Install Firebase CLI
npm install -g firebase-tools

# Login
firebase login

# Navigate to backend folder
cd firebase-backend

# Set configuration
firebase functions:config:set sheets.id="YOUR_SHEET_ID"
firebase functions:config:set service.email="YOUR_SERVICE_ACCOUNT_EMAIL"
firebase functions:config:set service.key="YOUR_PRIVATE_KEY_FROM_JSON"

# Deploy
cd functions && npm install && cd ..
firebase deploy --only functions
```

**Save the function URLs from output!** You'll need them.

### Step 5: Configure Android App (3 minutes)

In `FirebaseSync.kt`, update line 16:
```kotlin
private const val FIREBASE_FUNCTION_URL = "https://YOUR-REGION-YOUR-PROJECT.cloudfunctions.net/syncUsageData"
```

Replace with your actual Firebase Function URL from Step 4.

### Step 6: Build & Test (10 minutes)

1. Open project in Android Studio
2. Build → Generate Signed Bundle/APK → APK
3. Install on test device
4. Register with test name
5. Check if data appears in Google Sheet!

---

## 🎯 What Makes This Secure?

### OLD (Flawed) Approach:
❌ Participants had Editor access to sheet  
❌ Could view all participants' data  
❌ OAuth popups during sync  
❌ Access to ALL participant spreadsheets  

### NEW (Secure) Firebase Approach:
✅ Service account writes to sheet  
✅ Participants can't access sheet  
✅ No Google account needed  
✅ Silent background sync  
✅ Complete privacy  

**See `MIGRATION_GUIDE.md` for full explanation.**

---

## 📱 Participant Instructions

**Send this to participants:**

### Installation

1. Download APK
2. Enable "Unknown Sources" in Settings
3. Install and open app

### Setup

1. Enter your name (needed for payment)
2. Wait for server registration
3. Grant "Usage Access" permission
4. Grant "Notification Access" permission
5. Done!

### Daily Use

- Use phone normally
- Check stats when notified (10 AM daily)
- Data syncs automatically
- **No Google account needed!**

---

## 📊 Monitoring

### Check Sync Logs
```bash
firebase functions:log --only syncUsageData
```

### View Statistics
Visit: `YOUR_FIREBASE_URL/getStats`

### Monitor Costs
Firebase Console → Usage and billing  
**Expected:** $0 (free tier covers research)

---

## 🔧 Troubleshooting

**"Registration failed"**  
→ Check internet connection  
→ Verify Firebase Function URL in code

**"Billing account not configured"**  
→ Upgrade to Blaze plan

**"Permission denied" on sheet**  
→ Share sheet with service account email

**Test endpoints:**
```bash
# Health check
curl YOUR_URL/healthCheck

# Test registration  
curl -X POST YOUR_URL/registerParticipant \
  -H "Content-Type: application/json" \
  -d '{"userName":"Test","anonymousId":"test123"}'
```

---

## 📖 Full Documentation

- **Firebase Setup:** `firebase-backend/FIREBASE_SETUP.md` (comprehensive)
- **Architecture:** `MIGRATION_GUIDE.md` (security explanation)
- **Technical:** `README.md` (full details)

---

## ✅ Pre-Launch Checklist

- [ ] Firebase project created with Blaze plan
- [ ] Cloud Functions deployed
- [ ] Service account created
- [ ] Google Sheet shared with service account
- [ ] Function URL updated in app
- [ ] APK built and tested
- [ ] Test sync completed successfully
- [ ] Budget alerts set ($5, $10)

**You're ready!** 🚀
