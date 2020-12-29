var stompClient = null;


function connect() {
    var socket = new SockJS('/websocket/root');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/stomp/root', function (notification) {
            console.log(notification);
            let notificationObj = JSON.parse(notification.body);
            if (notificationObj.type === 'del') {
                $("#movemet_" + notificationObj.payload.id).remove();
            } else if (notificationObj.type === 'add') {
                let dom =
                    '<tr id="movemet_' + notificationObj.payload.id+ '">' +
                    '<th scope="col">' + notificationObj.payload.cityFrom.name + '</th>' +
                    '<th scope="col">' + notificationObj.payload.cityTo.name + '</th>' +
                    '<th scope="col">' + notificationObj.payload.departureDate + '</th>' +
                    '<th scope="col">' + notificationObj.payload.arrivalDate + '</th>' +
                    '<th scope="col">' + notificationObj.payload.transport.transportType + '</th>' +
                    '<th scope="col">' + notificationObj.payload.price + '</th>' +
                    '<th scope="col">' +
                    '<a class="float-right btn btn-outline-primary mt-1" href="/movement/' + notificationObj.payload.id+ '">Подробнее</a>' +
                    '</th>' +
                    '</tr>';
                $("#table").append(dom);
            }
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}


