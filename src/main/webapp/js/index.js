jQuery(function($) {

    var sock = new WebSocket("ws://" + location.host + "/doorbell/chime");

    sock.onopen = function() {
        console.log('open');
    };

    sock.onclose = function() {
        console.log('close');
    };

    sock.onmessage = function(e) {
        var content = JSON.parse(e.data);
        $('#chat-content').val(function(i, text) {
            return text + 'User ' + content.username + ': ' + content.message + '\n';
        });
    };

    $('#btnSend').click(function() {
        console.log('about to send');
        var message = $('#message').val();
        var username = $('#username').val();
        sock.send(JSON.stringify({ message: message, username: username }));
    });

});
