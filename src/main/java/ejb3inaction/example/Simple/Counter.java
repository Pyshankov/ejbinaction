package ejb3inaction.example.Simple;

import javax.ejb.Remove;
import javax.ejb.Stateful;

/**
 * Created by pyshankov on 04.11.15.
 */
@Stateful
public class Counter {

    private int count = 0;

    public int count() {
        return count;
    }

    public int increment() {
        return ++count;
    }

    public int reset() {
        return (count = 0);
    }
    @Remove
    public void remove(){
    };
}