package code.client;

import java.util.*;

public class Client extends Thread {

    private ChatClient chatClient;
    private ClientView clientView;
    private Map<String,ArrayList<String>> chatRecords = new HashMap<String, ArrayList<String>>();
    private boolean stopClient = false;
    private String username;

    public void setClientViewThread(ClientView cv) { clientView = cv; }


    @Override
    public void run() {
        super.run();
        while (!stopClient){
        }
    }


    //连接服务
    public void connect(String ip, int port, String username){
        try{
            this.username = username;
            chatClient = new ChatClient(ip,port,username,this);
            chatRecords.put("GroupChat",new ArrayList<String>());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //发送消息
    public void sendMessage(String message){
        if (message.equals("[OFFLINE]")){
            chatClient.sendMessage(message);
            return;
        }
        StringTokenizer tokenizer = new StringTokenizer(message,"[#]");
        String command = tokenizer.nextToken();
        String msg = tokenizer.nextToken();
        if(command.equals("P2P")){
            String usr = tokenizer.nextToken();
            chatRecords.get(usr).add(username + "[#]" + msg);
        }else{
            chatRecords.get("GroupChat").add(username + "[#]" + msg);
        }
        chatClient.sendMessage(message);
    }


    //退出服务
    public void disconnect(){
        chatClient.stopChatThread();
        stopClient = true;
    }

    //获取对应用户的聊天记录
    public List<String> getChatRecords(String username){
        return chatRecords.get(username);
    }

    //接收消息
    public void receiveMessage(String message) {
        StringTokenizer tokenizer = new StringTokenizer(message,"[#]");
        String command = tokenizer.nextToken();
        String usr = tokenizer.nextToken();
        if(command.equals("INFO") || command.equals("ONLINE")){
            String ip = tokenizer.nextToken();
            String port = tokenizer.nextToken();
            chatRecords.put(usr,new ArrayList<String>());
            clientView.updateGUI("ONLINE",usr,"");
        }else if(command.equals("GROUP")){
            String msg = tokenizer.nextToken();
            chatRecords.get("GroupChat").add(usr + "[#]" + msg);
            clientView.updateGUI("GROUP", msg, usr);
        }else if(command.equals("P2P")){
            String msg = tokenizer.nextToken();
            chatRecords.get(usr).add(usr + "[#]" + msg);
            clientView.updateGUI("P2P", msg, usr);
        }else if(command.equals("OFFLINE")){
            String ip = tokenizer.nextToken();
            String port = tokenizer.nextToken();
            chatRecords.remove(usr);
            clientView.updateGUI("OFFLINE", usr, "");
        }
    }
}
