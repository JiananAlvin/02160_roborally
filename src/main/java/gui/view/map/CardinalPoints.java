package gui.view.map;

public enum CardinalPoints {

    N(0), S(180), E(90), W(270);

    private int angle;

    private CardinalPoints(int angle) {
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }
}

