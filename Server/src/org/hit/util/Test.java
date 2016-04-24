package org.hit.util;
import org.json.JSONObject;
public class Test {
	public static void main(String args[]){
		test();
		
		
	}
	public static void test(){
	String a  =  "agentId";
	
	String taskresult
	= "{"
	+ "\"agentId\" : \""+a+"\", "
	+ "\"status\" : \"" + 0+ "\", "
	+ "\"tmpLocation\" : \"" + "" + "/" +1 + "\","
	+ "\"fileSize\" : " + "-1" + ","
	+ "\"startTime\" : \"" +"---"+ "\","
	+ "\"endTime\" : \"" + "---" + "\","
	+ "\"duration\" : " + "-1"+ ""
	+ "}";
	JSONObject myJsonObject1=new JSONObject(taskresult);
  	myJsonObject1.put("subtaskId", 1);
  	SendResult sendResult1=new SendResult();
	sendResult1.send(myJsonObject1.toString());
	}
}
