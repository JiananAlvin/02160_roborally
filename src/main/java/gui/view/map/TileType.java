package gui.view.map;

public enum TileType {

    BLANK("images/map_elements/tiles/blank.png"),
    CHARGER("images/map_elements/tiles/charger.png"),
    CHECK_POINT1("images/map_elements/tiles/check_point1.png"),
    REBOOT_POINT("images/map_elements/tiles/check_point1.png"),
    SOUTH_TWO("images/map_elements/tiles/south_two.png"),
    WEST_TWO("images/map_elements/tiles/west_two.png"),
    PRIORITY_ANTENNA("images/map_elements/tiles/priority_antenna.png"),
    EAST_ONE("images/map_elements/tiles/east_one.png"),
    EAST_TWO("images/map_elements/tiles/east_two.png"),
    START_POINT("images/map_elements/tiles/start_point.png"),
    NORTH_TWO("images/map_elements/tiles/north_two.png"),
    WALL_SOUTH("images/map_elements/tiles/wall_south.png"),
    WALL_SOUTH_LASER("images/map_elements/tiles/wall_south_laser.png"),
    WALL_WEST("images/map_elements/tiles/wall_west.png"),
    WALL_WEST_LASER("images/map_elements/tiles/wall_west_laser.png"),
    WALL_EAST("images/map_elements/tiles/wall_east.png"),
    WALL_EAST_LASER("images/map_elements/tiles/wall_east_laser.png"),
    WALL_NORTH("images/map_elements/tiles/wall_north.png"),
    WALL_NORTH_LASER("images/map_elements/tiles/wall_north_laser.png");

    private String pictureFile;

    private TileType(String pictureFile) {
        this.pictureFile = pictureFile;
    }

    public String getPictureFile() {
        return pictureFile;
    }
}
