package ua.nure.serdiuk.server;

import ua.nure.serdiuk.entity.User;
import ua.nure.serdiuk.util.MessageContainer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

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
        Scanner scanner = null;
        PrintWriter pw = null;
        try {
            scanner = new Scanner(clientSocket.getInputStream());

            while (scanner.hasNextLine()) {
                String[] message = scanner.nextLine().split(":");

                System.out.println(Arrays.toString(message));
                container.add(new MessageContainer.Message(new User(message[0].trim(), ""), message[1].trim()));
            }
        } catch (IOException e) {
            System.out.println(String.format("IOException occurred: %s", e.getMessage()));
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (pw != null) {
                pw.close();
            }
        }

    }
}

