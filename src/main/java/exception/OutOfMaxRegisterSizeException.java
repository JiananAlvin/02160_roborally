package exception;

public class OutOfMaxRegisterSizeException extends Exception {
    private static final String CAN_NOT_ADD_MORE_CARDS = "Max register number is 5.  Current input number:";

    public OutOfMaxRegisterSizeException(int size) {
        super(CAN_NOT_ADD_MORE_CARDS + size);
    }
}
