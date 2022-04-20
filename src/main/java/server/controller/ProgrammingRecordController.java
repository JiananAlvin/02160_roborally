package server.controller;

import org.json.JSONObject;
import server.controller.ServerConnection;

/**
 * This class is used to connect to server and try to fetch and update data stored in collection "ProgrammingRecord"
 */
public class ProgrammingRecordController extends ServerConnection {

    public static final String RESPONSE_USER = "user";
    public static final String RESPONSE_ROOM = "room";
    public static final String RESPONSE_ROUND = "round";
    public static final String RESPONSE_REGISTER1 = "register1";
    public static final String RESPONSE_REGISTER2 = "register2";
    public static final String RESPONSE_REGISTER3 = "register3";
    public static final String RESPONSE_REGISTER4 = "register4";
    public static final String RESPONSE_REGISTER5 = "register5";

    public JSONObject getProgrammingRecords(String roomNumber, int round) {
        this.setPath("/getProgrammingRecords/" + roomNumber + "/" + round );
        this.setMethod("GET");
        this.connect();
        return this.getResponse();
    }

    public JSONObject createProgrammingRecord(String roomNumber, int round) {
        this.setPath("/getProgrammingRecords");
        this.setMethod("POST");
        this.connect();
        return this.getResponse();
    }

}