package code.client;

import code.utils.CloseUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient extends Thread{



    private String serverIP;
    private String username;
    private int port;
    PrintWriter sender;
    BufferedReader getter;
    Socket socket;
    boolean stopChat = false;
    Client parentThread;
    ListenerThread listener;

    public ChatClient(String serverIP,int port,String username,Client parentThread) throws IOException {
        this.serverIP = serverIP;
        this.port = port;
        this.parentThread = parentThread;
        this.socket = new Socket(serverIP,port);
        this.username = username;
        sender = new PrintWriter(socket.getOutputStream(),true);
        getter = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sender.println(username);
        listener = new ListenerThread();
        listener.start();

    }

    public void sendMessage(String message){
        sender.println(message);
        sender.flush();
    }

    @Override
    public void run() {
        super.run();
        try{

            while (!stopChat){

            }
            listener.shutdown();
            sender.println("[OFFLINE]");
            CloseUtil.close(sender,getter,socket);
        }catch (Exception e){
            return;
        }
    }

    private class ListenerThread extends Thread{
        private boolean stop = false;

        @Override
        public void run() {
            super.run();
            while (!stop){
                try {
                    String message = getter.readLine();
                    if(message == null)continue;
                    parentThread.receiveMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        public void shutdown(){
            stop = true;
        }
    }

    public void stopChatThread() { stopChat = true; }
}
