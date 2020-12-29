var stompClient = null;


function connect() {
    var socket = new SockJS('/websocket/profile');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/stomp/profile', function (notification) {
            console.log(notification);
            let notificationObj = JSON.parse(notification.body);
            if (notificationObj.type === 'del') {
                $("#movement_" + notificationObj.payload.id).remove();
            } else if (notificationObj.type === 'add') {
                let dom =
                    '<tr id="movemet_' + notificationObj.payload.id + '">' +
                    '<th scope="col">' + notificationObj.payload.cityFrom.name + '</th>' +
                    '<th scope="col">' + notificationObj.payload.cityTo.name + '</th>' +
                    '<th scope="col">' + notificationObj.payload.departureDate + '</th>' +
                    '<th scope="col">' + notificationObj.payload.arrivalDate + '</th>' +
                    '<th scope="col">' + notificationObj.payload.transport.transportType + '</th>' +
                    '<th scope="col">' + notificationObj.payload.price + '</th>' +
                    '<th scope="col">' +
                    '<button onclick="' +
                    '$.post({url: \'/booking/delete/' + notificationObj.payload.id + '\',function(){}});' +
                    'class="float-right btn btn-outline-primary mt-1">снять бронь </button></th>' +
                    '<th scope="col">' +
                    '<a class="float-right btn btn-outline-primary mt-1" href="/movement/' + notificationObj.payload.id + '">Подробнее</a>' +
                    '</th>' +
                    '</tr>';
                $("#table_1").append(dom);
            } else if (notificationObj.type === 'mov') {
                $('#table_2').append($('#movement_' + notificationObj.payload.id));
                $('#movement_' + notificationObj.payload.id).children("th:eq(7)").remove();
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


