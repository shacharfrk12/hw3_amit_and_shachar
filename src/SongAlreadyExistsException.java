public class SongAlreadyExistsException extends RuntimeException{
    public SongAlreadyExistsException(){}

    public SongAlreadyExistsException(String message){
        super(message);
    }
}
