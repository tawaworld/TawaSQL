package world.tawa.tawajdbc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by tawa on 2018-12-15
 */
public class JdbcUtil {
    //
    private static Logger logger = LoggerFactory.getLogger(JdbcUtil.class);

    /**
     * @param conn The connection you want to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.debug("Could not close JDBC Connection", e);
            } catch (Throwable t) {
                logger.debug("Unexpected exception on closing JDBC Connection", t);
            } finally {
                if (logger.isDebugEnabled()) {
                    logger.debug("Close Connection:{}", conn);
                }
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.trace("Could not close JDBC Statement", e);
            } catch (Throwable t) {
                logger.trace("Unexpected exception on Closing JDBC Statement", t);
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.trace("Could not close JDBC ResultSet", e);
            } catch (Throwable t) {
                logger.trace("Unexpected exception on Closing JDBC ResultSet", t);
            }
        }
    }

    public static void closeStatementAndResultSet(Statement statement, ResultSet rs){
        closeStatement(statement);
        closeResultSet(rs);
    }

}
