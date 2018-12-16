package world.tawa.tawajdbc;

/**
 * Created by tawa on 2018-12-15
 */
public interface SqlInterceptor {

    /**
     * before execute interceptor
     */
    void beforeExecute(String sql, Object ... parameters);

    /**
     * after execute interceptor
     */
    void afterExecute(String sql, Object ... parameters);
}
