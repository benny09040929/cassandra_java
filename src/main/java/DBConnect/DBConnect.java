package DBConnect;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * Created by TB690275 on 2017/1/23.
 */
public class DBConnect {

    public void CsConnect(String address, String DBname, String query) {

        Cluster cluster = Cluster.builder().addContactPoint(address).build();

        Session session = cluster.connect(DBname);

        session.execute(query);




    }

}
