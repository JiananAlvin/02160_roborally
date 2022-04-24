package content;

public enum MapName {

    STARTER("STARTER"),
    BEGINNER("BEGINNER"),
    INTERMEDIATE("INTERMEDIATE"),
    ADVANCED("ADVANCED");

    private final String mapName;

    private MapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }
}