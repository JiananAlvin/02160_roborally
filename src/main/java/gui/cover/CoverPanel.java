package gui.cover;

import content.RobotImageEnum;

import javax.swing.*;
import java.awt.*;

public class CoverPanel extends JPanel {

    public CoverPanel() {
        ImageIcon cover = new ImageIcon(RobotImageEnum.COVER.getImage().getScaledInstance(870, 390, Image.SCALE_DEFAULT));
        JLabel lblCover = new JLabel(cover);
        this.add(lblCover);
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame(Application.APP_TITLE);
//        frame.setSize(880, 400);
//        CoverPanel coverPanel = new CoverPanel();
//        frame.add(coverPanel);
//        frame.setVisible(true);
//    }
}
