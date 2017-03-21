package Mongodb.Mongodb;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.samplers.SampleResult;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

/**
 * 
 * 
 * @remark 一次性插入多条数据测试MongoDB的性能
 */

public class MongodbQuery extends AbstractJavaSamplerClient {
	private static String name;

	private static String dbName = "test";

	@Override
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("name","kevin");
		//params.addArgument("value","value");
		return params;
	}

	// 初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行
	@Override
	public void setupTest(JavaSamplerContext arg0) {
		//BasicDBObject doc = new BasicDBObject();
	}

	public SampleResult runTest(JavaSamplerContext context) {
		MongoDBDao dao = new MongoDBDaoImpl();
		SampleResult sr = new SampleResult();
		sr.setSampleLabel("查询数据");
		name = context.getParameter("name");
		//String kk ="kk";
		sr.sampleStart();
		try {		

			 ArrayList<DBObject> queryObject = new ArrayList<DBObject>();
			 //queryObject =dao.find(dbName, "wjl2016", name, value);
			 queryObject =dao.find(dbName, "wjl2016", "name", name);
			 //sr.setSamplerData(value.toString());
			 sr.setSamplerData(name.toString());
			 sr.setSuccessful(true);
			 sr.setResponseData("结果是：" + queryObject.toString(), "UTF-8");
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			sr.setSuccessful(false);
		} finally {
			sr.sampleEnd();// jmeter 结束统计响应时间标记
		}
		return sr;

	}

	public static void main(String[] args) {
		Arguments params = new Arguments();
		// params.addArgument("num1", "1");//设置参数，并赋予默认值1
		// params.addArgument("num2", "2");//设置参数，并赋予默认值2
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		MongodbQuery test = new MongodbQuery();
		test.setupTest(arg0);
		test.runTest(arg0);
		test.teardownTest(arg0);
	}
}
