package org.hit.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hit.util.MessageInfo;
import org.apache.log4j.Logger;
import org.hit.util.AnalysisUtil;
import org.hit.util.DealException;
import org.hit.util.DownLoadUtil;
import org.hit.util.GetConfigure;
import org.hit.util.HttpClientUtils;
import org.hit.util.getInfoByRedis;
import org.hit.util.HttpClientUtils.HttpClientDownLoadProgress;
import com.google.gson.Gson;
public class ReceiveServlet extends HttpServlet{
	private static Logger logger = Logger.getLogger(ReceiveServlet.class);  
	public static boolean isBad;
	public static int failtimes;
	public static String failTaskId;
	private static final long serialVersionUID = 6731251846319286501L;
	public ReceiveServlet() {
		super();
	}
	public void destroy() {
		super.destroy(); 
	}
	public  int count;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Object> apkinfo =  getInfoByRedis.getInfo();
		if (apkinfo==null){
			System.out.println("bad connection with the redis");
			 logger.error("bad connection to the redis ");	
		}
		else
		{
			GetConfigure getConfigure=new GetConfigure();
			logger.info("the apkinfo from the task is "+apkinfo.get(0));
			System.out.println("the apkinfo from the task is "+apkinfo.get(0));
			String result; 
			if(apkinfo.get(0).toString().equals("[]")){
			 logger.info("we have no task");	
			   System.out.println("we have no task");
			   result = "we have no task"; 
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.print(result);
				out.flush();
				out.close();  
			}
			else{
				if(new DownLoadUtil().download(apkinfo))
					result= "download failed";
				else
					result = "download success"; 
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.print(result);
				out.flush();
				out.close();  
				}
		}
	}
	public void init() throws ServletException {
	}
}