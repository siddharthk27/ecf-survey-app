/* eslint-disable max-len */
const functions = require("firebase-functions");
const admin = require("firebase-admin");
const {google} = require("googleapis");
const crypto = require("crypto");

admin.initializeApp();
const db = admin.firestore();

// ⚠️ PASTE YOUR SPREADSHEET ID HERE
const SPREADSHEET_ID = "1TOAuz8M7kZJrfOOD7p2YFUaGcv6v70VazxgLpXhLnFY";

// 1. Health Check
exports.healthCheck = functions.https.onRequest((req, res) => {
    res.status(200).send("Usage Tracker Secure Backend is running!");
});

// 2. Register Participant
exports.registerParticipant = functions.https.onRequest(async (req, res) => {
    try {
        const {userName, anonymousId} = req.body;

        if (!userName || !anonymousId) {
            return res.status(400).json({error: "Missing name or ID"});
        }

        const token = crypto.randomBytes(32).toString("hex");

        await db.collection("participants").doc(token).set({
            userName: userName,
            anonymousId: anonymousId,
            registeredAt: admin.firestore.FieldValue.serverTimestamp(),
        });

        res.status(200).json({token: token});
    } catch (error) {
        functions.logger.error("Registration error:", error);
        res.status(500).json({error: "Internal server error"});
    }
});

// 3. Secure Sync to Google Sheets
exports.syncUsageData = functions.https.onRequest(async (req, res) => {
    try {
        const {token, rowData} = req.body;

        if (!token || !rowData) {
            return res.status(400).json({error: "Missing token or data"});
        }

        const participantDoc = await db.collection("participants").doc(token).get();
        if (!participantDoc.exists) {
            return res.status(403).json({error: "Unauthorized. Invalid token."});
        }

        const auth = new google.auth.GoogleAuth({
            scopes: ["https://www.googleapis.com/auth/spreadsheets"],
        });
        const sheets = google.sheets({version: "v4", auth});

        await sheets.spreadsheets.values.append({
            spreadsheetId: SPREADSHEET_ID,
            range: "Sheet1!A:J",
            valueInputOption: "RAW",
            requestBody: {
                values: [rowData],
            },
        });

        await db.collection("sync_logs").add({
            anonymousId: participantDoc.data().anonymousId,
                                             timestamp: admin.firestore.FieldValue.serverTimestamp(),
                                             status: "success",
        });

        res.status(200).json({
            success: true,
            message: "Data synced securely!",
        });
    } catch (error) {
        functions.logger.error("Sync error:", error);
        res.status(500).json({error: "Failed to sync data"});
    }
});
