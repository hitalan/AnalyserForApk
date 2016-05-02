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
import org.hit.util.GetConfigure;
import org.hit.util.HttpClientUtils;
import org.hit.util.Main;
import org.hit.util.getInfoByRedis;
import org.hit.util.HttpClientUtils.HttpClientDownLoadProgress;
import com.google.gson.Gson;
public class ReceiveServlet extends HttpServlet{
	private static Logger logger = Logger.getLogger(Main.class);  
	public static boolean isBad;
	private static final long serialVersionUID = 6731251846319286501L;
	public ReceiveServlet() {
		super();
	}
	public void destroy() {
		super.destroy(); 
	}
	public static int count;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Object> apkinfo =  getInfoByRedis.getInfo();
		GetConfigure getConfigure=new GetConfigure();
		logger.debug("the apkinfo from the task is "+apkinfo.get(0));
		if(apkinfo.get(0).toString().equals("[]")){
			   logger.info("we have no task");	
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.print("结束");
				out.flush();
				out.close();  
		}
		else
		{
		Gson gson = new Gson();
		MessageInfo[] info = gson.fromJson(apkinfo.get(0).toString(), MessageInfo[].class);
		String urlhost = getConfigure.getDownloadUrl();
		String channelpath = getConfigure.getDownloadChannelPath();
		String clientpath =getConfigure.getDownloadClientPath();
		String taskId = info[0].getSubtaskId();
		 String channelUrl = "";
		HashMap <String ,String> hash =  info[0].getChannelApp();
		Iterator iter = hash.entrySet().iterator();
		while (iter.hasNext()) {
		Map.Entry entry = (Map.Entry) iter.next();
	    Object key = entry.getKey();
	    if(key.equals("url")){
	    	 channelUrl = URLDecoder.decode((String)entry.getValue(),"UTF-8");
	    }
		}
		List<HashMap<String,String>> hashList = info[0].getClientsApp();
		List<String> clientUrl = new ArrayList<String>();
		for(HashMap hashmap : hashList){
			iter = hashmap.entrySet().iterator();
			while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
		    Object key = entry.getKey();
		    if(key.equals("url")){
		    	clientUrl.add( URLDecoder.decode((String)entry.getValue(),"UTF-8"));
		    }
		    }
		    }
		logger.debug("clientUrl.size() is " + clientUrl.size());
		String [] clientUrls = new String[clientUrl.size()];
		for(int i = 0;i<clientUrl.size();i++){
			clientUrls[i] = clientUrl.get(i);
		}
		logger.debug("the taskId is "+taskId+" the channelUrl "+channelUrl+" the url is "+clientUrl.get(0));
		String channelUrlInfo = channelUrl;
		String channelFileName = "channel.apk";
		logger.debug("the channelurlinfo is "+channelUrlInfo);
		logger.debug("the channelFileName is "+channelFileName);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String dirPath = 	df.format(new Date());
        List<String> makeDirList = new ArrayList<String>();
        makeDirList =new AnalysisUtil().getShellEcho(makeDirList,"makeDir.sh  0  "+getConfigure.getAnalyzerPath()+" "+dirPath);
        HttpClientUtils.taskId=taskId;
		HttpClientUtils.getInstance().download(urlhost+channelUrlInfo, channelpath+dirPath+"/channel.apk",new HttpClientDownLoadProgress() { 
			  public void onProgress(int progress) 
			  {
				  if(HttpClientUtils.isBad==true){
					  isBad = true;
				  }
				  if(progress==100)
				  {
						logger.debug("download channel progress = " + progress);
				  count++;
					logger.debug("the count is "+count);
				  }
			  }
		}
		);
		for(int i = 0;i<clientUrl.size();i++){
			HttpClientUtils.getInstance().download(urlhost+clientUrls[i], clientpath+dirPath+"/client"+i+".apk",new HttpClientDownLoadProgress() {
				public void onProgress(int progress) {
					  if(HttpClientUtils.isBad==true){
						  isBad = true;
					  }
					if(progress==100){
						logger.debug("download client progress = " + progress);	
						count++;
						logger.debug("the count is "+count);
					}    
				}
			});
		}
		while(count<=clientUrl.size()+1)
		{
			logger.debug("");
			  if(isBad)
			  {
				 new DealException().sendWrongAnswerBy404(taskId);
			      HttpClientUtils.isBad=false;
			      isBad=true;
			  	  logger.error("we finish this analyze because the bad 404 download");
				  count=0;
			      break;
			  }			  
			  else
			  {
				  if(count==clientUrl.size()+1){
						logger.info("finish download task");
						count=0;
						try {
							  Thread.sleep(2000);
								response.setCharacterEncoding("UTF-8");
								PrintWriter out = response.getWriter();
								out.print("下载成功");
								out.flush();
								out.close();  
								new AnalysisUtil().dealTheApk(0,taskId,dirPath);
							  }
						 catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;
		        }
			  }
		}
	}
	}
	public void init() throws ServletException {
	}
}