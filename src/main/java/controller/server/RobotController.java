package controller.server;

import org.json.JSONObject;

public class RobotController extends ServerConnection {

    public static final String RESPONSE_ROBOT_NAME = "name";
    public static final String RESPONSE_ROBOT_XCOORD = "x";
    public static final String RESPONSE_ROBOT_YCOORD = "y";

    public JSONObject updatePosition(String username, int x, int y) {
        this.setPath("/updateRobotPosition/" + username + "/" + x + "/" + y);
        this.setMethod("PUT");
        this.connect();
        return this.getResponse();
    }

    public JSONObject getRobotInfo(String username) {
        this.setPath("/getRobotInfo/" + username);
        this.setMethod("GET");
        this.connect();
        return this.getResponse();
    }

    public JSONObject deleteRobotInfo(String username) {
        this.setPath("/deleteRobot/" + username);
        this.setMethod("DELETE");
        this.connect();
        return this.getResponse();
    }
}
