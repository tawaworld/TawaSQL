package world.tawa.tawajdbc.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import world.tawa.tawajdbc.Config;
import world.tawa.tawajdbc.util.JdbcUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by tawa on 2018-12-15
 */
public class DefaultTransactionManager implements TransactionManager {
    //
    private static Logger logger = LoggerFactory.getLogger(DefaultTransactionManager.class);
    //
    private static ThreadLocal<ConnectionHelper> connectionHelperThreadLocal = new ThreadLocal<>();

    private Connection openConnection(String dataSourceIndex) throws SQLException {
        if (dataSourceIndex == null) dataSourceIndex = Config.DEFAULT_DATASOURCE_INDEX;
        DataSource dataSource = Config.getDataSource().get(dataSourceIndex);
        if (dataSource == null) {
            throw new world.tawa.tawajdbc.TawaSQLException("DataSource not found with index " + dataSourceIndex);
        }
        return dataSource.getConnection();
    }

    /**
     * Transaction get connection
     */
    @Override
    public Connection getConnection(String dataSourceIndex) {
        Connection conn;
        ConnectionHelper connectionHelper = connectionHelperThreadLocal.get();
        if (connectionHelper == null) {
            connectionHelper = new ConnectionHelper();
            connectionHelperThreadLocal.set(connectionHelper);
        }
        conn = connectionHelper.getConnection();
        if (conn == null) {
            try {
                conn = openConnection(dataSourceIndex);
                if (connectionHelper.isUseTransaction() == conn.getAutoCommit()) {
                    conn.setAutoCommit(!connectionHelper.isUseTransaction());
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            }
        }
        return conn;

    }

    /**
     * Transaction commit
     */
    @Override
    public void commit() {
        ConnectionHelper connectionHelper = connectionHelperThreadLocal.get();
        connectionHelperThreadLocal.set(null);
        if (connectionHelper == null || connectionHelper.getConnection() == null) {
            return;
        }
        if (connectionHelper.isUseTransaction()) { // Using Transaction
            try {
                connectionHelper.getConnection().commit();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                JdbcUtil.closeConnection(connectionHelper.getConnection());
            }
        }

    }

    /**
     * Transaction rollback
     */
    @Override
    public void rollback() {
        ConnectionHelper connectionHelper = connectionHelperThreadLocal.get();
        connectionHelperThreadLocal.set(null);
        if (connectionHelper == null || connectionHelper.getConnection() == null) {
            return;
        }
        if (connectionHelper.isUseTransaction()) { // Using Transaction
            try {
                connectionHelper.getConnection().rollback();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                JdbcUtil.closeConnection(connectionHelper.getConnection());
            }
        }
    }
}
