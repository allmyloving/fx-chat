package ua.nure.serdiuk.util;

import ua.nure.serdiuk.entity.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MessageContainer extends ArrayList<MessageContainer.Message> {

    private List<Socket> sockets;

    public MessageContainer(List<Socket> threads) {
        this.sockets = threads;
    }

    @Override
    public boolean add(Message message) {
        for (Socket socket : sockets) {
            try (PrintWriter pw = new PrintWriter(socket.getOutputStream(), true)) {
                pw.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.add(message);
    }

    public static class Message {
        private User user;

        private String data;

        public Message(User user, String data) {
            this.user = user;
            this.data = data;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(user.getLogin());
            sb.append(": ").append(data);
            return sb.toString();
        }
    }
}
