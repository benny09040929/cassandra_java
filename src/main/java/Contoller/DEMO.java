package Contoller;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import java.util.Iterator;

/**
 * Created by TB690275 on 2017/1/23.
 */
public class DEMO {

    public static void main (String[] args){
        QueryOptions options = new QueryOptions();
        options.setConsistencyLevel(ConsistencyLevel.QUORUM);

        Cluster cluster = Cluster.builder()
                .addContactPoint("192.168.56.130")
                .withCredentials("cassandra", "cassandra")
                .withQueryOptions(options)
                .build();

        Session session = cluster.connect();
        session.execute("CREATE  KEYSPACE kp WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};");

        Session kpSession = cluster.connect("kp");
        kpSession.execute("CREATE TABLE tbl(a INT,  b INT, c INT, PRIMARY KEY(a));");

        RegularStatement insert = QueryBuilder.insertInto("kp", "tbl").values(new String[]{"a", "b", "c"}, new Object[]{1, 2, 3});
        kpSession.execute(insert);

        RegularStatement insert2 = QueryBuilder.insertInto("kp", "tbl").values(new String[]{"a", "b", "c"}, new Object[]{3, 2, 1});
        kpSession.execute(insert2);

        RegularStatement delete = QueryBuilder.delete().from("kp", "tbl").where(QueryBuilder.eq("a", 1));
        kpSession.execute(delete);

        RegularStatement update = QueryBuilder.update("kp", "tbl").with(QueryBuilder.set("b", 6)).where(QueryBuilder.eq("a", 3));
        kpSession.execute(update);

        RegularStatement select = QueryBuilder.select().from("kp", "tbl").where(QueryBuilder.eq("a", 3));
        ResultSet rs = kpSession.execute(select);
        Iterator<Row> iterator = rs.iterator();
        while (iterator.hasNext())
        {
            Row row = iterator.next();
            System.out.println("a=" + row.getInt("a"));
            System.out.println("b=" + row.getInt("b"));
            System.out.println("c=" + row.getInt("c"));
        }
        kpSession.close();
        session.close();
        cluster.close();
    }

}
