package code.main;

import code.server.ChatServer;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) {
        try {
            ChatServer chatServer = new ChatServer();
            chatServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
