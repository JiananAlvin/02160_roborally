package content;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public enum TileImageEnum {

    BLANK("src/main/resources/images/tiles/blank.png"),
    CHARGER("src/main/resources/images/tiles/charger.png"),
    CHECKPOINT1("src/main/resources/images/tiles/check_point1.png"),
    CHECKPOINT2("src/main/resources/images/tiles/check_point2.png"),
    CHECKPOINT3("src/main/resources/images/tiles/check_point3.png"),
    REBOOTPOINT("src/main/resources/images/tiles/reboot_point.png"),
    SOUTHONE("src/main/resources/images/tiles/south_one.png"),
    SOUTHTWO("src/main/resources/images/tiles/south_two.png"),
    WESTONE("src/main/resources/images/tiles/west_one.png"),
    WESTTWO("src/main/resources/images/tiles/west_two.png"),
    EASTONE("src/main/resources/images/tiles/east_one.png"),
    EASTTWO("src/main/resources/images/tiles/east_two.png"),
    NORTHONE("src/main/resources/images/tiles/north_one.png"),
    NORTHTWO("src/main/resources/images/tiles/north_two.png"),
    ANTENNA("src/main/resources/images/tiles/priority_antenna.png"),
    STARTPOINT("src/main/resources/images/tiles/start_point.png"),
    LASERHORIZONTAL("src/main/resources/images/tiles/laser_horizontal.png"),
    LASERVERTICAL("src/main/resources/images/tiles/laser_vertical.png"),
    WALLSOUTH("src/main/resources/images/tiles/wall_south.png"),
    WALLWEST("src/main/resources/images/tiles/wall_west.png"),
    WALLEAST("src/main/resources/images/tiles/wall_east.png"),
    WALLNORTH("src/main/resources/images/tiles/wall_north.png"),
    PIT("src/main/resources/images/tiles/pit.png"),
    OIL_STAIN("src/main/resources/images/tiles/oil_stain.png"),
    ROTATING_GEAR("src/main/resources/images/tiles/rotating_gear.png");

    private final BufferedImage image;

    private TileImageEnum(String fileName) {
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
