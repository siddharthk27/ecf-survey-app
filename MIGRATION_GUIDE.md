# Migration to Firebase Backend - Architecture Change

## 🎯 What Changed and Why

### The Problem with the Original Design

The initial implementation had **serious privacy and security flaws**:

❌ **Privacy Violation**: Participants needed Editor access to your Google Sheet  
❌ **Data Leakage**: Any participant could view all other participants' data  
❌ **Over-Privileged**: App requested access to ALL user spreadsheets  
❌ **Poor UX**: OAuth popups during midnight sync  
❌ **Security Risk**: Participant Google accounts controlled research data  

### The New Firebase Solution

✅ **Complete Privacy**: Service account writes to sheet (no participant access)  
✅ **Data Isolation**: Participants can't see others' data  
✅ **Minimal Permissions**: No Google account needed from participants  
✅ **Seamless UX**: True background sync, no popups  
✅ **Secure**: Service account key protected in Firebase backend  
✅ **Audit Trail**: All syncs logged in Firestore  

## 🏗️ Architecture Comparison

### Old Architecture (Flawed)

```
Android App (OAuth with participant's Google account)
    ↓
Participant grants permission to ALL their spreadsheets
    ↓
App writes directly to Google Sheet
    ↓
Participant can open sheet and see everyone's data ❌
```

**Critical Flaw:** Participants had Editor access to the central research sheet.

### New Architecture (Secure)

```
Android App (anonymous token)
    ↓
Firebase Cloud Function (validates token)
    ↓
Service Account writes to Google Sheet
    ↓
Only researchers can access the sheet ✅
```

**Secure:** Participants never have access to Google Sheets at all.

## 📊 What Changed in the Code

### Removed Files
- `GoogleSheetsSync.kt` - OAuth-based sync (flawed)

### New Files
- `FirebaseSync.kt` - HTTP-based sync to Firebase Functions
- `firebase-backend/` - Complete Firebase backend implementation

### Modified Files

**AppPreferences.kt:**
- Removed: `sheetId` storage
- Added: `participantToken` storage

**MainActivity.kt:**
- Now registers participant with Firebase
- Receives secure token for syncing

**MidnightScheduler.kt:**
- Changed from `GoogleSheetsSync` to `FirebaseSync`

**DashboardActivity.kt:**
- Changed sync button from "Google Sheets" to "Firebase"
- Uses `FirebaseSync` instead

**build.gradle:**
- Removed Google Sheets API dependencies
- Removed OAuth dependencies
- Simplified to basic HTTP only

## 🔑 Key Concepts

### Participant Tokens

**Old way:** Participant's Google account  
**New way:** Anonymous 64-character token

```kotlin
// When participant registers
val token = FirebaseSync.registerParticipant(context, userName, anonymousId)
// Returns: "a7f3e9d2c1b4f5e8..." (secure random token)

// Stored locally on device
prefs.setUserInfo(userName, anonymousId, token)

// Used for all future syncs
FirebaseSync.syncData(context) // Uses stored token
```

**Security:**
- Token is cryptographically random
- Stored in Firebase Firestore
- Can be revoked if needed
- No connection to participant's identity

### Service Account

**What it is:** A "bot" Google account that writes to your sheet

**Why it's secure:**
- Private key stored safely in Firebase (not in the app)
- Only your backend can use it
- Participants never see it
- Can't be extracted from APK

**How it works:**
```javascript
// In Firebase Cloud Function
const auth = new google.auth.JWT({
  email: SERVICE_ACCOUNT_EMAIL,
  key: SERVICE_ACCOUNT_PRIVATE_KEY,
  scopes: ['https://www.googleapis.com/auth/spreadsheets']
});

// Service account writes to sheet
await sheets.spreadsheets.values.append({
  spreadsheetId: SHEET_ID,
  range: 'Sheet1!A:J',
  valueInputOption: 'RAW',
  requestBody: { values: rows }
});
```

### Firebase Cloud Functions

**What they are:** Serverless backend functions

**Why we use them:**
- No server to manage
- Auto-scaling
- Free tier generous (125K calls/month)
- Built-in HTTPS endpoints

**Endpoints we created:**

1. **`/registerParticipant`**
   - Registers new participant
   - Returns secure token
   - Stores in Firestore

2. **`/syncUsageData`**
   - Validates participant token
   - Writes data to Google Sheets
   - Logs sync in Firestore

3. **`/healthCheck`**
   - Checks if backend is running
   - For debugging

4. **`/getStats`**
   - Shows sync statistics
   - For researchers

## 🔒 Security Improvements

### Privacy Protection

| Aspect | Old | New |
|--------|-----|-----|
| Participant sees sheet? | YES ❌ | NO ✅ |
| Participant Google account needed? | YES ❌ | NO ✅ |
| Access to personal spreadsheets? | YES ❌ | NO ✅ |
| Can participant delete data? | YES ❌ | NO ✅ |
| Anonymous research possible? | NO ❌ | YES ✅ |

### Data Flow Security

