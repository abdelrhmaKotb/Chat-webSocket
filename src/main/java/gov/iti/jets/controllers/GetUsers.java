package gov.iti.jets.controllers;

import java.io.IOException;

import com.google.gson.Gson;

import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/get-users")
public class GetUsers {
    

    @OnMessage
    public void OnMessage(String message, Session session){
        Gson g = new Gson();
        String users = g.toJson(Connect.users.values());
        try {
            session.getBasicRemote().sendText(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
