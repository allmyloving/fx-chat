package ua.nure.serdiuk.server;

import ua.nure.serdiuk.Settings;
import ua.nure.serdiuk.util.MessageContainer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        List<Socket> sockets = new ArrayList<>();
        MessageContainer container = new MessageContainer(sockets);

//        try {
        ServerSocket server = new ServerSocket(Settings.PORT_NUM);
        while (true) {
            Socket s = server.accept();
            ServerThread t = new ServerThread(s, container);
            t.setDaemon(true);
            t.start();

            sockets.add(s);
        }
//        }

    }
}
