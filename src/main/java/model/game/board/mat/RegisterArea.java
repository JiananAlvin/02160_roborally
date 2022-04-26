package model.game.board.mat;

import com.sun.jdi.PathSearchingVirtualMachine;
import exception.OutOfMaxRegisterSizeException;
import io.cucumber.java.bs.A;
import lombok.Data;
import model.game.board.map.element.Robot;
import model.game.card.Card;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Data
public class RegisterArea {
    private ArrayList<Card> registers;
    public static final int REGISTER_QUEUE_SIZE = 5;
    private static final String FULLY_QUALIFIED_NAME_OF_PROGRAMMING = "model.game.card.programming.";

    public RegisterArea() {
        this.registers = new ArrayList<>();
    }

    /**
     * The names of programming cards in five registers are stored in an arraylist in order when the user has finished
     * programming.
     * <p> This method converts the arraylist of cards' names to the arraylist of {@code Card} elements that is {@code registers}.
     * @param cardNames the arraylist of names of cards in the 5 finished registers.
     */
    public void addCard(ArrayList<String> cardNames) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (String cardName : cardNames) {
            this.registers.add(createCardInstance(cardName));
        }
    }

    /**
     * @param index an integer n
     * @return the {@code Card} instance in the nth register.
     */
    public Card getCard(int index) throws OutOfMaxRegisterSizeException {
        if (index < REGISTER_QUEUE_SIZE)
            return this.registers.get(index);
        else throw new OutOfMaxRegisterSizeException(index);
    }

    /**
     * A player who is not finished programming must randomly fill the unfinished registers with the programming
     * cards still in his hand.
     * @param namesOfCardsInRegisters the arraylist of names of cards in the finished registers.
     * @param namesOfRemainingCards the arraylist of names of cards still in the player's hand.
     */
    public void addRandomCard(ArrayList<String> namesOfCardsInRegisters, ArrayList<String> namesOfRemainingCards) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for (String cardName : namesOfCardsInRegisters) {
            if (cardName.equals("null")) {
                this.registers.add(this.createCardInstance(namesOfRemainingCards.remove(new Random().nextInt(namesOfRemainingCards.size()))));
            } else {
                this.registers.add(createCardInstance(cardName));
            }
        }
    }

    /**
     * Each string of card name corresponds to a simple name of card class.
     * <p>This method creates a {@code card} instance corresponding to the passed card name.
     * @param cardName A string of card name
     * @return the corresponding {@code Card} instance
     */
    private Card createCardInstance(String cardName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Class<?> clz = Class.forName(FULLY_QUALIFIED_NAME_OF_PROGRAMMING + cardName);
        return (Card) clz.getConstructor().newInstance();
    }
}
