/**
 * Exception caused by trying to add an element to stack when there is no space to add it
 */
public class StackOverflowException extends StackException{
    public StackOverflowException(){}

    public StackOverflowException(String message){
        super(message);
    }
    public StackOverflowException(String message, Throwable cause){ super(message, cause);}
}
