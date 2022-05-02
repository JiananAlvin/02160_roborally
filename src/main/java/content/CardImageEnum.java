package content;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public enum CardImageEnum {

    CardAgain("/images/programming_cards/CardAgain.png"),
    CardBackUp("/images/programming_cards/CardBackUp.png"),
    CardMove1("/images/programming_cards/CardMove1.png"),
    CardMove2("/images/programming_cards/CardMove2.png"),
    CardMove3("/images/programming_cards/CardMove3.png"),
    CardPowerUp("/images/programming_cards/CardPowerUp.png"),
    CardTurnLeft("/images/programming_cards/CardTurnLeft.png"),
    CardTurnRight("/images/programming_cards/CardTurnRight.png"),
    CardUTurn("/images/programming_cards/CardUTurn.png");

    private final BufferedImage image;

    private CardImageEnum(String fileName) {
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

