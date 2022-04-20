package server.controller.room;

import org.json.JSONObject;
import server.controller.ServerConnection;

public class RoomController extends ServerConnection {
    public static final String RESPONSE_ROOM_NUMBER = "room_number";
    public static final String RESPONSE_USERS_IN_ROOM = "users";
    public static final String RESPONSE_ROOM_OWNER = "owner";
    public static final String RESPONSE_REQUEST_TIME = "requestTime";

    public JSONObject createRoom(String owner, String map) {
        this.setPath("/createRoom/" + owner + "/" + map);
        this.setMethod("POST");
        this.connect();
        return this.getResponse();
    }

    public JSONObject roomInfo(int roomNumber) {
        this.setPath("/roomInfo/" + roomNumber);
        this.setMethod("GET");
        this.connect();
        return this.getResponse();
    }

    public JSONObject deleteRoom(int roomNumber) {
        this.setPath("/deleteRoom/" + roomNumber);
        this.setMethod("DELETE");
        this.connect();
        return this.getResponse();
    }
}
