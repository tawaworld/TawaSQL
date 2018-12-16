package world.tawa.tawajdbc;

import java.sql.ResultSet;

/**
 * Created by tawa on 2018-12-16
 */
public interface ResultSetHandler<T> {

    T handlerRow(ResultSet rs) throws Exception;
}
