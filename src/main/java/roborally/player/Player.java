package roborally.player;

public class Player {
    String name;
    boolean isPlaying;


    public void setName(String name) {
        this.name = name;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public Player() {
        this.setPlaying(true);
    }

    public Player(String name, boolean isPlaying) {
        this.name = name;
        this.isPlaying = isPlaying;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player p = (Player) obj;
            return p.name.equals(this.name);
        }
        return false;
    }
}
