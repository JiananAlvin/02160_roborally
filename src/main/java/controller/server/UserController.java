package controller.server;

import org.json.JSONObject;

public class UserController extends ServerConnection {

    public JSONObject createUser(String username){
        this.setPath("/createUser/" + username);
        this.setMethod("POST");
        this.connect();
        return this.getResponse();
    }

    public JSONObject joinRoom(String username, int roomNumber){
        this.setPath("/joinRoom/" + username + "/" + roomNumber);
        this.setMethod("PUT");
        this.connect();
        return this.getResponse();
    }

    public JSONObject exitRoom(String username){
        this.setPath("/exitRoom/" + username);
        this.setMethod("PUT");
        this.connect();
        return this.getResponse();
    }

    public JSONObject chooseRobot(String username, String robotName){
        this.setPath("/chooseRobot/" + username + "/" + robotName);
        this.setMethod("PUT");
        this.connect();
        return this.getResponse();
    }

    public JSONObject deleteUser(String username){
        this.setPath("/deleteUser/" + username );
        this.setMethod("DELETE");
        this.connect();
        return this.getResponse();
    }
}
