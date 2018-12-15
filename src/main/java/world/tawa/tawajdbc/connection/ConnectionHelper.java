package world.tawa.tawajdbc.connection;

import java.sql.Connection;

/**
 * Created by tawa on 2018-12-15
 */
public class ConnectionHelper {

    private Connection connection;

    private boolean useTransaction;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setUseTransaction(boolean useTransaction) {
        this.useTransaction = useTransaction;
    }

    public boolean isUseTransaction(){
        return useTransaction;
    }
}
