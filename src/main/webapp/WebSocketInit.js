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
        var json = JSON.parse(evt.data);
        var chart = json.chart;
        if(chart === 'pie') {
            var httpSuccess = json.successful_requests;
            if (typeof httpSuccess !== 'undefined') {
                $("#pie-success-monitor-holder").html("").html('<canvas id="pie-success-monitor-area" width="400" height="400"></canvas>');
                var ctx = $("#pie-success-monitor-area").get(0).getContext("2d");
                graph = new Chart(ctx).PolarArea(httpSuccess,{
                    animation: false,
                    animateRotate : false
                });
            }

            var httpFailure = json.failed_requests;
            if (typeof httpFailure !== 'undefined') {
                $("#pie-failed-monitor-holder").html("").html('<canvas id="pie-failed-monitor-area" width="400" height="400"></canvas>');
                var ctx = $("#pie-failed-monitor-area").get(0).getContext("2d");
                graph = new Chart(ctx).PolarArea(httpFailure,{
                    animation: false,
                    animateRotate : false
                });
            }
        }
        if(chart === 'radar') {
            var dataset = json.dataset;
            if (dataset === 'successful_requests') {
                var httpSuccess = json.data;
                $("#radar-success-monitor-holder").html("").html('<canvas id="radar-success-monitor-area" width="400" height="400"></canvas>');
                var ctx = $("#radar-success-monitor-area").get(0).getContext("2d");
                graph = new Chart(ctx).Radar(httpSuccess,{
                    animation: false
                });
            }

            if (dataset === 'failed_requests') {
                var httpFailure = json.data;
                $("#radar-failed-monitor-holder").html("").html('<canvas id="radar-failed-monitor-area" width="400" height="400"></canvas>');
                var ctx = $("#radar-failed-monitor-area").get(0).getContext("2d");
                graph = new Chart(ctx).Radar(httpFailure,{
                    animation: false
                });
            }
        }
        
    };
    websocket.onerror = function(evt) {
        onError(evt);
    };
}