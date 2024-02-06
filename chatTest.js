const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:7777/api/ws-dm'
});


function connect() {
    stompClient.activate();
}

var roomId;
stompClient.onConnect = (frame) => {

roomId = $("#roomId").val().trim();
console.log(roomId);
console.log('Connected: ' + frame);

setConnected(true);

console.log(roomId+'번 방에 들어갔습니다.');
stompClient.subscribe(`/topic/room/${roomId}`, (response) => {
    console.log("response", response);
    console.log(JSON.parse(response.body));
    addTextBox(response.body);
});

}

function sendChat() {
    console.log(`/app/sendDm/${roomId}`);
    stompClient.publish({
        destination: `/app/sendDm/${roomId}`,
        body: JSON.stringify(
            {
                content: $("#chat").val(),
                sender : $("#sender").val()
            })
    });
}




function addTextBox(message) {
    var newDiv = document.createElement("div");
    
    // div에 텍스트 추가 (예시로 "New Content"라는 텍스트 추가)
    newDiv.textContent = message;

    // div를 tbody에 추가
    document.getElementById("messageBox").appendChild(newDiv);}

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

// stompClient.onStompError = (frame) => {
//     console.error('Broker reported error: ' + frame.headers['message']);
//     console.error('Additional details: ' + frame.body);
// };

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}



function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}



function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendChat());
});