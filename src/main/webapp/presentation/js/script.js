$(document).ready(function () {
    connect();
    getUsers();
});

function User(name, gender){
    this.name = name;
    this.gender = gender;
}

let currentUser = new User($("#name").text(), $("#gender").val());

var wsocket;


function Message(sender,content, time) {
    this.sender = sender;
    this.content = content;
    this.time = time;
}

function connect() {
    wsocket = new WebSocket("ws://localhost:8082/chat/connect");
    wsocket.onopen = register;
    wsocket.onmessage = onMessage;
}

let registerSocket;
function register() {
    registerSocket = new WebSocket("ws://localhost:8082/chat/register");

    registerSocket.onopen = function () {
        registerSocket.send(JSON.stringify(currentUser));
    }

    registerSocket.onmessage = function (evt){
        let user = JSON.parse(evt.data);

        
        
        if(Array.isArray(user)){
            $("#users").html("");

            user.forEach(user => {
                drawOnlineUsers(user);
            })
        }else{
            drawOnlineUsers(user);
        }

    }


    registerSocket.onclose = function (){
        location.href = "/chat";

    }

}

let userSocket;
function getUsers() {
    userSocket = new WebSocket("ws://localhost:8082/chat/get-users");
    userSocket.onopen = function () {
        userSocket.send("");
    }

    userSocket.onmessage = function (evt) {
        let users = JSON.parse(evt.data);

       
        users.forEach(user => {
            drawOnlineUsers(user);
        });
       
        
    };


}

function drawOnlineUsers(user){
    $("#users").append(`
    <li class="clearfix active">
        <img src=${user.gender === 'male' ? 'https://bootdey.com/img/Content/avatar/avatar7.png' : 'https://bootdey.com/img/Content/avatar/avatar3.png'} alt="avatar">
        <div class="about">
            <div class="name">${user.name}</div>
            <div class="status"> <i class="fa fa-circle online"></i> online </div>
        </div>
     </li>
    `);
}

function onMessage(evt) {

    let obj = JSON.parse(evt.data);

    append(obj)
}


function append(obj, flag = false) {
    $("#chatList").append(`
    <li class="clearfix">
        <div class="message-data text-right" ${flag ? "style='text-align:right'" : ""}>
           
            <span class="message-data-time">${obj.time} AM, Today</span>
            <img src=${obj.sender.gender === 'male' ? 'https://bootdey.com/img/Content/avatar/avatar7.png' : 'https://bootdey.com/img/Content/avatar/avatar3.png'} alt="avatar">
        </div>
        <div class="message other-message ${flag ? "float-right" : ""}" >

        ${!flag ? `<div class="font-weight-bold mb-1" style="font-weight: bold">${obj.sender.name}</div>` : ''}

        ${obj.content}</div>
    </li>
    `);
}



function sendMessage() {
    let message = $("#msg").val();


    let today = new Date();

    let user = new Message(currentUser, message, today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds());

    let str = JSON.stringify(user);

    append(user, true);
    $("#msg").val("");
    wsocket.send(str);
}


function logout(){
    registerSocket.close();
}


