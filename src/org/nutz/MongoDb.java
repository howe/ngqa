package org.nutz;
 
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.util.Set;
 
public class MongoDb {
     
    static final String  host = "mongodb-wendal.jelastic.servint.net",
                                dbname = "ngqa",
                                user = "wendal",passwd="123456";
    
    public void createConnection() {
     
        DBCollection dbc = null;
   
        try {
            Mongo m = new Mongo(host);        
            DB db = m.getDB(dbname);
          
            if (db.authenticate(user, passwd.toCharArray())) {
                System.out.println("Connected!");                
            }
            dbc = db.getCollection("myCollection");
            System.out.println("name of collection :  " + dbc.getName());
            System.out.println("name of collection :  " + m.getDB("ngqa").getCollection("CCC").getName());           
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (MongoException ex) {
            ex.printStackTrace();
        }
    
    }
    
    public static void main(String[] args) {
		new MongoDb().createConnection();
	}
}