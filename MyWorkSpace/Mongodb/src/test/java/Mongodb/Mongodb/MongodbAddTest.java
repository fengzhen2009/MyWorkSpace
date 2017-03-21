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

import com.mongodb.BasicDBObject;

/**
 * 
 * 
 * @remark 一次性插入多条数据测试MongoDB的性能
 */

public class MongodbAddTest extends AbstractJavaSamplerClient {
	private static String name;
	private static String userName;	
	private static String email;
	private static String avatar;
	private static String dob;
	private static String phone;
	private static String address;
	private static String website;
	private static String company;
	private static String postId;
	private static String age;
	private static String home;
	private static String randomID;
	private static String street;
	private static String suite;
	private static String city;
	private static String zipcode;
	private static String geo;
	private static String lat;
	private static String lng;
	
	private static String dbName = "test";

	@Override
	public Arguments getDefaultParameters() {

		Arguments params = new Arguments();
		params.addArgument("name","kevin");
		params.addArgument("userName","bee");
		params.addArgument("email","115646@qq.com");
		params.addArgument("avatar","https://s3.amazonaws.com/uifaces/faces/twitter/thinkleft/128.jpg");
		params.addArgument("dob","ISODate(\"2016-09-29T02:56:38.039+08:00\")");
		params.addArgument("phone","1562856542");
		//params.addArgument("address","{\"street\":\"Gisselle Lakes\",\"suite\":\"Apt. 139\",\"city\":\"Port Jamilbury\",\"zipcode\":\"47259\",\"geo\":{\"lat\":\"56.9480\",\"lng\":\"-89.2121\"}}");
		params.addArgument("street", "Gisselle Lakes");
		params.addArgument("suite","Apt. 139" );
		params.addArgument("city", "guangzhou");
		params.addArgument("zipcode", "47259");
		params.addArgument("lat","56.9480");
		params.addArgument("lng","-89.2121");	
		params.addArgument("website","elza.org");
		params.addArgument("company","蓝月亮中国有限公司");
		params.addArgument("postId","a");
		params.addArgument("age","12");
		params.addArgument("home","guangzhou");
		params.addArgument("randomID","1");		
		return params;
	}

	// 初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行
	@Override
	public void setupTest(JavaSamplerContext arg0) {
		BasicDBObject doc = new BasicDBObject();
	}

	public SampleResult runTest(JavaSamplerContext context) {
		MongoDBDao dao = new MongoDBDaoImpl();
		SampleResult sr = new SampleResult();
		sr.setSampleLabel("增加数据");
		name = context.getParameter("name");
		userName = context.getParameter("userName");
		email = context.getParameter("email");
		avatar = context.getParameter("avatar");
		dob = context.getParameter("dob");
		phone = context.getParameter("phone");
		address = context.getParameter("address");
		street = context.getParameter("street");
		suite = context.getParameter("suite");		
		city = context.getParameter("city");
		zipcode = context.getParameter("zipcode");
		geo = context.getParameter("geo");
		lat = context.getParameter("lat");
		lng = context.getParameter("lng");
		website = context.getParameter("website");
		company = context.getParameter("company");
		postId = context.getParameter("postId");
		age = context.getParameter("age");
		home = context.getParameter("home");
		randomID = context.getParameter("randomID");
	
		sr.sampleStart();
		try {
			Map<String, Object> params = new LinkedHashMap<String, Object>();
			Map<String,Object> address = new LinkedHashMap<String,Object>();
			Map<String,Object> geo = new LinkedHashMap<String,Object>();
			params.put("name", name);
			params.put("userName", userName);
			params.put("email", email );
			params.put("avatar", avatar);
			params.put("dob", dob);
			params.put("phone", phone);
			address.put("street", street);
			address.put("suite", suite);
			address.put("city", city);
			address.put("zipcode", zipcode);
			address.put("geo", geo);
			geo.put("lat", lat);
			geo.put("lng", lng);
			
			params.put("address", address);
			params.put("website", website);
			params.put("company", company);
			params.put("postId", postId);
			params.put("age", age);
			params.put("home", home);
			params.put("randomID", randomID);
			
			boolean res = dao.inSert(dbName, "wjl2016", params);
			if(res==true){
				sr.setSamplerData(params.toString());
				sr.setSuccessful(true);
				sr.setResponseData("结果是：" + res, "UTF-8");
				sr.setResponseData("结果是：" + params.toString(), "UTF-8");
				}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			sr.setSuccessful(false);
			sr.setResponseData("插入数据失败", "UTF-8");
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
		MongodbAddTest test = new MongodbAddTest();
		test.setupTest(arg0);
		test.runTest(arg0);
		test.teardownTest(arg0);
	}
}
