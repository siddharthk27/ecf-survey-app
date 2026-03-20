/* eslint-disable max-len */
const functions = require("firebase-functions");
const admin = require("firebase-admin");
const crypto = require("crypto");

admin.initializeApp();
const db = admin.firestore();

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

// 3. Secure Sync to Firestore
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

        if (data.length === 0) {
            return res.status(200).json({success: true, rowsAdded: 0});
        }

        const batch = db.batch();
        data.forEach((item) => {
            const docRef = db.collection("usage_logs").doc();
            batch.set(docRef, {
                ...item,
                participantToken: participantToken,
                anonymousId: participantDoc.data().anonymousId,
                userName: participantDoc.data().userName,
                uploadedAt: admin.firestore.FieldValue.serverTimestamp(),
            });
        });
        await batch.commit();

        await db.collection("sync_logs").add({
            anonymousId: participantDoc.data().anonymousId,
            rowCount: data.length,
            timestamp: admin.firestore.FieldValue.serverTimestamp(),
            status: "success",
        });

        res.status(200).json({
            success: true,
            rowsAdded: data.length,
            message: "Data synced securely to Firestore!",
        });
    } catch (error) {
        functions.logger.error("Sync error:", error);
        res.status(500).json({success: false, error: "Failed to sync data"});
    }
});

// 4. Export Data to CSV
exports.exportDataToCsv = functions.https.onRequest(async (req, res) => {
    try {
        const logsSnapshot = await db.collection("usage_logs").orderBy("uploadedAt", "desc").get();

        const headersList = [
            "date",
            "userName",
            "anonymousId",
            "studyDay",
            "totalScreenTimeMin",
            "topApps",
            "totalUnlocks",
            "topUnlockApps",
            "totalNotifications",
            "topNotificationApps",
            "uploadedAt",
        ];

        let csv = headersList.join(",") + "\n";

        if (!logsSnapshot.empty) {
            logsSnapshot.forEach((doc) => {
                const data = doc.data();
                const row = headersList.map((field) => {
                    let value = data[field];
                    if (field === "uploadedAt" && value && value.toDate) {
                        value = value.toDate().toISOString();
                    }
                    if (value === undefined || value === null) {
                        return "";
                    }
                    const strValue = String(value);
                    if (strValue.includes(",") || strValue.includes("\\n") || strValue.includes("\"")) {
                        return `"${strValue.replace(/"/g, "\"\"")}"`;
                    }
                    return strValue;
                });
                csv += row.join(",") + "\n";
            });
        }

        res.setHeader("Content-Type", "text/csv");
        res.setHeader("Content-Disposition", "attachment; filename=usage_logs.csv");
        res.status(200).send(csv);
    } catch (error) {
        functions.logger.error("Export error:", error);
        res.status(500).send("Error generating CSV");
    }
});
