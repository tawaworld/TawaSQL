package world.tawa.sql;

/**
 * Created by tawa on 2018-12-15
 */
public class TawaSQLException extends RuntimeException {

    public TawaSQLException() {
        super();
    }

    public TawaSQLException(Throwable t) {
        super(t);
    }

    public TawaSQLException(String s) {
        super(s);
    }

    public TawaSQLException(String msg, Throwable t) {
        super(msg, t);
    }

}
