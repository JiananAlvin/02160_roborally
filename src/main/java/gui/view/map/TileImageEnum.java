package gui.view.map;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public enum TileImageEnum {

    BLANK("src/main/resources/images/tiles/blank.png"),
    CHARGER("src/main/resources/images/tiles/charger.png"),
    CHECKPOINT1("src/main/resources/images/tiles/check_point1.png"),
    //    TODO change it to checkpoint2 image
    CHECKPOINT2("src/main/resources/images/tiles/check_point1.png"),
    //    TODO change it to checkpoint3 image
    CHECKPOINT3("src/main/resources/images/tiles/check_point1.png"),
    REBOOTPOINT("src/main/resources/images/tiles/reboot_point.png"),
    // TODO change the path
    SOUTHONE("src/main/resources/images/tiles/south_two.png"),
    SOUTHTWO("src/main/resources/images/tiles/south_two.png"),
    // TODO change the path
    WESTONE("src/main/resources/images/tiles/west_two.png"),
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
    ARROW("src/main/resources/images/robots/arrow.png");

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
