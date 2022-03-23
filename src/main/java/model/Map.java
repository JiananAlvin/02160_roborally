package model;

public class Map {
    public static final String MAP1_NAME = "map1";
    public static final String MAP2_NAME = "map2";
    String name;
    String content;

    public Map(String name) {
        this.name = name;
        if (this.name.equals(MAP1_NAME))
            this.content = "MAP1_CONTENT";
        else if (this.name.equals(MAP2_NAME))
            this.content = "MAP2_CONTENT";
        else this.content = "ERROR_MAP_NUM";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
