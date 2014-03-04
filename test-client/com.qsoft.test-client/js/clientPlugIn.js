jws.ToDoListClientPlugIn = {
    NS: "com.qsoft.demojwebsocket.addNote",

    //Method is called when a token has to be progressed
    processToken: function (aToken)
    {
        if (aToken.ns == jws.ToDoListClientPlugIn.NS)
        {
            document.getElementById("responseContent").value = JSON.stringify(aToken)
        }

    },
    submitToServer: function (lToken,aOptions)
    {
        if (this.isConnected())
        {
            this.sendToken(lToken,aOptions);//send it
        }
        else
        {
            alert("disconnected");
        }
    }
};


jws.oop.addPlugIn(jws.jWebSocketTokenClient, jws.ToDoListClientPlugIn);
