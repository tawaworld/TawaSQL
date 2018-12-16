package world.tawa.tawajdbc.connection;

import world.tawa.tawajdbc.TawaJdbcException;

import java.sql.Connection;

/**
 * Created by tawa on 2018-12-15
 */
public class ConnectionManager {
    //
    private static TransactionManager transactionManager = new DefaultTransactionManager();


    public static void setTransactionManager(TransactionManager transactionManager) {
        ConnectionManager.transactionManager = transactionManager;
    }

    public static TransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * get connection by data source index
     */
    public static Connection getConnection(String dataSourceIndex){
        if(transactionManager == null){
            throw new TawaJdbcException("transactionManager is null");
        }
        return transactionManager.getConnection(dataSourceIndex);
    }

    /**
     * Transaction commit
     */
    public static void commit(){
        transactionManager.commit();
    }

    /**
     * Transaction rollback
     */
    public static void rollback(){
        transactionManager.rollback();
    }
}
