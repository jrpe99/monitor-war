(function(views) {

})(moduleCache.module("views"));


// Action views (buttons)
(function (views) {
    views.JoinActionView = Backbone.View.extend({
        el: $("#joinAction"),
        initialize: function(){
            this.joinView();
        },
        events: {
            "click #joinButton": "joinAction"
        },
        joinAction: function() {
            new models.ActionModel().joinAction();
            return this;
        },
        joinView: function() {
            $(this.el).html("<button id='joinButton' value='join'>Join</button>");            
        }
    });
})(moduleCache.module("views"));