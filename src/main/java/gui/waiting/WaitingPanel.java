package gui.waiting;


import controller.game.GameManager;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


@EqualsAndHashCode(callSuper = true)
@Data
public class WaitingPanel extends JPanel {

    public WaitingPanel(String roomNumberStr, String signal, JFrame frame, String userName) {
        JToggleButton btQuit;
        JLabel lblRoomNumber;
        if (Objects.equals(signal, "owner")) {
            lblRoomNumber = new JLabel("Room Number: " + roomNumberStr);
            lblRoomNumber.setFont(new Font("Calibri", Font.BOLD, 20));
            JToggleButton btStart = new JToggleButton("Start");
            btQuit = new JToggleButton("Quit");
            GameManager.getInstance().loadParticipantsTable(userName, roomNumberStr, signal, this);

            this.setLayout(null);
            lblRoomNumber.setBounds(100, 28, 780, 20);
            btStart.setBounds(100, 270, 80, 30);
            btQuit.setBounds(300, 270, 80, 30);
            this.add(lblRoomNumber);
            this.add(btStart);
            this.add(btQuit);
            btQuit.addActionListener(e -> {
                GameManager.getInstance().quitRoomForRoomOwner(roomNumberStr);
            });

            btStart.addActionListener(e -> {
                btStart.setEnabled(false);
                GameManager.getInstance().startGameForRoomOwner(roomNumberStr, userName, signal);
            });
        } else {
            lblRoomNumber = new JLabel("Room Number: " + roomNumberStr);
            lblRoomNumber.setFont(new Font("Calibri", Font.BOLD, 20));
            btQuit = new JToggleButton("Quit");
            JLabel lblTip = new JLabel("Please wait for the owner to start the game.");
            lblTip.setFont(new Font("Calibri", Font.PLAIN, 15));
            GameManager.getInstance().loadParticipantsTable(userName, roomNumberStr, signal, this);
            this.setLayout(null);
            lblRoomNumber.setBounds(100, 28, 780, 20);
            btQuit.setBounds(100, 270, 80, 30);
            lblTip.setBounds(300, 275, 580, 30);
            this.add(lblRoomNumber);
            this.add(btQuit);
            this.add(lblTip);
            btQuit.addActionListener(e -> {
                GameManager.getInstance().quitRoomForParticipants(userName);
            });
        }
    }

}