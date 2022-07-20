# Secure-Chat
A secure way to chat using Journey Elevate for identifying users before they can join the chat room.
## Technologies
### Websocket Server
A connection will be established with a WebSocket server to provide a communication channel between the server and the different clients.
In case the WebSocket connection is not possible, it will fall back to HTTP long-polling. And if the connection is lost, the client will automatically try to reconnect. This is for the chatting functionality of this project.
### Node js/Express Server
Using Express.js a back end web application framework for Node.js. I've constructed a web API to act as a server for communicating with the client applications. This is for authentication purposes of this project.
### Android Client App
A simple android app to provide an interface for chatting with securely identified people.

# Setting up this project
This project is currently still in production so the set up process is subject to change.
## Deployment of Server and Websocket Server
The two servers will need to be deployed anyway you'd like as long as they have a valid https URL associated with them. I personally used Heroku deployment of Node js apps for this project while in production.
## Client applications
You'll need to set up the callback url of the Journey Elevate end point being pinged into ChannelListAdapter line 55. Here you'll put the url to the server youve deployed.
In the MessageActivity class update the serverUrl to the URL of the websocket you deployed.
