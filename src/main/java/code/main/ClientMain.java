package code.main;

import code.client.Client;
import code.client.ClientView;

public class ClientMain {
    public static void main(String[] args) {
        ClientView clientView = new ClientView();
        Client client = new Client();
        clientView.setClientThread(client);
        client.setClientViewThread(clientView);
        clientView.start();
        client.start();
    }

}
