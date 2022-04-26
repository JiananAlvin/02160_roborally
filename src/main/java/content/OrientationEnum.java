package content;

public enum OrientationEnum {
    N(0), S(180), E(90), W(270);

    private int angle;

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
            default:
                return null;
        }
    }

    public OrientationEnum getOpposite() {
        this.angle += 180;
        minimizeAngle();
        return matchOrientation(angle);
    }

    private void minimizeAngle() {
        if (this.angle >= 360) {
            this.angle -= 360;
        }
    }
}
