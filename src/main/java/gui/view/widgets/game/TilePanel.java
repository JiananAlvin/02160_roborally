package gui.view.widgets.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import gui.view.map.TileImageEnum;
import model.game.board.map.Orientation;
import model.game.board.map.element.Tile;
import net.coobird.thumbnailator.Thumbnails;

public class TilePanel extends JPanel {

    public static final int PIXEL_SIZE = 60;
    private Tile tile;
    private BufferedImage image;
    private BufferedImage imageArrow;
    private boolean containsRobot = false;
    private Orientation direction;
    private Color backgroundColor;

    public TilePanel(Tile tile) {
        super(true);
        this.image = tile.getTileImageEnum().getImage();
        this.tile = tile;
        try {
            this.imageArrow = resize(TileImageEnum.ARROW.getImage(), PIXEL_SIZE, PIXEL_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setMinimumSize(new Dimension(PIXEL_SIZE, PIXEL_SIZE));
        setMaximumSize(getMinimumSize());
        setPreferredSize(getMinimumSize());
    }

    public void setRobot(Orientation direction, Color bgColor) {
        this.containsRobot = true;
        this.direction = direction;
        this.backgroundColor = bgColor;
    }

    public void unsetRobot() {
        this.containsRobot = false;
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image, 0, 0, PIXEL_SIZE, PIXEL_SIZE, null);  // the width of the rectangle, pixel

        if (containsRobot) {
            System.out.println("drawing" + new Random().nextInt(10));
            AffineTransform old2 = g2d.getTransform();
//          draw bg
            g.setColor(this.backgroundColor);
            Ellipse2D.Double circle = new Ellipse2D.Double(10, 10, 30, 30);
            g2d.fill(circle);
//            draw arrow
//            g2d.rotate(Math.toRadians(direction.getAngle()), 33, 33);
            g2d.rotate(Math.toRadians(direction.getAngle()), 25, 25);
            g2d.drawImage(imageArrow, 0, 0, null);
            g2d.setTransform(old2);
        }
    }

    public BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException {
        return Thumbnails.of(img).size(newW, newH).asBufferedImage();
    }
}
