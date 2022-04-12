package server.controller.robot;

import org.json.JSONObject;
import server.controller.ServerConnection;

public class RobotController extends ServerConnection {

    public JSONObject updatePosition(String username, int x, int y){
        this.setPath("/updateRobotPosition/" + username + "/" + x + "/" + y);
        this.setMethod("PUT");
        this.connect();
        return this.getResponse();
    }

    public JSONObject updateDirection(String username, String dir){
        this.setPath("/updateRobotDirection/" + username + "/" + dir);
        this.setMethod("PUT");
        this.connect();
        return this.getResponse();
    }

    public JSONObject getRobotInfo(String username){
        this.setPath("/getRobotInfo/" + username);
        this.setMethod("GET");
        this.connect();
        return this.getResponse();
    }

    public JSONObject deleteRobotInfo(String username){
        this.setPath("/deleteRobot/" + username);
        this.setMethod("DELETE");
        this.connect();
        return this.getResponse();
    }
}
