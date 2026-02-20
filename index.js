const functions = require('firebase-functions');
const admin = require('firebase-admin');
const { google } = require('googleapis');

admin.initializeApp();

// Load configuration from Firebase environment
const SHEET_ID = functions.config().sheets?.id || process.env.SHEET_ID;
const SERVICE_ACCOUNT_EMAIL = functions.config().service?.email || process.env.SERVICE_ACCOUNT_EMAIL;
const SERVICE_ACCOUNT_PRIVATE_KEY = (functions.config().service?.key || process.env.SERVICE_ACCOUNT_PRIVATE_KEY || '').replace(/\\n/g, '\n');

// Valid participant tokens (in production, store these in Firestore)
// These are generated when you register participants
const VALID_TOKENS = new Set();

/**
 * Initialize Google Sheets API client with Service Account
 */
function getSheetsClient() {
  const auth = new google.auth.JWT({
    email: SERVICE_ACCOUNT_EMAIL,
    key: SERVICE_ACCOUNT_PRIVATE_KEY,
    scopes: ['https://www.googleapis.com/auth/spreadsheets'],
  });

  return google.sheets({ version: 'v4', auth });
}

/**
 * Main endpoint: Sync usage data to Google Sheets
 * 
 * POST /syncUsageData
 * Body: {
 *   participantToken: "string",
 *   data: [{
 *     date: "2024-02-15",
 *     userName: "John Doe",
 *     anonymousId: "a1b2c3d4",
 *     studyDay: 1,
 *     totalScreenTimeMin: 245,
 *     topApps: "Instagram: 120min; Chrome: 65min",
 *     totalUnlocks: 87,
 *     topUnlockApps: "Instagram: 42x; WhatsApp: 25x",
 *     totalNotifications: 156,
 *     topNotificationApps: "WhatsApp: 45; Gmail: 32"
 *   }]
 * }
 */
exports.syncUsageData = functions.https.onRequest(async (req, res) => {
  // Enable CORS for Android app
  res.set('Access-Control-Allow-Origin', '*');
  res.set('Access-Control-Allow-Methods', 'POST');
  res.set('Access-Control-Allow-Headers', 'Content-Type, Authorization');

  if (req.method === 'OPTIONS') {
    res.status(204).send('');
    return;
  }

  if (req.method !== 'POST') {
    res.status(405).json({ error: 'Method not allowed' });
    return;
  }

  try {
    const { participantToken, data } = req.body;

    // Validate request
    if (!participantToken) {
      res.status(401).json({ error: 'Missing participant token' });
      return;
    }

    if (!data || !Array.isArray(data) || data.length === 0) {
      res.status(400).json({ error: 'Invalid or empty data array' });
      return;
    }

    // Verify participant token (in production, check Firestore)
    const isValidToken = await verifyParticipantToken(participantToken);
    if (!isValidToken) {
      console.error(`Invalid token attempt: ${participantToken}`);
      res.status(403).json({ error: 'Invalid participant token' });
      return;
    }

    // Validate sheet configuration
    if (!SHEET_ID || !SERVICE_ACCOUNT_EMAIL || !SERVICE_ACCOUNT_PRIVATE_KEY) {
      console.error('Missing Google Sheets configuration');
      res.status(500).json({ error: 'Server configuration error' });
      return;
    }

    // Prepare rows for Google Sheets
    const rows = data.map(item => [
      item.date,
      item.userName,
      item.anonymousId,
      item.studyDay.toString(),
      item.totalScreenTimeMin.toString(),
      item.topApps,
      item.totalUnlocks.toString(),
      item.topUnlockApps,
      item.totalNotifications.toString(),
      item.topNotificationApps
    ]);

    // Append to Google Sheets
    const sheets = getSheetsClient();
    const response = await sheets.spreadsheets.values.append({
      spreadsheetId: SHEET_ID,
      range: 'Sheet1!A:J',
      valueInputOption: 'RAW',
      requestBody: {
        values: rows
      }
    });

    // Log successful sync
    console.log(`Synced ${rows.length} rows for participant ${participantToken.substring(0, 8)}...`);
    
    // Store sync record in Firestore for audit trail
    await admin.firestore().collection('sync_logs').add({
      participantToken: participantToken.substring(0, 8) + '...',  // Partial for privacy
      rowCount: rows.length,
      timestamp: admin.firestore.FieldValue.serverTimestamp(),
      success: true
    });

    res.status(200).json({
      success: true,
      rowsAdded: response.data.updates.updatedRows,
      message: 'Data synced successfully'
    });

  } catch (error) {
    console.error('Sync error:', error);
    
    // Log failed sync
    try {
      await admin.firestore().collection('sync_logs').add({
        error: error.message,
        timestamp: admin.firestore.FieldValue.serverTimestamp(),
        success: false
      });
    } catch (logError) {
      console.error('Failed to log error:', logError);
    }

    res.status(500).json({
      success: false,
      error: 'Failed to sync data',
      details: error.message
    });
  }
});

