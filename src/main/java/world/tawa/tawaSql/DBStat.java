package world.tawa.tawajdbc;

import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicLong;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tawa on 2018-12-15
 */
public class DBStat {
    //
    public static boolean enable = true;

    //
    public static class SqlStat {

        public long invokeCount;

        public long totalSpendTime;

        public long avgSpendTime;

        public long maxSpendTime;

        public long minSpendTime;

        public long lastSpendTime;

        public long exceptionCount;
    }
    //
    private static AtomicLong invokeCount = new AtomicLong(0);
    private static AtomicLong totalSpendTime = new AtomicLong(0);
    private static AtomicLong maxSpendTime = new AtomicLong(0);
    private static AtomicLong minSpendTime = new AtomicLong(0);
    private static AtomicLong lastSpendTime = new AtomicLong(0);
    private static AtomicLong exceptionCount = new AtomicLong(0);
    private static Map<String, SqlStat> sqlStatMap = new ConcurrentHashMap<>();


    public static AtomicLong getInvokeCount() {
        return invokeCount;
    }

    public static AtomicLong getTotalSpendTime() {
        return totalSpendTime;
    }

    public static AtomicLong getMaxSpendTime() {
        return maxSpendTime;
    }

    public static AtomicLong getMinSpendTime() {
        return minSpendTime;
    }

    public static AtomicLong getLastSpendTime() {
        return lastSpendTime;
    }

    public static AtomicLong getExceptionCount() {
        return exceptionCount;
    }

    public static Map<String, SqlStat> getSqlStatMap() {
        return sqlStatMap;
    }

    /**
     * execute sql stat
     */
    public static void executeStat(String sql, long time, boolean isException) {
        if (!enable) {
            return;
        }
        lastSpendTime.set(time);
        totalSpendTime.addAndGet(time);
        invokeCount.incrementAndGet();
        if (invokeCount.longValue() < 0) { //reset
            invokeCount.set(0);
        }
        if (totalSpendTime.longValue() < 0) { //reset
            totalSpendTime.set(0);
        }
        if (maxSpendTime.longValue() < time) {
            maxSpendTime.set(time);
        }
        if (minSpendTime.longValue() > time) {
            minSpendTime.set(time);
        }
        if (isException) {
            exceptionCount.incrementAndGet();
            if (exceptionCount.longValue() < 0) { //reset
                exceptionCount.set(0);
            }
        }
        SqlStat sqlStat = sqlStatMap.get(sql);
        if (sqlStat == null) {
            sqlStat = new SqlStat();
            sqlStat.invokeCount++;
            sqlStat.maxSpendTime = time;
            sqlStat.minSpendTime = time;
            sqlStat.avgSpendTime = time;
            sqlStat.lastSpendTime = time;
            sqlStat.totalSpendTime = time;
            sqlStatMap.put(sql, sqlStat);
        } else {
            sqlStat.invokeCount++;
            if (sqlStat.maxSpendTime < time) {
                sqlStat.maxSpendTime = time;
            }
            if (sqlStat.minSpendTime > time) {
                sqlStat.minSpendTime = time;
            }
            sqlStat.totalSpendTime += time;
            sqlStat.lastSpendTime = time;
            if (isException) {
                sqlStat.exceptionCount++;
            }
        }
    }

    public static String dump(){
        StringBuilder sb = new StringBuilder();
        String format = "%-20s: %-20s\n";
        sb.append(String.format(format, "InvokeCount", DBStat.invokeCount));
        sb.append(String.format(format, "ExceptionCount", DBStat.exceptionCount));
        sb.append(String.format(format, "LastSpendTime(ms)", DBStat.lastSpendTime));
        sb.append(String.format(format, "MaxSpendTime(ms)", DBStat.maxSpendTime));
        sb.append(String.format(format, "MiniSpendTime(ms)", DBStat.minSpendTime));
        if(DBStat.invokeCount.longValue() > 0){
            double avgSpendTime = DBStat.totalSpendTime.longValue() * 1.0 / DBStat.invokeCount.longValue();
            sb.append(String.format(format, "AvgSpendTime(ms)", avgSpendTime));
        }
        format="%-5s %-8s %-8s %-15s %-15s " +
                "%-15s %-15s %-50s\n";
        sb.append(String.format(format,
                "#","COUNT","AVG","LAST TIME",
                "MAX TIME","MIN_TIME",
                "EXCEPTION","SQL"));
        int i = 0;
        for (Map.Entry<String, DBStat.SqlStat> entry : DBStat.sqlStatMap.entrySet()){
            SqlStat v = entry.getValue();
            v.avgSpendTime=v.totalSpendTime/v.invokeCount;
            String sql=entry.getKey();
            if(sql.length()>100){
                sql=sql.substring(0, 100);
            }
            sb.append(String.format(format,
                    ++i,
                    v.invokeCount,
                    v.avgSpendTime,
                    v.lastSpendTime,
                    v.maxSpendTime,
                    v.minSpendTime,
                    v.exceptionCount,
                    sql
            ));
        }
        return sb.toString();
    }

}
