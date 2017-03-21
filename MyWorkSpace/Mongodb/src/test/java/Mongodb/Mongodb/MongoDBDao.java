package Mongodb.Mongodb;
import java.util.ArrayList;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
* 类名： MongoDBDao
* 作者：wjl
*/
public interface MongoDBDao {
    /**
     * 
     * 方法名：getCollection
     * 作者：wjl
     * 描述：获取指定mongodb数据库的collection集合
     * @param dbName    数据库名
     * @param collectionName    数据库集合名
     * @return
     */
    public DBCollection getCollection(String dbName, String collectionName);
    /**
     * @param dbName
     * @param collectionName
     * @param params
     * @return
     */
    public boolean inSert(String dbName, String collectionName, Map<String,Object> params);
    /**
     * 
     * 方法名：delete
     * 作者：wjl
     * 描述：删除数据库dbName中，指定keys和相应values的值
     * @param dbName
     * @param collectionName
     * @param keys
     * @param values
     * @return
     */
    public boolean delete(String dbName, String collectionName, String keys, Object values);
    /**
     * 
     * 方法名：find
     * 作者：wjl
     * 描述：从数据库dbName中取出相应数目的数据
     * @param dbName
     * @param collectionName
     * @param keys
     * @param values
     * @param num
     * @return
     */
    public ArrayList<DBObject> find(String dbName, String collectionName ,String key,Object value);
    /**
     * 
     * 方法名：update
     * 作者：wjl
     * 描述：更新数据库dbName，用指定的newValue更新oldValue
     * @param dbName
     * @param collectionName
     * @param oldValue
     * @param newValue
     * @return
     */
    public boolean update(String dbName, String collectionName, DBObject oldValue, DBObject newValue);
    /**
     * 
     * 方法名：isExit
     * 作者：wjl
     * 描述：判断给定的keys和相应的values在指定的dbName的collectionName集合中是否存在
     * @param dbName
     * @param collectionName
     * @param keys
     * @param values
     * @return
     */
    public boolean isExit(String dbName, String collectionName, String key, Object value);
}