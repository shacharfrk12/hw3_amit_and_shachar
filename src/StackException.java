/**
 * Exception caused by something connected to a stack
 */
public class StackException extends RuntimeException{
    public StackException(){}

    public StackException(String message){
        super(message);
    }
    public StackException(String message, Throwable cause){ super(message, cause);}

}
