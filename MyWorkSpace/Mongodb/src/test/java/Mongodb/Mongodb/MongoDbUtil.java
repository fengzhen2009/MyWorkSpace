package Mongodb.Mongodb;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

public class MongoDbUtil {
	
	private static MongoClient mongoClient = null;
	
	public static MongoClient getMongoClient(){
		if (mongoClient == null) {
			MongoClientOptions.Builder buide = new MongoClientOptions.Builder();
			buide.connectionsPerHost(100);// 与目标数据库可以建立的最大链接数
			buide.connectTimeout(1000 * 60 * 20);// 与数据库建立链接的超时时间
			buide.maxWaitTime(100 * 60 * 5);// 一个线程成功获取到一个可用数据库之前的最大等待时间
			buide.threadsAllowedToBlockForConnectionMultiplier(100);
			// buide.maxConnectionIdleTime(0);
			// buide.maxConnectionLifeTime(0);
			buide.socketTimeout(0);
			buide.socketKeepAlive(true);
			MongoClientOptions myOptions = buide.build();
			try {
				mongoClient = new MongoClient(new ServerAddress("192.168.234.28", 27017), myOptions);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		if(null==mongoClient){
			 try {
				 throw new Exception("link mongdb fail");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mongoClient;
	}
	
	
}
