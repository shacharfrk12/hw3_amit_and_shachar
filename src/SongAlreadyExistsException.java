/**
 * Exception caused by trying to add a song that already exist (when only one instance of a song can be existed)
 */
public class SongAlreadyExistsException extends RuntimeException{
    public SongAlreadyExistsException(){}

    public SongAlreadyExistsException(String message){
        super(message);
    }
    public SongAlreadyExistsException(String message, Throwable cause){ super(message, cause);}

}
