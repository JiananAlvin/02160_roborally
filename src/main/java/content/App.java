package content;

import gui.cover.CoverPanel;
import gui.login.LoginPanel;
import lombok.SneakyThrows;
import model.Game;
import model.game.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is an entrance for our user application
 */
public class App {

    private Game game;
    private Player user;
    private CoverPanel coverPanel;
    private LoginPanel loginPanel;
    private JFrame frame;
    private static App appInstance;
    public final static String APP_TITLE = "RoboRally Group10 v1.0";

    private App() {
        // Initialize all the elements in an APP
        this.frame = new JFrame(APP_TITLE);
        this.game = new Game();
        this.user = new Player();
        this.coverPanel = new CoverPanel();
        this.loginPanel = new LoginPanel(frame);
    }

    public static App getApplicationInstance() {
        if (App.appInstance == null)
            App.appInstance = new App();
        return App.appInstance;
    }

    public boolean run() {
        return App.appInstance != null;
    }

    @SneakyThrows
    public static void main(String[] args) {
        App app = App.getApplicationInstance();
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

