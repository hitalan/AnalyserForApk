package org.hit.util;

import org.json.JSONObject;

public class DealException {
	public  void sendWrongAnswerBy404(String taskId){
	   JSONObject json = new JSONObject();
	   GetConfigure getConfigure = new GetConfigure();
	   json.put("agentId", getConfigure.getName());
	  json.put("subtaskId",taskId);
	   json.put("status", 0);
	   json.put("result","-1" );
	   json.put("analysisReport","404 for download the apk");
	  SendResult sendResult=new SendResult();
	  sendResult.send(json.toString());
	  System.out.println(json.toString());
	}
}
