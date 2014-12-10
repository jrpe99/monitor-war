(function(models) {
    models.ActionModel = Backbone.Model.extend({
        initialize: function() {
            this.on("change:graph", function() {
                var graph = this.get("graph");

            });
        },
        updateIntervalAction: function() {
            var gameId = $("#gameID").val();
            if (gameId === '') {
                alert("Add a game ID");
            } else {
                this.set({"type":"joinreq", "gameId":gameId});
                websocket.send(JSON.stringify(this));
            }
            return this;
        }
    });
})(moduleCache.module("models"));
