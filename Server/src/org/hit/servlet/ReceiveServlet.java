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
import org.hit.util.AnalysisUtil;
import org.hit.util.HttpClientUtils;
import org.hit.util.getInfoByRedis;
import org.hit.util.HttpClientUtils.HttpClientDownLoadProgress;

import com.google.gson.Gson;
public class ReceiveServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6731251846319286501L;
	public ReceiveServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
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
		System.out.println("the apkinfo from the task is "+apkinfo.get(0));
		if(apkinfo.get(0)=="")
			System.out.println(1);
		else
			System.out.println(0);
		if(apkinfo.get(0).toString()=="[]"){
			   System.out.println("we have no task");	
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
		String urlhost = "http://dfs.asec.buptnsrc.com/";
		String channelpath = "/home/hit_alan/zhoufandi/somefilebetweenlinuxandwindows/channelapk/";
		String clientpath = "/home/hit_alan/zhoufandi/somefilebetweenlinuxandwindows/clientapk/";
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
		System.out.println("clientUrl.size() is " + clientUrl.size());
		String [] clientUrls = new String[clientUrl.size()];
		for(int i = 0;i<clientUrl.size();i++){
			clientUrls[i] = clientUrl.get(i);
		}
		System.out.println("the taskId is "+taskId+" the channelUrl "+channelUrl+" the url is "+clientUrl.get(0));
		String channelUrlInfo = channelUrl;
		System.out.println("the channelurlinfo is "+channelUrlInfo);
		String channelFileName = "channel.apk";
		System.out.println("the channelFileName is "+channelFileName);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String dirPath = 	df.format(new Date());
        List<String> makeDirList = new ArrayList<String>();
        makeDirList =new AnalysisUtil().getShellEcho(makeDirList,"makeDir.sh  0 "+dirPath);
		HttpClientUtils.getInstance().download(urlhost+channelUrlInfo, channelpath+dirPath+"/channel.apk",new HttpClientDownLoadProgress() { 
			  public void onProgress(int progress) 
			  {
				  if(progress==100)
				  {
				  System.out.println("download channel progress = " + progress);
				  count++;
				System.out.println("the count is "+count);
				  }
			  }
		}
		);
		for(int i = 0;i<clientUrl.size();i++){
			HttpClientUtils.getInstance().download(urlhost+clientUrls[i], clientpath+dirPath+"/client"+i+".apk",new HttpClientDownLoadProgress() {
				public void onProgress(int progress) {
					if(progress==100){
						System.out.println("download client progress = " + progress);	
						count++;
						System.out.println("the count is "+count);
					}    
				}
			});
		}
		while(count<=clientUrl.size()+1){
			System.out.print("");
			if(count==clientUrl.size()+1){
				System.out.println("finish done");
				count=0;
				try {
					  Thread.sleep(2000);
					   System.out.println("done already");	
						response.setCharacterEncoding("UTF-8");
						PrintWriter out = response.getWriter();
						out.print("下载成功");
						out.flush();
						out.close();  
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new AnalysisUtil().dealTheApk(0,taskId,dirPath);
				break;
			}
		}
		}
	}
	public void init() throws ServletException {
	}
}
