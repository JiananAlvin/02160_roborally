package server.controller.room;

import server.controller.ServerConnection;

public class RoomController extends ServerConnection {

    public void createRoom(String owner, String map){
        this.setPath("/createRoom/"+ owner + "/" + map);
        this.setMethod("POST");
        this.connect();
    }

    public void roomInfo(int roomNumber){
        this.setPath("/roomInfo/" + roomNumber);
        this.setMethod("GET");
        this.connect();
    }

    public void deleteRoom(int roomNumber){
        this.setPath("/deleteRoom/" + roomNumber);
        this.setMethod("DELETE");
        this.connect();
    }


//    public static void main(String args[]){
//        RoomController rc = new RoomController();
////        rc.createRoom("fd", "test");
////        System.out.println(rc.getResponse());
//        rc.roomInfo(100);
//        System.out.println(rc.getResponse());
//    }

}
