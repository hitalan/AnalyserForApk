package org.hit.quartz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hit.util.GetConfigure;
import org.hit.util.HttpUtil;
import org.hit.util.ShellUtil;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SendHeartJob implements Job{
	private static Logger logger = Logger.getLogger(SendHeartJob.class);  
	 GetConfigure getConfigure=new GetConfigure();
	public static List <String> list = new ArrayList<String>();
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		 System.out.println("心跳任务调度 ，当前时间为："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SS").format(new Date()));
		 logger.info("心跳任务调度 ，当前时间为："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SS").format(new Date()));
		heartBeat();
	}
	public void heartBeat(){
		 final String ip;
		 final String type;
		 final String version;
		 final String port;
		 final  String url;
		 final String name;
		 String maxCap;
		ip=getConfigure.getIp();
		port=getConfigure.getPort();
		type=getConfigure.getType();
		version=getConfigure.getVersion();
		url=getConfigure.getUrl();
		name=getConfigure.getName()+HttpUtil.getIp();
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++add the name " + name);
		maxCap=getConfigure.getMaxCap();
	     JSONObject jo=new JSONObject();
         jo.put("ip", ip);
         jo.put("type", type);
         jo.put("version", version);
         jo.put("status", 1);
         jo.put("maxCap", maxCap);
         jo.put("port", port);
         String currentLoad =   ShellUtil.getShellEcho(list, "checkTaskNum.sh "+getConfigure.getDownloadChannelPath()).get(0);
         list.clear();
         jo.put("currentLoad", Integer.valueOf(currentLoad));
         String json=jo.toString();
         try {
        	String content =  send(url+"/heart_beat/"+name,json,"POST",true,(Map)null);
			System.out.println("we get the response content is "+content);
			 logger.info("we get the response content is "+content);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("the heartbeat we send is"+json);	    
		 logger.info("the heartbeat we send is"+json);
}
	

    public String send(String url, String paramsStr,String method, boolean needFeedback, Map<String, String> header) throws IOException {
        if(paramsStr == null || paramsStr.equals("")) {
            paramsStr = "Without parameters";
        }

        HttpURLConnection connection = this.getConnection(url, method, header);
        connection = this.passParameters(connection, paramsStr);
        if(needFeedback) {
            InputStream is = connection.getInputStream();
            String feedBack = this.getTextFromInputStream(is);
            is.close();
            connection.disconnect();
            return feedBack;
        } else {
            return "";
        }
    }
    private HttpURLConnection passParameters(HttpURLConnection connection, String paramsStr) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
        osw.write(paramsStr);
        osw.close();
        return connection;
    }

    private String getTextFromInputStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String response;
        String line;
        for(response = ""; (line = br.readLine()) != null; response = response + line) {
            ;
        }

        return response;
    }
    
    private HttpURLConnection getConnection(String urlStr, String method, Map<String, String> header) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod(method);
        connection.setDoOutput(true);
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(30000);
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;
    }
}
