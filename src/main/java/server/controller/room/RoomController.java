package server.controller.room;

import org.json.JSONObject;
import server.controller.ServerConnection;

public class RoomController extends ServerConnection {

    public JSONObject createRoom(String owner, String map){
        this.setPath("/createRoom/"+ owner + "/" + map);
        this.setMethod("POST");
        this.connect();
        return this.getResponse();
    }

    public JSONObject roomInfo(int roomNumber){
        this.setPath("/roomInfo/" + roomNumber);
        this.setMethod("GET");
        this.connect();
        return this.getResponse();
    }

    public JSONObject deleteRoom(int roomNumber){
        this.setPath("/deleteRoom/" + roomNumber);
        this.setMethod("DELETE");
        this.connect();
        return this.getResponse();
    }
}
