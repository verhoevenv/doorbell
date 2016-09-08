package com.github.verhoevenv.doorbell;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(value="/chime")
public class ChimeSocket {

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onWebSocketConnect(final Session session) {
        System.out.println("Socket Connected: " + session);
        sessions.add(session);
    }

    @OnMessage
    public void onWebSocketText(final Session client, String message) throws Exception {
        System.out.println("Received TEXT message: " + message);
        for( final Session session: sessions ) {
            if(session != client)
                session.getBasicRemote().sendText(message);
        }
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
}