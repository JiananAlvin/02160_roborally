package model.game;

import java.util.TimerTask;

public class Timer extends TimerTask {

    @Override
    public void run() {
        System.out.println("Notifier is sending message to tell everyone that the time is gone");
    }
}
