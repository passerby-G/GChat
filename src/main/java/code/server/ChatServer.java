package code.server;

import code.constants.ChatServerConstants;
import code.utils.CloseUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ChatServer extends Thread{

    private List<ClientHandler> clients = new ArrayList<ClientHandler>();
    private ServerSocket server;

    public ChatServer() throws IOException {
        server = new ServerSocket(ChatServerConstants.CHATSERVER_PORT);
        System.out.println("ChatServer start at [A] " + server.getInetAddress() + " [P] " + server.getLocalPort());
    }

    @Override
    public void run() {
        super.run();

        while(true){
            try{
                ClientHandler client = new ClientHandler(server.accept());
                client.start();
                clients.add(client);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private class ClientHandler extends Thread{
        private Socket socket;
        private BufferedReader getter;
        private PrintWriter sender;
        private String username;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.getter = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.sender = new PrintWriter(socket.getOutputStream(),true);
            this.username = getter.readLine();

            //将新上线的用户信息发送给所有以在线的用户
            sendToAllUser("ONLINE[#]" + username + "[#]" + getIP() + "[#]" + getPort());

            //将当前在线用户信息发给新上线用户
            for (ClientHandler client : clients) {
                if(client.getIdentifier().equals(this.getIdentifier()))
                    continue;
                sender.println("INFO[#]" + client.getUsername() + "[#]" + client.getIP() + "[#]" + client.getPort());
            }
            sender.flush();

            //在server端打印新用户信息
            System.out.println("ChatServer: [ONLINE] [" + username + "] [" + getIdentifier() + "]");


        }

        //群发消息
        private void sendToAllUser(String message) {
            for (ClientHandler client : clients) {
                if(client.getIdentifier().equals(this.getIdentifier()))
                    continue;
                client.getSender().println(message);
                client.getSender().flush();
            }


        }

        //私聊信息
        private void sendToSpecificUser(String message, String dest) {
            for (ClientHandler client : clients) {
                if (client.getUsername().equals(dest)){
                    client.getSender().println(message);
                    client.getSender().flush();
                }
            }
        }

        @Override
        public void run() {
            super.run();
            String line;
            while (true){
                try{
                    line = getter.readLine();
                    if(line.equals("[OFFLINE]")){
                        offline();
                        return;
                    }else{

                        System.out.println(line);
                        StringTokenizer tokenizer = new StringTokenizer(line,"[#]");

                        String command = tokenizer.nextToken();
                        String message = tokenizer.nextToken();

                        if (command.equals("GROUP")){
                            sendToAllUser("GROUP[#]" + username + "[#]" + message);
                        }else{
                            String dest = tokenizer.nextToken();
                            sendToSpecificUser("P2P[#]" + username + "[#]" + message,dest);
                        }
                    }
                }catch (Exception e){
                    offline();
                    return;
                }
            }
        }

        private void offline(){
            sendToAllUser("OFFLINE[#]" + username + "[#]" + getIP() + "[#]" + getPort());
            System.out.println("ChatServer: [OFFLINE] [" + username + "] [" + getIdentifier() + "]");
            CloseUtil.close(getter,sender,socket);
            for (ClientHandler client : clients) {
                if(client.getIdentifier().equals(this.getIdentifier())){
                    clients.remove(client);
                    return;
                }
            }
        }



        PrintWriter getSender() { return sender; }
        int getPort() { return socket.getPort(); }
        String getIP() { return socket.getInetAddress().getHostAddress(); }
        String getUsername() { return username; }
        String getIdentifier() { return socket.getInetAddress().getHostAddress() + ":" + socket.getPort(); }



    }

}
