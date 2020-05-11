let first = true;

function sendMessage(text,first) {
    let body = {
        text: text
    };

    $.ajax({
        url: "/messages",
        method: "POST",
        data: JSON.stringify(body),
        contentType: "application/json",
        dataType: "json",
        complete: function () {
            if (first) {
                receiveMessage();
            }
        }
    });
}

// LONG POLLING
function receiveMessage() {
    $.ajax({
        url: "/messages" ,
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            $('#messages').first().after('<li>' + response[0].authorName + ': ' + response[0].text + '</li>');
            receiveMessage();
        }
    })
}