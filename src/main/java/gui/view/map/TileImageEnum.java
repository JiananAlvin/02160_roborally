package gui.view.map;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public enum TileImageEnum {

    BLANK(1, "src/main/resources/images/tiles/blank.png"),
    CHARGER(2, "src/main/resources/images/tiles/charger.png"),
    CHECKPOINT1(3, "src/main/resources/images/tiles/check_point1.png"),
    REBOOTPOINT(4, "src/main/resources/images/tiles/reboot_point.png"),
    SOUTHTWO(5, "src/main/resources/images/tiles/south_two.png"),
    WESTTWO(6, "src/main/resources/images/tiles/west_two.png"),
    ANTENNA(7, "src/main/resources/images/tiles/priority_antenna.png"),
    EASTONE(8, "src/main/resources/images/tiles/east_one.png"),
    EASTTWO(9, "src/main/resources/images/tiles/east_two.png"),
    STARTPOINT(10, "src/main/resources/images/tiles/start_point.png"),
    NORTHTWO(11, "src/main/resources/images/tiles/north_two.png"),
    WALLSOUTH(12, "src/main/resources/images/tiles/wall_south.png"),
    WALLSOUTHLASER(13, "src/main/resources/images/tiles/wall_south_laser.png"),
    WALLWEST(14, "src/main/resources/images/tiles/wall_west.png"),
    WALLWESTLASER(15, "src/main/resources/images/tiles/wall_west_laser.png"),
    WALLEAST(16, "src/main/resources/images/tiles/wall_east.png"),
    WALLEASTLASER(17, "src/main/resources/images/tiles/wall_east_laser.png"),
    WALLNORTH(18, "src/main/resources/images/tiles/wall_north.png"),
    WALLNORTHLASER(19, "src/main/resources/images/tiles/wall_north_laser.png"),
    NORTHONE(20, "src/main/resources/images/tiles/north_one.png"),
    ARROW(21,"src/main/resources/images/robots/arrow.png");

    private final BufferedImage image;
    private final int identity;


    private TileImageEnum(int identity, String fileName) {
        // first assign to tempImage to meet teh final modifier of image
        BufferedImage tempImage;
        try {
            tempImage = ImageIO.read(new File(fileName));
        } catch (Exception e) {
            tempImage = null;
        }
        this.image = tempImage;
        this.identity = identity;
    }

    public BufferedImage getImage() {
        return this.image;
    }
}
