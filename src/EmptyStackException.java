/**
 * Exception thrown when trying to get an element from an empty stack
 */
public class EmptyStackException extends StackException{
    public EmptyStackException(){}

    public EmptyStackException(String message){
        super(message);
    }
    public EmptyStackException(String message, Throwable cause){ super(message, cause);}
}
