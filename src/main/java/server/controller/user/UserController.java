package server.controller.user;

import server.controller.ServerConnection;

public class UserController extends ServerConnection {


    public void createUser(String username){
        this.setPath("/createUser/" + username);
        this.setMethod("POST");
        this.connect();
    }

    public void joinRoom(String username, int roomNumber){
        this.setPath("/joinRoom/" + username + "/" + roomNumber);
        this.setMethod("PUT");
        this.connect();
    }

    public void exitRoom(String username){
        this.setPath("/exitRoom/" + username);
        this.setMethod("PUT");
        this.connect();
    }

    public void chooseRobot(String username, String robotName){
        this.setPath("/chooseRobot/" + username + "/" + robotName);
        this.setMethod("PUT");
        this.connect();
    }


//    public static void main(String args[]){
//        UserController userController = new UserController();
////        userController.createUser("newuse");
////        userController.joinRoom("newusefff", 100);
////        userController.exitRoom("newusefff");
//        System.out.println(userController.getResponse());
//    }

}
