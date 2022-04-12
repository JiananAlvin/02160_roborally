package gui.view.widgets;

import javax.swing.*;
import java.awt.*;

public class CoverPanel extends JPanel {

    private final JLabel lblCover;

    public CoverPanel() {
        ImageIcon cover = new ImageIcon(new ImageIcon("src/main/resources/images/robots/cover.jpg").getImage().getScaledInstance(870, 360, Image.SCALE_DEFAULT));
        this.lblCover = new JLabel(cover);
        this.add(lblCover);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RoboRally Group10 v1.0");
        frame.setSize(880, 400);
        CoverPanel coverPanel = new CoverPanel();
        frame.add(coverPanel);
        frame.setVisible(true);
    }
}
