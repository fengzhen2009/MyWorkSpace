package Mongodb.Mongodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.BSONObject;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

/*
 * mongodb数据库链接池
 */
public class MongoDBDaoImpl implements MongoDBDao {

	public DBCollection getCollection(String dbName, String collectionName) {
		DB db = MongoDbUtil.getMongoClient().getDB(dbName);
		// return db.getCollection(collectionName);
		if (!db.collectionExists(collectionName)) {
			return db.createCollection(collectionName, new BasicDBObject("capped", false).append("size", 10485));
		}
		return db.getCollection(collectionName);
	}

	// public boolean inSert(String dbName, String collectionName,
	// Map<String,Object> params) {
	// DB db = null;
	// db = MongoDbUtil.getMongoClient().getDB(dbName);
	// DBCollection dbCollection = db.getCollection(collectionName);
	// long num = dbCollection.count();
	// BasicDBObject doc = new BasicDBObject();
	// try {
	// doc.putAll(params);
	// dbCollection.insert(doc);
	// if (dbCollection.count() - num > 0) {
	// System.out.println("添加数据成功！！！");
	// return true;
	// }
	// //return false;
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }finally{
	// if(null != db){
	// db.requestDone(); //请求结束后关闭db
	// }
	// }
	// return false;
	// }

	public boolean inSert(String dbName, String collectionName, Map<String,Object> params) {  
		DB db = null;
		DBCollection dbCollection = null;
		WriteResult result = null;
		String resultString = null;

		db = MongoDbUtil.getMongoClient().getDB(dbName); // 获取数据库实例
		dbCollection = db.getCollection(collectionName); // 获取数据库中指定的collection集合
		BasicDBObject insertObj = new BasicDBObject();
		insertObj.putAll(params);
		try {
			result = dbCollection.insert(insertObj);
			resultString = result.getError();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.requestDone(); // 请求结束后关闭db
			}
		}
		return (resultString != null) ? false : true;
	}

	public boolean delete(String dbName, String collectionName, String keys, Object values) {
		WriteResult writeResult = null;
		DB db = MongoDbUtil.getMongoClient().getDB(dbName);
		DBCollection dbCollection = db.getCollection(collectionName);
		BasicDBObject doc = new BasicDBObject();
		try {		
			doc.put(keys, values);
			writeResult = dbCollection.remove(doc);
			if (writeResult.getN() > 0) {
				System.out.println("删除数据成功!!!!");
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{  
            if(null != db){  
                db.requestDone();   //关闭db  
                db = null;  
            }  
        }  
		return false;
	}

	public ArrayList<DBObject> find(String dbName, String collectionName,String key ,Object value) {
		DB db = MongoDbUtil.getMongoClient().getDB(dbName);
		DBCollection dbCollection = db.getCollection(collectionName);
		
		ArrayList<DBObject> resultList = new ArrayList<DBObject>();
		
		
		try {
			//DBCursor dbCursor = dbCollection.find({}, {'userName' : 1});
			BasicDBObject queryObject = new BasicDBObject(key,value);
			DBCursor dbCursor = dbCollection.find(queryObject);
			
				while (dbCursor.hasNext()) {
					//queryObject.add(dbCursor.next());
					//DBObject obj = dbCursor.next();
					resultList.add(dbCursor.next());
					System.out.println(resultList.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{                       
            }  
            if(null != db){  
                db.requestDone(); //关闭数据库请求  
            }
			return resultList;
    }  
	
	
	
	public boolean update(String dbName, String collectionName, DBObject oldValue, DBObject newValue) {
		WriteResult writeResult = null;
		DB db = MongoDbUtil.getMongoClient().getDB(dbName);
		DBCollection dbCollection = db.getCollection(collectionName);
		try {
			writeResult = dbCollection.update(oldValue, newValue);
			if (writeResult.getN() > 0) {
				System.out.println("数据更新成功");
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{  
            if(null != db){  
                db.requestDone();   //关闭db  
                db = null;  
            }  
        }  
		return false;
	}

	public boolean isExit(String dbName, String collectionName, String key, Object value) {
		DB db = MongoDbUtil.getMongoClient().getDB(dbName);
		DBCollection dbCollection = db.getCollection(collectionName);
		BasicDBObject doc = new BasicDBObject();
		doc.put(key, value);
		Long countL = dbCollection.count(doc);
		if (countL > 0) {
			return true;
		}
		return false;
	}

	private static String dbName = "test";

	/**
	 * :"16112916525695097131"
	 * @param args
	 */
	public static void main(String args[]) {
		MongoDBDao dao = new MongoDBDaoImpl();
		
		DBCollection dbConlle = dao.getCollection(dbName, "wjl2016");
		System.out.println(dbConlle.toString());
		
		//插入数据
		
		
//		{
//			Map<String,Object> params = new LinkedHashMap<String,Object>();
//			Map<String,Object> userName = new LinkedHashMap<String,Object>();
//			userName.put("firstName", "L");
//			userName.put("lastName", "wf");
//			params.put("userName", userName);
//			
//			params.put("age", 18);
//			params.put("province", "湖北");
//			params.put("home", "hb");
//			dao.inSert(dbName, "wjl2016", params);
//		}
//		
//		Map<String,Object> userName = new LinkedHashMap<String,Object>();
//		userName.put("firstName", "L");
//		userName.put("lastName", "wf");
		
		
		//删除数据
				
		//BasicDBObject doc = new BasicDBObject("_id",new ObjectId("584682ba4e1f2262923ae08a"));
		//System.out.println(dao.delete(dbName, "wjl2016", "_id", new ObjectId("584682ba4e1f2262923ae08b")));
		//var ObjectId = require('mongodb').ObjectId;

		//更新数据		
//		BasicDBObject oldValue = new BasicDBObject().append("name", "kk");
//		BasicDBObject newValue = new BasicDBObject();
//		newValue.append("$set", new BasicDBObject().append("userName", "jj"));
//		System.out.println(dao.update(dbName, "wjl2016",oldValue,newValue  ));
		
		
		//查询数据
//		 ArrayList<DBObject> queryObject = new ArrayList<DBObject>();
//		 queryObject =dao.find(dbName, "lwf2016", "name", "jay618878");
//		 System.out.println(queryObject.toString());
		
	}

	private static Object require() {
		// TODO Auto-generated method stub
		return null;
	}

}