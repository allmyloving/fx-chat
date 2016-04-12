package ua.nure.serdiuk.server;

import ua.nure.serdiuk.entity.User;
import ua.nure.serdiuk.util.MessageContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

public class ServerThread extends Thread {

    private Socket clientSocket;

    private MessageContainer container;

    public ServerThread(Socket server, MessageContainer container) {
        this.clientSocket = server;
        this.container = container;
    }

    @Override
    public void run() {
        System.out.println(String.format("Thread #%d started", getId()));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            while (true) {
                String line = reader.readLine();
                String[] message = line.split(":");
                System.out.println(Arrays.toString(message));
                container.add(new MessageContainer.Message(new User(message[0].trim(), ""), message[1].trim()));
            }
        } catch (IOException e) {
            System.out.println(String.format("run#IOException occurred: %s", e.getMessage()));
        } finally {
            System.out.println("finally");
//            closeSocket();
        }
    }

    private void closeSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

