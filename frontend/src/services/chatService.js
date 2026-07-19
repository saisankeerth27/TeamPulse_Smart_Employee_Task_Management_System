import SockJS from "sockjs-client";
import Stomp from "stompjs";
import api from "./api";

let stompClient = null;

export const connect = (userId, onMessageReceived) => {

    const socket = new SockJS(
        "http://localhost:8080/chat"
    );

    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {

        stompClient.subscribe(
            "/user/" + userId + "/queue/messages",

            (message) => {

                onMessageReceived(
                    JSON.parse(message.body)
                );

            }

        );

    });

};

export const sendMessage = (message) => {

    stompClient.send(

        "/app/send",

        {},

        JSON.stringify(message)

    );

};

export const getConversation = (sender, receiver) => {

    return api.get(`/chat/${sender}/${receiver}`);

};
