package content;

public enum MapNameEnum {

    STARTER("STARTER"),
    BEGINNER("BEGINNER"),
    INTERMEDIATE("INTERMEDIATE"),
    ADVANCED("ADVANCED");

    private final String mapName;

    private MapNameEnum(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }
}