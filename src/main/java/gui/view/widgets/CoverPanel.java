package gui.view.widgets;

import content.Application;

import javax.swing.*;
import java.awt.*;

public class CoverPanel extends JPanel {

    private static final String PATH_TO_COVER_IMAGE = "src/main/resources/images/robots/cover.jpg";

    public CoverPanel() {
        ImageIcon cover = new ImageIcon(new ImageIcon(PATH_TO_COVER_IMAGE).getImage().getScaledInstance(870, 360, Image.SCALE_DEFAULT));
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
