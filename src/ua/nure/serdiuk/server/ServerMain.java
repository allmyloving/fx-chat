package ua.nure.serdiuk.server;

import ua.nure.serdiuk.Settings;
import ua.nure.serdiuk.util.MessageContainer;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        MessageContainer container = new MessageContainer();
        try (ServerSocket server = new ServerSocket(Settings.PORT_NUM)) {
            while (true) {
                Thread t = new ServerThread(server.accept(), container);
                t.setDaemon(true);
                t.start();
            }
        }

    }
}
