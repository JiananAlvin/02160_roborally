package model.game.board.mat.element;

import com.sun.jdi.PathSearchingVirtualMachine;
import exception.OutOfMaxRegisterSizeException;
import io.cucumber.java.bs.A;
import model.game.board.map.element.Robot;
import model.game.card.Card;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class RegisterArea {
    private ArrayList<Card> registers;
    private static final int REGISTER_QUEUE_SIZE = 5;
    private static final String FULLY_QUALIFIED_NAME_OF_PROGRAMMING = "model.game.card.programming.";

    public RegisterArea() {
        this.registers = new ArrayList<>();
    }

    /**
     * The names of programming cards in five registers are stored in an arraylist in order when the user has finished
     * programming. In this arraylist of strings, each string corresponds to a simple name of card class.
     * <p> This method converts the arraylist of cards' names to the ArrayList&lt;Card&gt that is registers.
     * @param cardNames the arraylist of cards' names.
     */
    public void addCard(ArrayList<String> cardNames) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (String cardName : cardNames) {
            Class<?> clz = Class.forName(FULLY_QUALIFIED_NAME_OF_PROGRAMMING + cardName);
            this.registers.add((Card) clz.getConstructor().newInstance());
        }
    }

    /**
     * @param index n
     * @return the Card object in the nth register
     */
    public Card getCard(int index) throws OutOfMaxRegisterSizeException {
        if (index < REGISTER_QUEUE_SIZE)
            return this.registers.get(index);
        else throw new OutOfMaxRegisterSizeException(index);
    }

    public void addRandomCard(ArrayList<String> cardNames, ArrayList<Card> remainingCardsInHand) {

    }
//    public static void main(String[] args) {
//
//    }
}
