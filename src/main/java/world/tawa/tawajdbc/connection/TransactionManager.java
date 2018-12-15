package world.tawa.tawajdbc.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by tawa on 2018-12-15
 */
public interface TransactionManager {

    Connection getConnection(String dataSourceIndex);

    void commit();

    void rollback();
}
