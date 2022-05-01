package app;

import controller.game.GameManager;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;

/**
 * This class is an entrance for our user application
 */
public class ClientRunner {

    @Getter
    private final JFrame frame;
    private static ClientRunner clientRunnerInstance;
    public final static String APP_TITLE = "RoboRally Group10 v2.0";
    @Getter
    private final static GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices()[0];

    private ClientRunner() {
        this.frame = new JFrame(APP_TITLE);
    }

    public static ClientRunner getApplicationInstance() {
        if (ClientRunner.clientRunnerInstance == null)
            ClientRunner.clientRunnerInstance = new ClientRunner();
        return ClientRunner.clientRunnerInstance;
    }


    @SneakyThrows
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameManager.getInstance().showLoginPanel();
        });
    }
}

