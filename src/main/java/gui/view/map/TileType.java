package gui.view.map;

public enum TileType {

    BLANK("src/main/resources/images/tiles/blank.png"),
    CHARGER("src/main/resources/images/tiles/charger.png"),
    CHECK_POINT1("src/main/resources/images/tiles/check_point1.png"),
    REBOOT_POINT("src/main/resources/images/tiles/reboot_point.png"),
    SOUTH_TWO("src/main/resources/images/tiles/south_two.png"),
    WEST_TWO("src/main/resources/images/tiles/west_two.png"),
    PRIORITY_ANTENNA("src/main/resources/images/tiles/priority_antenna.png"),
    EAST_ONE("src/main/resources/images/tiles/east_one.png"),
    EAST_TWO("src/main/resources/images/tiles/east_two.png"),
    START_POINT("src/main/resources/images/tiles/start_point.png"),
    NORTH_TWO("src/main/resources/images/tiles/north_two.png"),
    WALL_SOUTH("src/main/resources/images/tiles/wall_south.png"),
    WALL_SOUTH_LASER("src/main/resources/images/tiles/wall_south_laser.png"),
    WALL_WEST("src/main/resources/images/tiles/wall_west.png"),
    WALL_WEST_LASER("src/main/resources/images/tiles/wall_west_laser.png"),
    WALL_EAST("src/main/resources/images/tiles/wall_east.png"),
    WALL_EAST_LASER("src/main/resources/images/tiles/wall_east_laser.png"),
    WALL_NORTH("src/main/resources/images/tiles/wall_north.png"),
    WALL_NORTH_LASER("src/main/resources/images/tiles/wall_north_laser.png");

    private String pictureFile;

    private TileType(String pictureFile) {
        this.pictureFile = pictureFile;
    }

    public String getPictureFile() {
        System.out.println("pictureFile:"+pictureFile);
        return pictureFile;
    }
}
