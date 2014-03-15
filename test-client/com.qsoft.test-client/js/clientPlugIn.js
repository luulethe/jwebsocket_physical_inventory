jws.ToDoListClientPlugIn = {
    NS: "com.qsoft.demojwebsocket.addNote",

    //Method is called when a token has to be progressed
    processToken: function (aToken)
    {
        document.getElementById("responseContent").value = JSON.stringify(aToken)
        var token =  {
            ns:"com.discorp.physicalInventory.handleResponse",
            type:"ackResponse",
            utidResponse:aToken["id"]
        }
        this.submitToServer(token)

    },
    submitToServer: function (lToken,aOptions)
    {
        if (this.isConnected())
        {
            this.sendToken(lToken,aOptions);//send it
        }
        else
        {
            log("disconnected");
        }
    }
};


jws.oop.addPlugIn(jws.jWebSocketTokenClient, jws.ToDoListClientPlugIn);
