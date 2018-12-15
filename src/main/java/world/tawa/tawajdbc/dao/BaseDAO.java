package world.tawa.tawajdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import world.tawa.tawajdbc.Config;
import world.tawa.tawajdbc.SqlInterceptor;
import world.tawa.tawajdbc.TawaJdbcException;
import world.tawa.tawajdbc.connection.ConnectionManager;
import world.tawa.tawajdbc.util.DumpUtil;
import world.tawa.tawajdbc.util.JdbcUtil;

import java.sql.*;

/**
 * Created by tawa on 2018-12-15
 */
public abstract class BaseDAO {
    //
    private static Logger logger = LoggerFactory.getLogger(BaseDAO.class);
    //
    private String dataSourceIndex;
    //
    private static final long SLOW_SQL_MINIMUM_SPEND_TIME = 1000; // 1s


    /**
     * before execute interceptor
     */
    private void beforeExecute(String sql, Object... parameters) {
        for (SqlInterceptor sqlInterceptor : Config.getSqlInterceptors()) {
            sqlInterceptor.beforeExecute(sql, parameters);
        }
    }

    /**
     * after execute interceptor
     */
    private void afterExecute(String sql, Object... parameters) {
        for (SqlInterceptor sqlInterceptor : Config.getSqlInterceptors()) {
            sqlInterceptor.afterExecute(sql, parameters);
        }
    }

    private String dumpParameters(Object[] parameters) {
        return DumpUtil.dump(parameters);
    }

    private int executeUpdate0(String sql, boolean returnAutoGeneratedKeys, Object... parameters) {
        int result = -1;
        long startTime = System.currentTimeMillis();
        long spendTime = 0;
        boolean isException = false;
        try {
            beforeExecute(sql, parameters);
            Connection conn = ConnectionManager.getConnection(dataSourceIndex);
            PreparedStatement ps = conn.prepareStatement(sql, returnAutoGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
            int index = 1;
            if (parameters != null) {
                for (Object parameter : parameters) {
                    ps.setObject(index++, parameter);
                }
            }
            result = executeUpdate(conn, ps, returnAutoGeneratedKeys);
            spendTime = System.currentTimeMillis() - startTime;
            return result;
        } catch (Throwable t) {
            isException = true;
            throw new TawaJdbcException(t.getMessage(), t);
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info("executeUpdate \nisException:{} \nspendTime:{} \nresult:{} \nparameters:{}\n{}"
                        , isException, spendTime, result, parameters == null ? 0 : parameters.length, dumpParameters(parameters));
            }
            afterExecute(sql, parameters);
            // TODO
        }
    }

    private int executeUpdate(Connection conn, PreparedStatement ps, boolean returnAutoGeneratedKeys) throws SQLException {
        ResultSet rs = null;
        int rowCount = ps.executeUpdate();
        try {
            if (returnAutoGeneratedKeys) {
                int autoIncrease = -1;
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    autoIncrease = rs.getInt(1);
                }
                return autoIncrease;
            }
            return rowCount;
        } catch (SQLException e) {
            throw e;
        } finally {
            JdbcUtil.closeStatementAndResultSet(ps, rs);
        }

    }

}
