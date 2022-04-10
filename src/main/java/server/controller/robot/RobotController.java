package server.controller.robot;

import server.controller.ServerConnection;

public class RobotController extends ServerConnection {
    public void updatePosition(String username, int x, int y){
        this.setPath("/updateRobotPosition/" + username + "/" + x + "/" + y);
        this.setMethod("PUT");
        this.connect();
    }

    public void updateDirection(String username, String dir){
        this.setPath("/updateRobotDirection/" + username + "/" + dir);
        this.setMethod("PUT");
        this.connect();
    }

    public void getRobotInfo(String username){
        this.setPath("/getRobotInfo/" + username);
        this.setMethod("GET");
        this.connect();
    }

//    public static void main(String args[]){
//        RobotController rc = new RobotController();
//        rc.updatePosition("ion",111,111);
//        rc.getRobotInfo("ion");
//        System.out.println(rc.getResponse());
//    }
}
