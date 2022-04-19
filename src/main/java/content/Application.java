package content;

import gui.view.widgets.CoverPanel;
import gui.view.widgets.LoginPanel;
import lombok.SneakyThrows;
import model.Game;
import model.game.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is an entrance for our user application
 */
public class Application {

    private Game game;
    private Player user;
    private CoverPanel coverPanel;
    private LoginPanel loginPanel;
    private JFrame frame;
    private static Application applicationInstance;
    public final static String APP_TITLE = "RoboRally Group10 v1.0";

    private Application() {
        // Initialize all the elements in an APP
        this.frame = new JFrame(APP_TITLE);
        this.game = new Game();
        this.user = new Player();
        this.coverPanel = new CoverPanel();
        this.loginPanel = new LoginPanel(frame);
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
                app.frame.setSize(880, 400);
                app.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                app.frame.setLocationRelativeTo(null); // this method display the JFrame to center position of a screen
                app.frame.getContentPane().add(app.coverPanel);
                app.frame.setVisible(true);
                Timer timer = new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        app.frame.getContentPane().removeAll();
                        app.frame.getContentPane().add(app.loginPanel);
                        app.frame.setVisible(true);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
    }
}

