package content;

public enum OrientationEnum {
    N(0), S(180), E(90), W(270);

    private final int angle;

    OrientationEnum(int angle) {
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }

    public static OrientationEnum matchOrientation(int angle) {
        switch (angle) {
            case 0:
                return N;
            case 90:
                return E;
            case 180:
                return S;
            case 270:
                return W;
        }
        return null;
    }

    public OrientationEnum getRight() {
        return matchOrientation((this.angle+90)%360);
    }

    public OrientationEnum getLeft() {
        return matchOrientation((this.angle+270)%360);
    }

    public OrientationEnum getUTurn() {
        return matchOrientation((this.angle+180)%360);
    }
}
