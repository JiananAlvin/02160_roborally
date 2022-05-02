package gui.game;

import content.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import model.Game;
import model.game.board.mat.ProgrammingDeck;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class MatPanel extends JPanel {

    private JLabel lblRobot;
    private JLabel lblRobotLives;
    private JLabel lblInfo;
    private JLabel lblCheckpointToken;
    private JLabel lblDeck;
    private JLabel lblDiscard;
    private JLabel lblDeckCards;
    private JLabel lblDiscardCards;

    private boolean dragComplete;
    private int fromRegisterIndex;
    private int toRegisterIndex;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    private JLabel lblRound;
    private JLabel lblRegister;
    private JLabel lblDiscardPile;
    private JLabel lblRegisters;
    private JLabel lblTimer;


    public MatPanel() throws IOException {
        // adding the user's information
        Game game = Game.getInstance();
        Icon iconRobot = new ImageIcon(RobotImageEnum.valueOf(game.getUser().getRobot().getName()).getImage().getScaledInstance(109, 140, Image.SCALE_DEFAULT));
        this.lblRobot = new JLabel(iconRobot);
        this.lblRobot.setOpaque(true);
        this.lblRobot.setBackground(game.getUser().getPlayerColor());
        this.lblRobotLives = new JLabel("Lives: " + game.getUser().getRobot().getLives());
        this.lblRobotLives.setFont(new Font("Default", Font.BOLD, 15));
        this.lblInfo = new JLabel("<html>Robot: " + game.getUser().getRobot().getName().replace('_', ' ') + "<br/>User: " + game.getUser().getName() + "</html>");
        this.lblInfo.setFont(new Font("Default", Font.BOLD, 15));
        this.lblCheckpointToken = new JLabel();
        Icon iconCheckpointToken = new ImageIcon(DecorationImageEnum.CHECKPOINT_TOKENS.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT));
        this.lblCheckpointToken.setIcon(iconCheckpointToken);
        this.lblInfo.setFont(new Font("Default", Font.BOLD, 15));
        this.lblCheckpointToken.setText("<html><br/>" + game.getUser().getRobot().getCheckpoints().size() + "</html>");
        this.lblCheckpointToken.setHorizontalTextPosition(JLabel.CENTER);
        this.lblCheckpointToken.setVerticalTextPosition(JLabel.CENTER);
        this.lblCheckpointToken.setForeground(Color.WHITE);
        this.lblCheckpointToken.setFont(new Font("Default", Font.BOLD, 20));
        Icon iconDeck = new ImageIcon(DecorationImageEnum.DECK.getImage().getScaledInstance(100, 140, Image.SCALE_DEFAULT));
        this.lblDeck = new JLabel(iconDeck);
        Icon iconDiscardPile = new ImageIcon(DecorationImageEnum.DISCARD.getImage().getScaledInstance(100, 140, Image.SCALE_DEFAULT));
        this.lblDiscardPile = new JLabel(iconDiscardPile);
        this.lblDeckCards = new JLabel("Programing Deck: " + game.getUser().getProgrammingDeck().getCards().size());
        this.lblDiscardCards = new JLabel("Discard Pile: " + game.getUser().getDiscardPile().getDiscards().size());
        this.lblDeckCards.setFont(new Font("Default", Font.BOLD, 13));
        this.lblDiscardCards.setFont(new Font("Default", Font.BOLD, 13));
        this.lblRound = new JLabel("Round: " + game.getCurrentRoundNum());
        this.lblRound.setFont(new Font("Calibri", Font.BOLD, 20));

        loadRegisterTable();

        this.lblRegister = new JLabel("<html><span bgcolor=\"green\">|Register1  Register2 Register3 Register4 Register5|</span></html>");
        this.lblDiscard = new JLabel("<html><span bgcolor=\"red\">|-------------------------Discard------------------------|</html>");
        this.lblRegister.setFont(new Font("Default", Font.BOLD, 15));
        this.lblDiscard.setFont(new Font("Default", Font.BOLD, 15));
        Icon iconClock = new ImageIcon(DecorationImageEnum.CLOCK.getImage().getScaledInstance(105, 114, Image.SCALE_DEFAULT));
        this.lblTimer = new JLabel();
        this.lblTimer.setIcon(iconClock);
        this.lblTimer.setHorizontalTextPosition(JLabel.CENTER);
        this.lblTimer.setVerticalTextPosition(JLabel.CENTER);
        this.lblTimer.setFont(new Font("Default", Font.BOLD, 20));

        this.setLayout(null);
        this.lblRobot.setBounds(20, 20, 109, 140);
        this.lblRobotLives.setBounds(20, 160, 60, 20);
        this.lblInfo.setBounds(137, 20, 200, 40);
        this.lblCheckpointToken.setBounds(157, 70, 90, 90);
        this.lblDeck.setBounds(340, 50, 100, 140);
        this.lblDiscardPile.setBounds(490, 50, 100, 140);
        this.lblDeckCards.setBounds(340, 5, 200, 50);
        this.lblDiscardCards.setBounds(490, 5, 200, 50);
        this.lblRound.setBounds(700, 0, 200, 50);
        this.scrollPane.setBounds(700, 40, 675, 130);
        this.lblRegister.setBounds(702, 170, 500, 20);
        this.lblDiscard.setBounds(1075, 170, 300, 20);
        this.lblTimer.setBounds(1400, 40, 117, 127);
        this.add(this.lblRobot);
        this.add(this.lblRobotLives);
        this.add(this.lblInfo);
        this.add(this.lblCheckpointToken);
        this.add(this.lblDeck);
        this.add(this.lblDiscardPile);
        this.add(this.lblDeckCards);
        this.add(this.lblDiscardCards);
        this.add(this.lblRound);
        this.add(this.scrollPane);
        this.add(this.lblRegister);
        this.add(lblDiscard);
        this.add(this.lblTimer);
    }

    /**
     * This method initializes register area.
     */
    private void loadRegisterTable() {
        // table header
        Game game = Game.getInstance();
        String[] columnNames = new String[ProgrammingDeck.NUMBER_OF_CARDS_DRAWN_IN_EACH_ROUND];
        for (int i = 0; i < ProgrammingDeck.NUMBER_OF_CARDS_DRAWN_IN_EACH_ROUND; i++) {
            String cardName = game.getUser().getCardsInHand().get(i).toString();
            columnNames[i] = cardName.substring(4);
        }
        // the first row adds the icons of the nine cards that the user "drew"
        Object[][] iconsCardInHand = new Icon[1][ProgrammingDeck.NUMBER_OF_CARDS_DRAWN_IN_EACH_ROUND];
        for (int i = 0; i < ProgrammingDeck.NUMBER_OF_CARDS_DRAWN_IN_EACH_ROUND; i++) {
            iconsCardInHand[0][i] = new ImageIcon(CardImageEnum.valueOf(game.getUser().getCardsInHand().get(i).toString()).getImage().getScaledInstance(75, 105, Image.SCALE_DEFAULT));
        }
        this.tableModel = new DefaultTableModel(iconsCardInHand, columnNames);
        this.table = new JTable(this.tableModel) {
            public Class getColumnClass(int column) {
                return Icon.class;
            }
        };
        // listening for the column dragging in the participants table
        this.table.getColumnModel().addColumnModelListener(new RegisterTableColumnModelListener());
        // when the user releases his mouse button, the oder of the nine cards(crds in his hand) is updated.
        this.dragComplete = false;
        this.table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (dragComplete) {
                    move(game.getUser().getCardsInHand(), fromRegisterIndex, toRegisterIndex);
                }
                dragComplete = false;
            }
        });

        this.table.setRowHeight(105);
        this.scrollPane = new JScrollPane(this.table);
        this.scrollPane.setPreferredSize(new Dimension(675, 130));
    }

    /**
     * This method moves element in a list from one index to another index.
     */
    private static void move(List<?> collection, int indexToMoveFrom, int indexToMoveAt) {
        if (indexToMoveAt >= indexToMoveFrom) {
            Collections.rotate(
                    collection.subList(indexToMoveFrom, indexToMoveAt + 1),
                    -1);
        } else {
            Collections.rotate(
                    collection.subList(indexToMoveAt, indexToMoveFrom + 1),
                    1);
        }
    }

    /**
     * This listener listens for the column dragging in the participants table
     */
    private class RegisterTableColumnModelListener implements TableColumnModelListener {

        public void columnAdded(TableColumnModelEvent e) {
        }

        public void columnRemoved(TableColumnModelEvent e) {
        }

        public void columnMoved(TableColumnModelEvent e) {
            if (!dragComplete)
                fromRegisterIndex = e.getFromIndex(); // when the user clicks on one of column headers
            dragComplete = true; // when the user starts to drag
            toRegisterIndex = e.getToIndex();
        }

        public void columnMarginChanged(ChangeEvent e) {
        }

        public void columnSelectionChanged(ListSelectionEvent e) {
        }
    }
}