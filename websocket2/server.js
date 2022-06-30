// Importing the required modules
const WebSocketServer = require("ws");

// Creating a new websocket server
const wss = new WebSocketServer.Server({ port: process.env.PORT || 3000 });

// app.js
const express = require("express");

const app = express();

module.exports = app;
const app = require("./app");
const port = 4000 || process.env.PORT;

app.listen(port, () => console.log(`The server is listening on port ${port}`));
//parse the client message for their UID
const getID = (data) => {
  let id = data.split(":")[1];
  return id;
};

//parse the client message for their UID
const getMessage = (data) => {
  let message = data.split(":")[0];
  message = message.split(" ")[0];
  return message;
};

// Creating connection using websocket
wss.on("connection", (ws) => {
  console.log("new client connected");

  // handling when recieving a new message
  ws.onmessage = ({ data }) => {
    ws.id = getID(data);
    wss.clients.forEach(function each(client) {
      client.send(data);
    });
    console.log(`Client has sent us:${data}`);
  };

  // handling when a client closes the connection
  ws.onclose = function () {
    console.log(`Client ${ws.id} has disconnected!`);
  };

  // handling what to do when clients disconnects from server
  ws.on("close", () => {
    console.log("the client has connected");
  });

  // handling client connection error
  ws.onerror = function () {
    console.log("Some Error occurred");
  };
});
console.log(
  `The WebSocket server is running on port ${WebSocketServer.Server.port}`
);
