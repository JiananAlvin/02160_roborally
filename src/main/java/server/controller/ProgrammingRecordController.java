package server.controller;

import model.Game;
import org.json.JSONObject;

/**
 * This class is used to connect to server and try to fetch and update data stored in collection "ProgrammingRecord"
 */
public class ProgrammingRecordController extends ServerConnection {

    public static final String RESPONSE_USER = "username";
    public static final String RESPONSE_ROOM = "room";
    public static final String RESPONSE_ROUND = "round";
    public static final String RESPONSE_REGISTER1 = "register1";
    public static final String RESPONSE_REGISTER2 = "register2";
    public static final String RESPONSE_REGISTER3 = "register3";
    public static final String RESPONSE_REGISTER4 = "register4";
    public static final String RESPONSE_REGISTER5 = "register5";
    public static final String RESPONSE_PROGRAMMING_RECORDS = "response";

    public JSONObject getProgrammingRecords(int roomNumber, int round) {
        this.setPath("/getProgrammingRecords/" + roomNumber + "/" + round);
        this.setMethod("GET");
        this.connect();
        return this.getResponse();
    }

    public JSONObject createProgrammingRecord(String userName, int roomNumber, int round, String register1, String register2, String register3, String register4, String register5) {
        this.setPath("/createProgrammingRecord");
        JSONObject body = new JSONObject();
        body.put("username", userName);
        body.put("roomNumber", roomNumber + "");
        body.put("round", round + "");
        body.put("register1", register1);
        body.put("register2", register2);
        body.put("register3", register3);
        body.put("register4", register4);
        body.put("register5", register5);
        this.postRequestWithBody(body);
        return this.getResponse();
    }

//    public static void main(String[] args) {
//    }

}