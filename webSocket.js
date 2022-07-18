const express = require("express");
var cors = require("cors");
const port = process.env.PORT || 8080;
const app = express();
const url = require("url");
app.use(cors());
let allow = false;
// to get the ADC automatically
var admin = require("firebase-admin");
var serviceAccount = require("./securechat-354721-firebase-adminsdk-hmrnx-378aea9257.json");
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

// define the first route
app.get("/", function (req, res) {});

app.post("/callback", function async(req, res) {
  console.log("pipeline complete");
  allow = true;
  // This registration token comes from the client FCM SDKs.
  const registrationToken = url.parse(req.url, true).query.token;
  console.log("registration token: " + registrationToken);
  const message = {
    data: {
      message: "Pipeline Completed",
    },
    token: registrationToken,
  };

  // Send a message to the device corresponding to the provided
  // registration token.
  admin
    .messaging()
    .send(message)
    .then((response) => {
      // Response is a message ID string.
      console.log("Successfully sent message:", response);
    })
    .catch((error) => {
      console.log("Error sending message:", error);
    });
});
// start the server listening for requests
app.listen(port, () => console.log(`Server is running on port: ${port}`));
