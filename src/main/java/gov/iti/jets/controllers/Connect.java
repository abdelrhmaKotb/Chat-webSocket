package gov.iti.jets.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gov.iti.jets.model.User;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/connect")
public class Connect {

    static List<String> messages = new ArrayList<>();
    static Map<Session,User> users = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        if (messages.size() > 0) {
            for (String str : messages) {
                try {
                    session.getBasicRemote().sendText(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {


        messages.add(message);

        System.out.println(messages.size());

        try {
            for (var s : session.getOpenSessions()) {
                if (!s.equals(session)) {
                    s.getBasicRemote().sendText(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("closed");
    }

    @OnError
    public void onErr(Throwable err) {
        System.out.println("err");
    }



   

}
