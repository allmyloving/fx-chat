package ua.nure.serdiuk;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import ua.nure.serdiuk.entity.User;
import ua.nure.serdiuk.util.DbManager;
import ua.nure.serdiuk.util.MessageContainer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;

public class Controller {

    @FXML
    private Button sendButton;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField messageText;

    private Socket clientSocket;

    private PrintWriter out;

    private Scanner in;

    private MessageContainer messages;

    private User currentUser;

    private void showModal() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Please, enter your credentials");

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> username.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();


        if (result.isPresent()) {
            currentUser = DbManager.auth(result.get().getKey(), result.get().getValue());
        }
        if (currentUser == null) {
            showModal();
        }
    }

    @FXML
    public void initialize() {
        showModal();
        try {
            clientSocket = new Socket("localhost", Settings.PORT_NUM);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
//            in = new Scanner(clientSocket.getInputStream());
        } catch (IOException e) {
            System.out.println(String.format("IOException occurred: %s", e.getMessage()));
        }
    }

    @FXML
    public void sendButtonOnClick() {
        String text = messageText.getText();
        textArea.appendText(text.concat(System.lineSeparator()));
        messageText.clear();

        out.println(String.format("%s: %s", currentUser.getLogin(), text));
    }
}
