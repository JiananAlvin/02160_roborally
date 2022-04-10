package gui.view.widgets;

import javax.swing.*;
import java.awt.*;

public class CoverPanel {
    private final ImageIcon cover = new ImageIcon(new ImageIcon("src/main/resources/images/robots/cover.jpg").getImage().getScaledInstance(900, 400, Image.SCALE_DEFAULT));
    private final JLabel lblCover = new JLabel(cover);
}
