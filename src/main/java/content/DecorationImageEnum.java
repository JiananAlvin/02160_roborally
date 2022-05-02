package content;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public enum DecorationImageEnum {

    CLOCK("/images/decorations/clock.png"),
    DECK("/images/decorations/deck.png"),
    DISCARD("/images/decorations/discard.png"),
    CHECKPOINT_TOKENS("/images/decorations/checkpoint_tokens.png");

    private final BufferedImage image;

    private DecorationImageEnum(String fileName) {
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
