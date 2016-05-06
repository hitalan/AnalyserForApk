package org.hit.util;

import org.apache.log4j.Logger;
import org.json.JSONObject;

public class DealException {
	private static Logger logger = Logger.getLogger(DealException.class);  
	public  void sendWrongAnswerBy404(String taskId){
	   JSONObject json = new JSONObject();
	   GetConfigure getConfigure = new GetConfigure();
	   json.put("agentId", getConfigure.getName());
	  json.put("subtaskId",taskId);
	   json.put("status", 0);
	   json.put("result","-1" );
	   json.put("analysisReport","404 or 502 for download the apk");
	  SendResult sendResult=new SendResult();
	  sendResult.send(json.toString());
	  System.out.println(json.toString());
	  logger.error("the result we send is "+json.toString());
	}
	
	
	public  void sendWrongAnswerByEmptyUrl(String taskId){
		   JSONObject json = new JSONObject();
		   GetConfigure getConfigure = new GetConfigure();
		   json.put("agentId", getConfigure.getName());
		  json.put("subtaskId",taskId);
		   json.put("status", 0);
		   json.put("result","-1" );
		   json.put("analysisReport","clientapk or channelapk url is empty");
		  SendResult sendResult=new SendResult();
		  sendResult.send(json.toString());
		  System.out.println(json.toString());
		  logger.error("the result we send is "+json.toString());
		}
}
