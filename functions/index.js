const functions = require("firebase-functions");
const {
  initializeApp,
  applicationDefault,
  cert,
} = require("firebase-admin/app");
const {
  getFirestore,
  Timestamp,
  FieldValue,
} = require("firebase-admin/firestore");
initializeApp();
const db = getFirestore();

// Create and Deploy Your First Cloud Functions
// https://firebase.google.com/docs/functions/write-firebase-functions

exports.helloWorld = functions.https.onRequest((request, response) => {
  functions.logger.info("Hello logs!", { structuredData: true });
  response.send("Hello from Firebase!");

  const databaseFunc = async () => {
    const data = {
      id: "fewvw542craca",
      status: "approved ",
      timestamp: Timestamp,
    };
    const docRef = db.collection("users").doc("RAND1").set(data);
    // Update the timestamp field with the value from the server
    const res = await docRef.update({
      timestamp: FieldValue.serverTimestamp(),
    });
    console.log(res);
  };
  databaseFunc();
});
