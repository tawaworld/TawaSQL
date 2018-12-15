package world.tawa.tawajdbc;

/**
 * Created by tawa on 2018-12-15
 */
public class TawaJdbcException extends RuntimeException {

    public TawaJdbcException() {
        super();
    }

    public TawaJdbcException(Throwable t) {
        super(t);
    }

    public TawaJdbcException(String s) {
        super(s);
    }

    public TawaJdbcException(String msg, Throwable t) {
        super(msg, t);
    }

}
