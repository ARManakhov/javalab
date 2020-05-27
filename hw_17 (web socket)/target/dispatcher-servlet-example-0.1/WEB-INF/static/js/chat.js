var webSocket;
function connect(id) {
    // webSocket = new WebSocket('ws://localhost:8080/chat');
    webSocket = new SockJS("http://localhost:8080/chat_ws/" + id+"/");


    webSocket.onmessage = function receiveMessage(response) {
        let data = response['data'];
        let json = JSON.parse(data);
        $('#messages').first().append("<div>" + json['authorName'] + ': ' + json['text'] + "</div>");
    };
    webSocket.onopen = function onopen(){
    sendMessage("вошел в чат");
        };
}

function sendMessage(text) {
    let message = {
        "text": text
    };

    webSocket.send(JSON.stringify(message));
}