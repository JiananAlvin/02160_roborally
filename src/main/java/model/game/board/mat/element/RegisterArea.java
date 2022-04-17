package model.game.board.mat.element;

import exception.OutOfMaxRegisterSizeException;
import io.cucumber.java.bs.A;
import model.game.card.Card;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class RegisterArea {
    private ArrayList<Card> registers;
    private static final int REGISTER_QUEUE_SIZE = 5;

    public RegisterArea() {
        this.registers = new ArrayList<>();
    }

    public void addCard(Card card) throws OutOfMaxRegisterSizeException {
        if (registers.size() >= REGISTER_QUEUE_SIZE) throw new OutOfMaxRegisterSizeException(registers.size());
        else this.registers.add(card);
    }

    public Card getCard(int index) throws OutOfMaxRegisterSizeException {
        if (index < REGISTER_QUEUE_SIZE)
            return this.registers.get(index);
        else throw new OutOfMaxRegisterSizeException(index);
    }


}
