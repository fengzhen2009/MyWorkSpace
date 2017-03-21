package uploadFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.xml.ws.Response;

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

public class threeAndOne extends AbstractJavaSamplerClient {
	private static String url;
	private static int j;

	@Override
	public Arguments getDefaultParameters() {

		Arguments params = new Arguments();
		params.addArgument("url", "C:\\Users\\liaoweifeng\\Desktop\\SFA_201701.zip");
		params.addArgument("j", "0");
		return params;
	}

	@Override
	public void setupTest(JavaSamplerContext arg0) {

	}

	public SampleResult runTest(JavaSamplerContext context) {

		SampleResult sr = new SampleResult();

		sr.sampleStart();// jmeter 开始统计响应时间标记
		sr.setSampleLabel("上传合并");
		url = context.getParameter("url");
		j = context.getIntParameter("j");
		sr.setRequestHeaders("上传文件为：" + url);
		ResumeBreakpointTest test = new ResumeBreakpointTest();
		try {
			// ResumeBreakpointTest test = new ResumeBreakpointTest();
			test.setFile(new File(url));
		} catch (Exception e) {
			// TODO: handle exception
			sr.setSuccessful(false);
			sr.setResponseData("获取文件失败", "UTF-8");
		}
		try {
			int chunks = test.getChunks();
			for (int i = 0; i < chunks; i++) {
				String res = test.uploadFile("http://tmallapi.bluemoon.com.cn/fileHttp2/chunk/upload", i);
				sr.setResponseData(res, "UTF-8");
			}
		} catch (Exception e) {
			// TODO: handle exception
			sr.setSuccessful(false);
			sr.setResponseData("上传文件失败", "UTF-8");
		}
		try {
			String res1 = test.mergeFile("http://tmallapi.bluemoon.com.cn/fileHttp2/chunk/mergeFile");
			test.clear();
			sr.setSuccessful(true);
			sr.setResponseData(res1, "UTF-8");
		}

		catch (Exception e) {
			// TODO: handle exception
			sr.setSuccessful(false);
			sr.setResponseData("合并文件失败", "UTF-8");
		} finally {
			sr.sampleEnd();// jmeter 结束统计响应时间标记
		}
		return sr;

	}

	public static void main(String[] args) {
		Arguments params = new Arguments();
		params.addArgument("url", "D:\\工作\\性能测试\\大文件上传\\create\\000.txt");// 设置参数，并赋予默认值1
		params.addArgument("j", "0");// 设置参数，并赋予默认值1
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		threeAndOne test = new threeAndOne();
		test.setupTest(arg0);
		test.runTest(arg0);
		test.teardownTest(arg0);
	}
}
