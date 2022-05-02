package content;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public enum TileImageEnum {

    BLANK("/images/tiles/blank.png"),
    CHARGER("/images/tiles/charger.png"),
    CHECKPOINT1("/images/tiles/check_point1.png"),
    CHECKPOINT2("/images/tiles/check_point2.png"),
    CHECKPOINT3("/images/tiles/check_point3.png"),
    REBOOTPOINT("/images/tiles/reboot_point.png"),
    SOUTHONE("/images/tiles/south_one.png"),
    SOUTHTWO("/images/tiles/south_two.png"),
    WESTONE("/images/tiles/west_one.png"),
    WESTTWO("/images/tiles/west_two.png"),
    EASTONE("/images/tiles/east_one.png"),
    EASTTWO("/images/tiles/east_two.png"),
    NORTHONE("/images/tiles/north_one.png"),
    NORTHTWO("/images/tiles/north_two.png"),
    ANTENNA("/images/tiles/priority_antenna.png"),
    STARTPOINT("/images/tiles/start_point.png"),
    LASERHORIZONTAL("/images/tiles/laser_horizontal.png"),
    LASERVERTICAL("/images/tiles/laser_vertical.png"),
    WALLSOUTH("/images/tiles/wall_south.png"),
    WALLWEST("/images/tiles/wall_west.png"),
    WALLEAST("/images/tiles/wall_east.png"),
    WALLNORTH("/images/tiles/wall_north.png"),
    PIT("/images/tiles/pit.png"),
    OIL_STAIN("/images/tiles/oil_stain.png"),
    ROTATING_GEAR_CLOCKWISE("/images/tiles/rotating_gear_clockwise.png"),
    ROTATING_GEAR_COUNTERCLOCKWISE("/images/tiles/rotating_gear_counterclockwise.png");

    private final BufferedImage image;

    private TileImageEnum(String fileName) {
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
