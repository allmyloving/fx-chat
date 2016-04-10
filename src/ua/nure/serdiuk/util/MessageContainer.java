package ua.nure.serdiuk.util;

import ua.nure.serdiuk.entity.User;

import java.util.ArrayList;

public class MessageContainer extends ArrayList<MessageContainer.Message> {

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
