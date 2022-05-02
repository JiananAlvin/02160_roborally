package content;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public enum RobotImageEnum {

    HAMMER_BOT("/images/robots/HAMMER_BOT.png"),
    HULK_X90("/images/robots/HULK_X90.png"),
    SPIN_BOT("/images/robots/SPIN_BOT.png"),
    SQUASH_BOT("/images/robots/SQUASH_BOT.png"),
    TRUNDLE_BOT("/images/robots/TRUNDLE_BOT.png"),
    ZOOM_BOT("/images/robots/ZOOM_BOT.png"),
    COVER("/images/robots/cover.png");

    private final BufferedImage image;

    private RobotImageEnum(String fileName) {
        BufferedImage tempImage;
        try {
            tempImage = ImageIO.read(getClass().getResource(fileName));
        } catch (Exception e) {
            tempImage = null;
        }
        this.image = tempImage;
    }

    public BufferedImage getImage() {
        return this.image;
    }
}
