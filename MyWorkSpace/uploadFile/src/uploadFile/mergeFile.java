package uploadFile;

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
import org.apache.jmeter.testbeans.TestBean;

import cn.com.bluemoon.*;

/**
 * 
 * 
 * @remark 上传文件
 */

public class mergeFile extends AbstractJavaSamplerClient {
	private static String url;

	@Override
	public Arguments getDefaultParameters() {

		Arguments params = new Arguments();
		params.addArgument("url","C://Users/liaoweifeng/Desktop/SFA_201701.zip");
		return params;
	}


	@Override
	public void setupTest(JavaSamplerContext arg0) {
		
	}

	public SampleResult runTest(JavaSamplerContext context) {
		
		SampleResult sr = new SampleResult();
		sr.sampleStart();// jmeter 开始统计响应时间标记
		sr.setSampleLabel("mergeFile");
		url = context.getParameter("url");
		try {
			ResumeBreakpointTest test = new ResumeBreakpointTest();
			test.setFile(new File(url));
			int chunks = test.getChunks();
			String res = test.mergeFile("http://tmallapi.bluemoon.com.cn/fileHttp2/chunk/mergeFile");
			sr.setResponseData(res,"UTF-8");
			sr.setSuccessful(true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			sr.setSuccessful(false);
			sr.setResponseData("获取文件失败", "UTF-8");
		} finally {
			sr.sampleEnd();// jmeter 结束统计响应时间标记
		}
		return sr;

	}

	public static void main(String[] args) {
		Arguments params = new Arguments();
		params.addArgument("url", "C:\\Users\\liaoweifeng\\Desktop\\test\\IMG_0100.MOV");//设置参数，并赋予默认值1
		// params.addArgument("num2", "2");//设置参数，并赋予默认值2
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		mergeFile test = new mergeFile();
		test.setupTest(arg0);
		test.runTest(arg0);
		test.teardownTest(arg0);
	}
}
