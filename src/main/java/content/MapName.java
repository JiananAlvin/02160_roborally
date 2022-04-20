package content;

public enum MapName {

    STARTER("STARTER"), BEGINNER("BEGINNER"),
    INTERMEDIATE("INTERMEDIATE"),ADVANCED("ADVANCED");
    private String mapName;
    MapName(String mapName) {
        this.mapName = mapName;
    }
    public String getMapName() {
        return mapName;
    }
//    public static final String STARTER ="STARTER";
//    public static final String BEGINNER = "BEGINNER";
//    public static final String INTERMEDIATE = "INTERMEDIATE";
//    public static final String ADVANCED = "ADVANCED";
    }
