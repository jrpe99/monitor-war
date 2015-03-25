function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function writeToScreen(message) {
    $("#output").val(message);
}

function initWebSocket() {
//    var uri = "ws://" + (document.location.hostname === "" ? "localhost" : document.location.hostname) + ":" +
//                    (document.location.port === "" ? "8080" : document.location.port);

    var uri = "ws://localhost:8080";
    var graph;
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
                try {
                    $("#pie-chart-success-holder").html("").html('<canvas id="pie-chart-success-area" width="400" height="400"></canvas>');
                    var ctx = $("#pie-chart-success-area").get(0).getContext("2d");
                    graph = new Chart(ctx).PolarArea(httpSuccess,{
                        animation: false,
                        animateRotate : false
                    });
                } catch(err) {
                    
                }
            }

            var httpFailure = json.failed_requests;
            if (typeof httpFailure !== 'undefined') {
                try {
                    $("#pie-chart-failed-holder").html("").html('<canvas id="pie-chart-failed-area" width="400" height="400"></canvas>');
                    var ctx = $("#pie-chart-failed-area").get(0).getContext("2d");
                    graph = new Chart(ctx).PolarArea(httpFailure,{
                        animation: false,
                        animateRotate : false
                    });
                } catch(err) {
                    
                }
            }
        }

        if(chart === 'radar') {
            var dataset = json.dataset;
            if (dataset === 'successful_requests') {
                try {
                    var httpSuccess = json.data;
                    $("#radar-chart-success-holder").html("").html('<canvas id="radar-chart-success-area" width="600" height="600"></canvas>');
                    var ctx = $("#radar-chart-success-area").get(0).getContext("2d");
                    graph = new Chart(ctx).Radar(httpSuccess,{
                        animation: false
                    });
                } catch(err) {
                    
                }
            }

            if (dataset === 'failed_requests') {
                try {
                    var httpFailure = json.data;
                    $("#radar-chart-failed-holder").html("").html('<canvas id="radar-chart-failed-area" width="600" height="600"></canvas>');
                    var ctx = $("#radar-chart-failed-area").get(0).getContext("2d");
                    graph = new Chart(ctx).Radar(httpFailure,{
                        animation: false
                    });
                } catch(err) {
                    
                }
            }
        }
        if(chart === 'line') {
            try {
                var dataset = json.dataset;
                if (dataset === 'requests_per_minute') {
                    var httpSuccess = json.data;
                    $("#line-chart-holder").html("").html('<canvas id="line-chart-area" width="800" height="500"></canvas>');
                    var ctx = $("#line-chart-area").get(0).getContext("2d");

                    graph = new Chart(ctx).Line(httpSuccess,{
                        animation: false
                    });
                }
            } catch(err) {

            }
        }

    };
    websocket.onerror = function(evt) {
        onError(evt);
    };
}