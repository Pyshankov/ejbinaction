package ejb3inaction.example.pyshankov.Bean.BankService;

/**
 * Created by pyshankov on 12.11.15.
 */
public class BankException extends Exception {
    BankException(){};
    BankException(String str){
        super(str);
    }
    BankException(Throwable cause){
        super(cause);
    }
}
