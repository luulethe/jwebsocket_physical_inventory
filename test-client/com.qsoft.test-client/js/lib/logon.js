function logon()
{
    var wsURL = "ws://localhost:8787/jwebsocket/jwebsocket";// get the default server url
//    var wsURL = "ws://localhost:8787/jwebsocket/jwebsocket";// get the default server url
//    var wsURL = jws.getDefaultServerURL();// get the default server url
    console.log("Connecting to " + websocketID + " at " + wsURL + "...");

    //get the guest username and password
    wsUsername = jws.GUEST_USER_LOGINNAME;
    wsPassword = jws.GUEST_USER_PASSWORD;

    // try to establish the connection to jWebSocket server
    websocketClient.logon(wsURL, wsUsername, wsPassword, {
        OnOpen: function (aEvent)
        {
            console.log("Connection to " + websocketID + " established.");
        },

        // OnMessage callback
        OnMessage: function (aEvent, aToken)
        {
            console.log(( aToken ? aToken.type : "-" ) + ": " + aEvent.data);

            if (websocketClient.isLoggedIn())
            {
                console.log("User is authenticated");
            }
            else
            {
                console.log("User is connected");
            }
            console.log(websocketClient.getId() + "&nbsp;" + ( jws.browserSupportsNativeWebSockets ? "(native)" : "(flashbridge)" ));
            if (aToken)
            {
                // is it a response from a previous request of this client?
                if (aToken.type == "response")
                {
                    if (aToken.reqType == "login")
                    {// figure out of which request
                        if (aToken.code == 0)
                        {
                            console.log("Welcome '" + aToken.username + "'");
                        }
                        else
                        {
                            console.log("Error logging in '" + eUsername.value + "': " + aToken.msg);
                        }
                    }
                    // is it an event w/o a previous request ?
                }
                else if (aToken.type == "goodBye")
                {
                    console.log("good bye (reason: " + aToken.reason + ")!");
                }
            }
        },
        // OnClose callback
        OnClose: function (aEvent)
        {
            console.log("Disconnected from " + websocketID + ".");
        }

    });
}