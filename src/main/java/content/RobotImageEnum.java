package content;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public enum RobotImageEnum {

    HAMMER_BOT("src/main/resources/images/robots/HAMMER_BOT.png"),
    HULK_X90("src/main/resources/images/robots/HULK_X90.png"),
    SPIN_BOT("src/main/resources/images/robots/SPIN_BOT.png"),
    SQUASH_BOT("src/main/resources/images/robots/SQUASH_BOT.png"),
    TRUNDLE_BOT("src/main/resources/images/robots/TRUNDLE_BOT.png"),
    ZOOM_BOT("src/main/resources/images/robots/ZOOM_BOT.png"),
    COVER("src/main/resources/images/robots/cover.png");

    private final BufferedImage image;

    private RobotImageEnum(String fileName) {
        BufferedImage tempImage;
        try {
            tempImage = ImageIO.read(new File(fileName));
        } catch (Exception e) {
            tempImage = null;
        }
        this.image = tempImage;
    }

    public BufferedImage getImage() {
        return this.image;
    }
}
