package com.github.verhoevenv.doorbell;

import com.google.gson.Gson;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(value="/chime")
public class ChimeSocket {

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());


    public static void ring() {
        for (Session session : sessions) {
            sendTo(session);
        }
    }

    private static void sendTo(Session session) {
        System.out.println("ringing");
        try {
            String jsonMessage = new Gson().toJson(new RingMessage());
            session.getBasicRemote().sendText(jsonMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onWebSocketConnect(final Session session) {
        System.out.println("Socket Connected: " + session);
        sessions.add(session);
    }

    @OnClose
    public void onWebSocketClose(final Session session, CloseReason reason) {
        System.out.println("Socket Closed: " + reason);
        sessions.remove(session);
    }

    @OnError
    public void onWebSocketError(Throwable cause) {
        cause.printStackTrace(System.err);
    }

    private static class RingMessage {
    }
}