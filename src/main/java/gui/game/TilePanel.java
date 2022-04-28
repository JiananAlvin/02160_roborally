package gui.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import content.OrientationEnum;
import content.RobotImageEnum;
import model.game.Player;
import model.game.board.map.element.Tile;
import net.coobird.thumbnailator.Thumbnails;

public class TilePanel extends JPanel {

    public static final int PIXEL_SIZE = 60;
    private Tile tile;
    private BufferedImage imageTile;
    private BufferedImage imageRobot;
    private boolean containsRobot = false;
    private OrientationEnum direction;
    private Color backgroundColor;

    public TilePanel(Tile tile) {
        super(true);
        this.imageTile = tile.getTileImageEnum().getImage();
        this.tile = tile;
        setMinimumSize(new Dimension(PIXEL_SIZE, PIXEL_SIZE));
        setMaximumSize(getMinimumSize());
        setPreferredSize(getMinimumSize());
    }

    public void setRobot(OrientationEnum direction, Player player) {
        this.containsRobot = true;
        this.direction = direction;
        this.backgroundColor = player.getPlayerColor();
        try {
            this.imageRobot = resize(RobotImageEnum.valueOf(player.getRobot().getName()).getImage(), PIXEL_SIZE, PIXEL_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unsetRobot() {
        this.containsRobot = false;
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(imageTile, 0, 0, PIXEL_SIZE, PIXEL_SIZE, null);  // the width of the rectangle, pixel

        if (containsRobot) {
            AffineTransform old2 = g2d.getTransform();
            // draw background
            g2d.setColor(this.backgroundColor);
            Ellipse2D.Double circle = new Ellipse2D.Double((int) (PIXEL_SIZE / 4), (int) (PIXEL_SIZE / 4), (int) (PIXEL_SIZE / 2), (int) (PIXEL_SIZE / 2));
            g2d.fill(circle);
            // draw arrow

            g2d.rotate(Math.toRadians(direction.getAngle()), (int) (PIXEL_SIZE / 2), (int) (PIXEL_SIZE / 2));
            g2d.drawImage(this.imageRobot, 0, 0, null);
            g2d.setTransform(old2);
        }
    }

    public BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException {
        return Thumbnails.of(img).size(newW, newH).asBufferedImage();
    }
}
