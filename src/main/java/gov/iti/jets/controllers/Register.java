package gov.iti.jets.controllers;

import java.io.IOException;
import java.util.LinkedList;

import com.google.gson.Gson;

import gov.iti.jets.model.User;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/register")
public class Register {

    @OnMessage
    public void OnMessage(String currentUser, Session session) {

        for (Session s : session.getOpenSessions()) {
            try {
                if (!s.equals(session)) {
                    s.getBasicRemote().sendText(currentUser);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!Connect.users.containsKey(session)) {
            User user = toPojo(currentUser);
            System.out.println(user);
            Connect.users.put(session, new User(user.getName(), user.getGender()));
        }

        System.out.println("users " + Connect.users);

    }

    private User toPojo(String json) {
        Gson g = new Gson();
        return g.fromJson(json, User.class);
    }

    @OnClose
    public void close(Session session) {
        Connect.users.remove(session);
        System.out.println(Connect.users);

        for (Session s : session.getOpenSessions()) {
            try {

                if (!s.equals(session)) {
                    java.util.List<User> list = new LinkedList<User>(Connect.users.values().stream().toList());
                    list.remove(Connect.users.get(s));
                    String json = new Gson().toJson(list);
                    s.getBasicRemote().sendText(json);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
