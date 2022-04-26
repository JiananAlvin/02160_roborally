package content;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public enum CardImageEnum {

    CardAgain("src/main/resources/images/programming_cards/CardAgain.png"),
    CardBackUp("src/main/resources/images/programming_cards/CardBackUp.png"),
    CardMove1("src/main/resources/images/programming_cards/CardMove1.png"),
    CardMove2("src/main/resources/images/programming_cards/CardMove2.png"),
    CardMove3("src/main/resources/images/programming_cards/CardMove3.png"),
    CardPowerUp("src/main/resources/images/programming_cards/CardPowerUp.png"),
    CardTurnLeft("src/main/resources/images/programming_cards/CardTurnLeft.png"),
    CardTurnRight("src/main/resources/images/programming_cards/CardTurnRight.png"),
    CardUTurn("src/main/resources/images/programming_cards/CardUTurn.png");

    private final BufferedImage image;

    private CardImageEnum(String fileName) {
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

