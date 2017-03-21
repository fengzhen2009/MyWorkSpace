package Mongodb.Mongodb;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.mongodb.WriteResult;

/**
 * 
 * 
 * @remark 一次性插入多条数据测试MongoDB的性能
 */

public class MongodbUpdate extends AbstractJavaSamplerClient {
	//private static String _id;
	private static String name;
	private static String userName;	
	private static String email;
	private static String avatar;
	private static String dob;
	private static String phone;
	private static String dbName = "test";

	@Override
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		//params.addArgument("_id","584625f04e1f16c455912b8d");
		params.addArgument("name","kevin");
		params.addArgument("userName","bee");
		params.addArgument("email","115646@qq.com");
		params.addArgument("avatar","https://s3.amazonaws.com/uifaces/faces/twitter/thinkleft/128.jpg");
		params.addArgument("dob","ISODate(\"2016-09-29T02:56:38.039+08:00\")");
		params.addArgument("phone","1562856542");	
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
		sr.setSampleLabel("更新数据");
		//_id = context.getParameter("_id");
		name = context.getParameter("name");
		userName = context.getParameter("userName");
		email = context.getParameter("email");
		avatar = context.getParameter("avatar");
		dob = context.getParameter("dob");
		phone = context.getParameter("phone");
		sr.sampleStart();
		try {
			BasicDBObject oldValue = new BasicDBObject();
			oldValue.append("name", name);	
			BasicDBObject newValue = new BasicDBObject();
			newValue.append("$set", new BasicDBObject().append("userName", userName).append("email", email)
					.append("avatar", avatar).append("dob", dob).append("phone", phone));
			boolean res = dao.update(dbName, "wjl2016", oldValue, newValue);			
			sr.setSamplerData(oldValue.toString());
			if(res==true){
			sr.setSuccessful(true);
			sr.setResponseData("结果是：" + res, "UTF-8");
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			sr.setSuccessful(false);
			sr.setResponseData("更新失败", "UTF-8");
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
		MongodbUpdate test = new MongodbUpdate();
		test.setupTest(arg0);
		test.runTest(arg0);
		test.teardownTest(arg0);
	}
}
