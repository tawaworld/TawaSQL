package world.tawa.tawajdbc;


import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tawa on 2018-12-15
 */
public class Config {
    //
    public static final String DEFAULT_DATASOURCE_INDEX = "master";
    //
    private static Map<String, DataSource> dataSourceMap = new HashMap<>();
    //
    private static List<SqlInterceptor> sqlInterceptors = new ArrayList<>();


    public static void addDataSource(DataSource dataSource) {
        dataSourceMap.put(DEFAULT_DATASOURCE_INDEX, dataSource);
    }

    public static void addDataSource(String dataSourceIndex, DataSource dataSource) {
        dataSourceMap.put(dataSourceIndex, dataSource);
    }

    public static Map<String, DataSource> getDataSource() {
        return dataSourceMap;
    }

    public static List<SqlInterceptor> getSqlInterceptors(){
        return sqlInterceptors;
    }

    public static void setSqlInterceptors(List<SqlInterceptor> sqlInterceptors) {
        Config.sqlInterceptors = sqlInterceptors;
    }

    public static void addSqlInterceptor(SqlInterceptor sqlInterceptor){
        sqlInterceptors.add(sqlInterceptor);
    }
}