/**
 * Register a new participant and generate a secure token
 * 
 * POST /registerParticipant
 * Body: {
 *   userName: "John Doe",
 *   anonymousId: "a1b2c3d4"
 * }
 */
exports.registerParticipant = functions.https.onRequest(async (req, res) => {
  res.set('Access-Control-Allow-Origin', '*');
  res.set('Access-Control-Allow-Methods', 'POST');
  res.set('Access-Control-Allow-Headers', 'Content-Type');

  if (req.method === 'OPTIONS') {
    res.status(204).send('');
    return;
  }

  if (req.method !== 'POST') {
    res.status(405).json({ error: 'Method not allowed' });
    return;
  }

  try {
    const { userName, anonymousId } = req.body;

    if (!userName || !anonymousId) {
      res.status(400).json({ error: 'Missing userName or anonymousId' });
      return;
    }

    // Generate a secure token
    const token = require('crypto').randomBytes(32).toString('hex');

    // Store participant in Firestore
    await admin.firestore().collection('participants').doc(token).set({
      userName,
      anonymousId,
      registeredAt: admin.firestore.FieldValue.serverTimestamp(),
      active: true
    });

    console.log(`Registered participant: ${userName} (${anonymousId})`);

    res.status(200).json({
      success: true,
      participantToken: token,
      message: 'Participant registered successfully'
    });

  } catch (error) {
    console.error('Registration error:', error);
    res.status(500).json({
      success: false,
      error: 'Failed to register participant'
    });
  }
});

/**
 * Verify participant token
 * In production, this checks Firestore
 */
async function verifyParticipantToken(token) {
  try {
    const doc = await admin.firestore().collection('participants').doc(token).get();
    return doc.exists && doc.data().active === true;
  } catch (error) {
    console.error('Token verification error:', error);
    return false;
  }
}

/**
 * Health check endpoint
 */
exports.healthCheck = functions.https.onRequest((req, res) => {
  res.status(200).json({
    status: 'healthy',
    timestamp: new Date().toISOString(),
    version: '1.0.0'
  });
});

/**
 * Get sync statistics (for researchers)
 * Requires admin authentication
 */
exports.getStats = functions.https.onRequest(async (req, res) => {
  res.set('Access-Control-Allow-Origin', '*');

  try {
    // In production, add proper admin authentication here
    
    const participants = await admin.firestore().collection('participants').get();
    const syncLogs = await admin.firestore().collection('sync_logs')
      .orderBy('timestamp', 'desc')
      .limit(100)
      .get();

    const stats = {
      totalParticipants: participants.size,
      activeParticipants: participants.docs.filter(doc => doc.data().active).length,
      recentSyncs: syncLogs.docs.map(doc => ({
        timestamp: doc.data().timestamp?.toDate(),
        success: doc.data().success,
        rowCount: doc.data().rowCount
      }))
    };

    res.status(200).json(stats);

  } catch (error) {
    console.error('Stats error:', error);
    res.status(500).json({ error: 'Failed to fetch stats' });
  }
});
