package repo;

public class RepositoryException extends Exception{
    private String custom_mesage;
    public RepositoryException(String custom_message){
        this.custom_mesage=custom_message;
    }
    public String getMessage(){
        return this.custom_mesage;
    }
}
