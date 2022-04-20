package gui.view.widgets;

import content.RobotName;
import model.game.Player;
import model.game.board.map.element.Robot;

import javax.swing.*;

public class MatPanel extends JPanel {
    private Player user;

    public MatPanel(String userName, String robotName) {
        this.user = new Player(userName, new Robot(RobotName.valueOf(robotName)));
    }

}
