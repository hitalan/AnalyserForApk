package org.hit.util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;
public class Test {
	public static List<String> list = new  ArrayList<String>();
	static GetConfigure getConfigure=new GetConfigure();
	public static void main(String args[]){
		//test();
		list.clear();
		ShellUtil.getShellEcho(list, "getUnDoTask.sh "+getConfigure.getDownloadChannelPath());
	   Iterator  iter = list.iterator();
	   if(!iter.hasNext()){
		   System.out.println("there are no bad analyzer task ");
	   }
	   else
	   {
		   	while(iter.hasNext()){  
			System.out.println("the folder name is "+iter.next());
       		} 
	   		int remain;
	   		for (int i = 0; i<list.size();i++)
	   		{
		    remain = list.size() -  i;
		   System.out.println("we still remain bad analyzer task for"+remain);
			new AnalysisUtil().dealTheApk(0,list.get(i),list.get(i));
			try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   		}
	   }
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
