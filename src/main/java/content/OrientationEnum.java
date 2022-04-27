package content;

import java.util.SortedSet;

public enum OrientationEnum {
    N(0), S(180), E(90), W(270);

    private int angle;
    private int right, left;

    OrientationEnum(int angle) {
        this.angle = angle;
        this.right = angle + 90;
        this.left = angle - 90;
        this.normalizeAngles();
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
        return matchOrientation(this.right);
    }

    public OrientationEnum getLeft() {
        return matchOrientation(this.left);
    }

    public void normalizeAngles() {
        this.angle %= 360;
        if (this.angle < 0) {
            this.angle += 360;
        }
        this.right %= 360;
        if (this.right < 0) {
            this.right += 360;
        }
        this.left %= 360;
        if (this.left < 0) {
            this.left += 360;
        }
    }


}
