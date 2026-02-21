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
            active: true,
        });

        functions.logger.info(`Registered participant: ${userName} (${anonymousId})`);

        res.status(200).json({
            success: true,
            participantToken: token,
            message: "Participant registered successfully",
        });
    } catch (error) {
        functions.logger.error("Registration error:", error);
        res.status(500).json({error: "Internal server error"});
    }
});

// 3. Secure Sync to Google Sheets
exports.syncUsageData = functions.https.onRequest(async (req, res) => {
    try {
        const {participantToken, data} = req.body;

        if (!participantToken || !data || !Array.isArray(data)) {
            return res.status(400).json({error: "Missing token or data array"});
        }

        const participantDoc = await db.collection("participants").doc(participantToken).get();
        if (!participantDoc.exists || participantDoc.data().active === false) {
            return res.status(403).json({error: "Unauthorized. Invalid token."});
        }

        const auth = new google.auth.GoogleAuth({
            scopes: ["https://www.googleapis.com/auth/spreadsheets"],
        });
        const sheets = google.sheets({version: "v4", auth});

        const rows = data.map((item) => [
            item.date,
            item.userName,
            item.anonymousId,
            (item.studyDay !== undefined ? item.studyDay : "0").toString(),
            (item.totalScreenTimeMin !== undefined ? item.totalScreenTimeMin : "0").toString(),
            item.topApps || "",
            (item.totalUnlocks !== undefined ? item.totalUnlocks : "0").toString(),
            item.topUnlockApps || "",
            (item.totalNotifications !== undefined ? item.totalNotifications : "0").toString(),
            item.topNotificationApps || "",
        ]);

        if (rows.length === 0) {
            return res.status(200).json({success: true, rowsAdded: 0});
        }

        const response = await sheets.spreadsheets.values.append({
            spreadsheetId: SPREADSHEET_ID,
            range: "Sheet1!A:J",
            valueInputOption: "RAW",
            requestBody: {
                values: rows,
            },
        });

        const updatedRows = (response.data && response.data.updates && response.data.updates.updatedRows) || rows.length;

        await db.collection("sync_logs").add({
            anonymousId: participantDoc.data().anonymousId,
            rowCount: rows.length,
            timestamp: admin.firestore.FieldValue.serverTimestamp(),
            status: "success",
        });

        res.status(200).json({
            success: true,
            rowsAdded: updatedRows,
            message: "Data synced securely!",
        });
    } catch (error) {
        functions.logger.error("Sync error:", error);
        res.status(500).json({success: false, error: "Failed to sync data"});
    }
});
