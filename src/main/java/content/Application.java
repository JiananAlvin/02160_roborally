package content;

import gui.view.widgets.CoverFrame;
import gui.view.widgets.LoginFrame;
import lombok.SneakyThrows;
import model.Game;
import model.game.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

/**
 * This class is an entrance for our user application
 */
public class Application {

    private Game game;
    private Player user;
    private LoginFrame loginFrame;
    private CoverFrame coverFrame;
    private static Application applicationInstance;

    private Application() {
//        Initialize all the elements in on APP
        this.game = new Game();
        this.user = new Player();
        this.loginFrame = new LoginFrame();
        this.coverFrame = new CoverFrame();
    }

    public static Application getApplicationInstance() {
        if (Application.applicationInstance == null)
            Application.applicationInstance = new Application();
        return Application.applicationInstance;
    }

    public boolean run() {
        return Application.applicationInstance != null;
    }

    @SneakyThrows
    public static void main(String[] args) {
        Application app = Application.getApplicationInstance();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                applicationInstance.coverFrame.showCoverFrame();
                Timer timer = new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        applicationInstance.coverFrame.dispose();
                        applicationInstance.loginFrame.showLoginFrame();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
    }
}

