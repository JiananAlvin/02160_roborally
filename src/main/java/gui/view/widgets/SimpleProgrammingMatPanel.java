package gui.view.widgets;

import lombok.Data;
import model.Game;
import model.game.Player;
import model.game.board.map.element.Robot;
import model.game.board.mat.element.ProgrammingDeck;
import model.game.board.mat.element.RegisterArea;
import server.controller.ProgrammingRecordController;
import server.controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

@Data
public class SimpleProgrammingMatPanel extends JPanel {

    private JTextField[] registers;
    private JLabel labelTokenNumber;
    private JLabel labelLive;
    private JLabel labelUserName;
    private JLabel labelRobotName;
    private JLabel labelRound;
    private JLabel labelTimer;
    private Timer timer;
    public static final int MAX_WAITING_TIME = 10;

    public SimpleProgrammingMatPanel(Game game) {
        super(true);
        this.setLayout(null);
        this.registers = new JTextField[5];
        this.labelTokenNumber = new JLabel("TokenNumber:0");
        this.labelLive = new JLabel("Live:5");
        this.labelUserName = new JLabel(game.getUser().getName());
        this.labelRobotName = new JLabel(game.getUser().getRobot().getName());
        this.labelRound = new JLabel("Round:" + game.getCurrentRoundNum());
        this.labelTimer = new JLabel("LeftTime:");

        this.labelUserName.setBounds(0, 0, 100, 25);
        this.add(labelUserName);
        this.labelRobotName.setBounds(100, 0, 100, 25);
        this.add(labelRobotName);
        this.labelTokenNumber.setBounds(200, 0, 100, 25);
        this.add(labelTokenNumber);
        this.labelLive.setBounds(300, 0, 100, 25);
        this.add(labelLive);
        this.labelRound.setBounds(400, 0, 100, 25);
        this.add(labelRound);
        this.labelTimer.setBounds(500, 0, 100, 25);
        this.add(labelTimer);

        for (int i = 0; i < this.registers.length; i++) {
            this.registers[i] = new JTextField("Move1");
            this.registers[i].setBounds(100 * i, 25, 100, 25);
            this.add(registers[i]);
        }
        this.setPreferredSize(new Dimension(1000, 50));
//        define a new timer to calculate
    }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("GridBagLayoutDemo");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(frame.getPreferredSize());
                //Set up the content pane.
                Game game = new Game();
                game.setUser(new Player("Wenjie", new Robot("TRUNDLE_BOT")));
                frame.add(new SimpleProgrammingMatPanel(game));

                //Display the window.
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
