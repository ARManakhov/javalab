var webSocket;
function connect() {
    // webSocket = new WebSocket('ws://localhost:8080/chat');
    webSocket = new SockJS("http://localhost:8080/chat");
    document.cookie = 'X-Authorization=' + '12345' + ';path=/';

    webSocket.onmessage = function receiveMessage(response) {
        let data = response['data'];
        let json = JSON.parse(data);
        $('#messages').first().append("<div>" + json['authorName'] + ': ' + json['text'] + "</div>")
    }
}

function sendMessage(text) {
    let message = {
        "text": text
    };

    webSocket.send(JSON.stringify(message));
}