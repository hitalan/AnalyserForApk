package org.hit.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;

public class DealException {
	private static Logger logger = Logger.getLogger(DealException.class);  
	public static List <String> list = new ArrayList<String>();
	public  static void sendFailInfo(String taskId,String failinfo){
		   JSONObject json = new JSONObject();
		   GetConfigure getConfigure = new GetConfigure();
		   json.put("agentId", getConfigure.getName());
		   json.put("subtaskId",taskId);
		   json.put("status", 0);
		   json.put("result","-1" );
		   json.put("analysisReport",failinfo); //"404 or 502 for download the apk"  clientapk or channelapk url is empty download time out bad connection with the taskmanager bad apkinfo in there xml
		  SendResult sendResult=new SendResult();
		  sendResult.send(json.toString());
		  System.out.println(json.toString());
		  logger.error("the result we send is "+json.toString());	
	     if(!failinfo.equals("bad apkinfo in the second post  xml"))
	    	// ;
	    	ShellUtil.getShellEcho(list, "delete.sh "+getConfigure.getAnalyzerPath()+"  0  "+taskId);
	     else
	        //;
	    	 ShellUtil.getShellEcho(list, "delete.sh "+getConfigure.getAnalyzerPath()+"  1  "+taskId);
		  logger.error("delete the FailInfo Floder safely");
		  list.clear();
	}
}
