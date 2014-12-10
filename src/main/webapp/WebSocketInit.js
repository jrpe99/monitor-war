function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function writeToScreen(message) {
    $("#output").val(message);
}

function initWebSocket() {
    var uri = "ws://" + (document.location.hostname === "" ? "localhost" : document.location.hostname) + ":" +
                    (document.location.port === "" ? "8080" : document.location.port);

    var graph;
    var action = new models.ActionModel();
    var wsUri = uri + "/Cassandra/monitor";
    websocket = new WebSocket(wsUri);

    websocket.onopen = function(evt) {
    };
    websocket.onmessage = function(evt) {
        var data = JSON.parse(evt.data).graph;
        $("#monitor-holder").html("").html('<canvas id="monitor-area" width="700" height="700"></canvas>');
        var ctx = $("#monitor-area").get(0).getContext("2d");
        graph = new Chart(ctx).PolarArea(data,{
            animateRotate : false
        });
    };
    websocket.onerror = function(evt) {
        onError(evt);
    };
}