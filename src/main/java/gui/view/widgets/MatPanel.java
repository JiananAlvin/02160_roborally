package gui.view.widgets;

import content.Application;
import model.Game;
import model.game.Player;
import model.game.board.map.element.Robot;
import model.game.board.mat.element.ProgrammingDeck;
import server.controller.RobotController;
import server.controller.RoomController;
import server.controller.UserController;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MatPanel extends JPanel {

    private final String PATH_TO_CARD_ICONS = "src/main/resources/images/programming_cards/";
    private final String PATH_TO_DECORATIONS_ICONS = "src/main/resources/images/decorations/";
    private ArrayList<String> namesOfCardsInHand;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JLabel lblRegisters;
    private JLabel lblClock;
    private JLabel lblRound;


    public MatPanel(Game game) {
        this.namesOfCardsInHand = new ArrayList<>();
        game.getUser().drawCards();
        game.getUser().getCardsInHand().forEach((card) -> this.namesOfCardsInHand.add(card.getClass().getSimpleName()));

        // Adding the user information

        this.lblRound = new JLabel("Round: " + game.getCurrentRoundNum());
        this.lblRound.setFont(new Font("Calibri", Font.BOLD, 20));
        // Initializing register area
        String[] columnNames = new String[ProgrammingDeck.NUMBER_OF_CARDS_DRAWN_IN_EACH_ROUND];
        for (int i = 0; i < ProgrammingDeck.NUMBER_OF_CARDS_DRAWN_IN_EACH_ROUND; i++) {
            columnNames[i] = "DRAG HERE";
        }

        Icon[][] iconsCardInHand = new Icon[1][ProgrammingDeck.NUMBER_OF_CARDS_DRAWN_IN_EACH_ROUND];
        for (int i = 0; i < ProgrammingDeck.NUMBER_OF_CARDS_DRAWN_IN_EACH_ROUND; i++) {
            iconsCardInHand[0][i] = new ImageIcon(new ImageIcon(PATH_TO_CARD_ICONS + this.namesOfCardsInHand.get(i) + ".png").getImage().getScaledInstance(75, 105, Image.SCALE_DEFAULT));
        }

        this.tableModel = new DefaultTableModel(iconsCardInHand, columnNames);
        this.table = new JTable(this.tableModel) {
            public Class getColumnClass(int column) {
                return Icon.class;
            }
        };
        this.table.setRowHeight(105);
        this.scrollPane = new JScrollPane(this.table);
        this.scrollPane.setPreferredSize(new Dimension(675, 130));

        this.lblRegisters = new JLabel("|Register1||Register2||Register3||Register4||Register5|------------------------Discard------------------------|");
        this.lblRegisters.setFont(new Font("Default", Font.BOLD, 15));
        Icon iconClock = new ImageIcon(new ImageIcon(PATH_TO_DECORATIONS_ICONS + "clock.png").getImage().getScaledInstance(117, 127, Image.SCALE_DEFAULT));
        this.lblClock = new JLabel(iconClock);

        this.setLayout(null);

        this.lblRound.setBounds(700, 550, 50, 50);
        this.scrollPane.setBounds(700, 600, 675, 130);
        this.lblRegisters.setBounds(698, 740, 700, 20);
        this.lblClock.setBounds(1400, 610, 117, 127);

        this.add(this.lblRound);
        this.add(this.scrollPane);
        this.add(this.lblRegisters);
        this.add(this.lblClock);
    }


    public static void main(String[] args) {
        Player user = new Player("SpongeBob", new Robot("SQUASH_BOT"));
        UserController userController = new UserController();
        userController.createUser("SpongeBob");
        userController.createUser("PatrickStar");
//        RobotController robotController = new RobotController();
        userController.chooseRobot(user.getName(), user.getRobot().getName());
        userController.chooseRobot("PatrickStar", "ZOOM_BOT");
        RoomController roomController = new RoomController();
        int roomNumber = roomController.createRoom("SpongeBob", "STARTER").getInt("room_number");
        userController.joinRoom("PatrickStar", roomNumber);
//        Game game = new Game(user.getName());

        JFrame frame = new JFrame(Application.APP_TITLE);
        frame.setSize(1650,1080);
        MatPanel MatPanel = new MatPanel(game);
        frame.add(MatPanel);
        frame.setVisible(true);


    }
}