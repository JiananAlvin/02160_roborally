package gui.view.widgets;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class CoverFrame extends JFrame{

    private final JLabel lblCover;

    public CoverFrame() {
        super("Roborally Group10 v1.0");
        ImageIcon cover = new ImageIcon(new ImageIcon("src/main/resources/images/robots/cover.jpg").getImage().getScaledInstance(900, 400, Image.SCALE_DEFAULT));
        this.lblCover = new JLabel(cover);
    }

    public void showCoverFrame() {
        this.setSize(900, 400);
        this.add(lblCover);
        this.setVisible(true);
    }
}
