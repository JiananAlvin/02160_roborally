package gui.view.map;

public enum TileType {

    BLANK("src/main/resources/images/tiles/blank.png"),
    CHARGER("src/main/resources/images/tiles/charger.png"),
    CHECKPOINT1("src/main/resources/images/tiles/check_point1.png"),
    REBOOTPOINT("src/main/resources/images/tiles/reboot_point.png"),
    SOUTHTWO("src/main/resources/images/tiles/south_two.png"),
    WESTTWO("src/main/resources/images/tiles/west_two.png"),
    ANTENNA("src/main/resources/images/tiles/priority_antenna.png"),
    EASTONE("src/main/resources/images/tiles/east_one.png"),
    EASTTWO("src/main/resources/images/tiles/east_two.png"),
    STARTPOINT("src/main/resources/images/tiles/start_point.png"),
    NORTHTWO("src/main/resources/images/tiles/north_two.png"),
    WALLSOUTH("src/main/resources/images/tiles/wall_south.png"),
    WALLSOUTHLASER("src/main/resources/images/tiles/wall_south_laser.png"),
    WALLWEST("src/main/resources/images/tiles/wall_west.png"),
    WALLWESTLASER("src/main/resources/images/tiles/wall_west_laser.png"),
    WALLEAST("src/main/resources/images/tiles/wall_east.png"),
    WALLEASTLASER("src/main/resources/images/tiles/wall_east_laser.png"),
    WALLNORTH("src/main/resources/images/tiles/wall_north.png"),
    WALLNORTHLASER("src/main/resources/images/tiles/wall_north_laser.png");

    private String pictureFile;

    private TileType(String pictureFile) {
        this.pictureFile = pictureFile;
    }

    public String getPictureFile() {
        return pictureFile;
    }
}