**Old (Insecure):**
```
Participant device → Participant's Google OAuth → Your Sheet
└─ Participant has full access to sheet
```

**New (Secure):**
```
Participant device → Anonymous token → Firebase Function → Service Account → Your Sheet
└─ Participant has zero access to sheet
```

### Audit Trail

The new system logs everything:

```javascript
// Every sync is logged in Firestore
{
  participantToken: "a7f3e9d2...",  // Partial, for privacy
  rowCount: 1,
  timestamp: "2024-02-20T14:30:00Z",
  success: true
}
```

You can:
- See who synced when
- Identify failed syncs
- Track participant compliance
- Debug issues easily

## 📱 User Experience Improvements

### Registration Flow

**Old:**
1. Enter name
2. Immediately proceed to permissions
3. No backend validation

**New:**
1. Enter name
2. Register with Firebase backend
3. Receive secure token
4. Proceed to permissions
5. ✅ Validated participant

### Sync Flow

**Old:**
1. Midnight sync triggers
2. OAuth popup appears 😱
3. User must sign in
4. Sync happens
5. Often fails if phone locked

**New:**
1. Midnight sync triggers
2. Silent background HTTP POST
3. No user interaction
4. Sync completes
5. ✅ Works even if phone locked

### Error Handling

**Old:**
- OAuth failures → vague errors
- Permission issues → confusing
- Hard to debug

**New:**
- HTTP status codes → clear errors
- Detailed logs in Firebase
- Easy to troubleshoot

## 🔧 For Developers

### Testing Locally

```bash
# Start Firebase emulators
cd firebase-backend
firebase emulators:start

# Functions run at: http://localhost:5001/PROJECT-ID/us-central1/syncUsageData

# Test with curl
curl -X POST http://localhost:5001/.../registerParticipant \
  -H "Content-Type: application/json" \
  -d '{"userName":"Test","anonymousId":"test123"}'
```

### Debugging

**Android app logs:**
```bash
adb logcat | grep FirebaseSync
```

**Firebase function logs:**
```bash
firebase functions:log --only syncUsageData
```

**Check sync status:**
```bash
curl YOUR_FIREBASE_URL/getStats
```

### Adding Features

Want to add new data fields?

1. **Update DailyUsage model** (Android)
2. **Update Firebase Function** to accept new fields
3. **Update Google Sheet** headers

Example:
```javascript
// In Firebase Function index.js
const row = [
  item.date,
  item.userName,
  // ... existing fields ...
  item.newField,  // Add here
];
```

## 📊 Cost Comparison

### Google Sheets API Quota (Old)

- Free tier: 100 requests/100 seconds per user
- Could hit limits with many participants
- OAuth overhead

### Firebase Free Tier (New)

- Cloud Functions: 125,000 invocations/month
- Firestore: 1 GB storage
- Firestore: 50,000 reads/day

**For a 10-participant, 10-day study:**
- Function calls: ~200 total
- Firestore storage: <1 MB
- Cost: **$0** (well within free tier)

## ✅ Migration Checklist

If you're switching from the old system:

### Backend Setup
- [ ] Create Firebase project
- [ ] Enable Firestore and Functions
- [ ] Create Google Service Account
- [ ] Download service account key (KEEP SECURE!)
- [ ] Enable Google Sheets API
- [ ] Share sheet with service account email
- [ ] Deploy Firebase Functions
- [ ] Test with curl commands

### Android App
- [ ] Replace `GoogleSheetsSync.kt` with `FirebaseSync.kt`
- [ ] Update `AppPreferences.kt` (add token storage)
- [ ] Update `MainActivity.kt` (Firebase registration)
- [ ] Update `MidnightScheduler.kt`
- [ ] Update `DashboardActivity.kt`
- [ ] Update `build.gradle` (remove Google dependencies)
- [ ] Update Firebase Function URL in code
- [ ] Test on device

### Participant Migration
- [ ] If study already started, ask participants to reinstall
- [ ] Old tokens won't work (they're Google OAuth, not Firebase)
- [ ] Participants re-register (generates new Firebase token)
- [ ] Historical data preserved in sheet

## 🎓 Learning Resources

**Firebase:**
- https://firebase.google.com/docs/functions/get-started
- https://firebase.google.com/docs/firestore

**Google Sheets API:**
- https://developers.google.com/sheets/api/quickstart/nodejs

**Service Accounts:**
- https://cloud.google.com/iam/docs/service-accounts

## 🙏 Credits

This architecture change addresses the security concerns raised in the code review. Special thanks to:
- The principle of least privilege
- Defense in depth security
- Proper separation of concerns

---

## Summary

**Old system:** Privacy violation, security risk, poor UX  
**New system:** Secure, private, seamless, scalable  

**The migration is worth it.** Your participants' data will be protected, your research will be more professional, and you'll sleep better at night knowing the architecture is sound.

**Deploy with confidence!** 🚀

---

**Version:** 2.0 - Firebase Architecture  
**Migration Date:** February 2026
