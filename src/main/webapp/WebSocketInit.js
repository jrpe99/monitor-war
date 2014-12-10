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
        var httpSuccess = JSON.parse(evt.data).successful_requests;
        if (typeof httpSuccess !== 'undefined') {
            $("#success-monitor-holder").html("").html('<canvas id="success-monitor-area" width="400" height="400"></canvas>');
            var ctx = $("#success-monitor-area").get(0).getContext("2d");
            graph = new Chart(ctx).PolarArea(httpSuccess,{
                animateRotate : false
            });
        }

        var httpFailure = JSON.parse(evt.data).failed_requests;
        if (typeof httpFailure !== 'undefined') {
            $("#failed-monitor-holder").html("").html('<canvas id="failed-monitor-area" width="400" height="400"></canvas>');
            var ctx = $("#failed-monitor-area").get(0).getContext("2d");
            graph = new Chart(ctx).PolarArea(httpFailure,{
                animateRotate : false
            });
        }
    };
    websocket.onerror = function(evt) {
        onError(evt);
    };
}