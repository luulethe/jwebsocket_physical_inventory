var websocketID = "meinTest",
    websocketClient = null
function log(message)
{
    console.log(message);
}
function initPage()
{
    if (jws.browserSupportsWebSockets())
    {
        console.log("Websockets are supported");
        websocketClient = new jws.jWebSocketJSONClient();
        logon();
    }
    else
    {
        console.log("Websockets are NOT supported");
    }
}

function submit()
{
    var submitContent = document.getElementById("submitContent").value;
    if (submitContent.trim().length >0) {
        var lToken =  JSON.parse(submitContent.replace(/\n/, ''))
        if (!websocketClient.isConnected())
        {
            websocketClient = new jws.jWebSocketJSONClient();
            logon();
        }
        websocketClient.submitToServer(lToken);
    }
}
