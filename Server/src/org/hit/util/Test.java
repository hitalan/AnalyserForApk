package org.hit.util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;
public class Test {
	public static List<String> list = new  ArrayList<String>();
	static GetConfigure getConfigure=new GetConfigure();
	public static void main(String args[]){
		System.out.println(substringFlodername());
		//testAD();
		//test();
	/*	try
		{    
			test();
			//   reDo();
		}
		catch(Exception e)
		{
		 System.out.println("there are bad exception");	
		}*/
	}
	public static String substringFlodername(){
		//String temps = "5b7ef3b22a666fe6495d01135c3f6fc61900bad745ec8453007bb7276a77993e";
       String temp= "asfasdfa/app7/M00/F2/91/wKhkA1bQmyeAJp3fAB8V7bXFw788776283";
		//return String.valueOf(temps.length()+temp.length());
       return temp.substring(temp.indexOf("/app")+1);
	}
	public static void  testAD(){
		String channelPathInDfs = "app100/M00/21/71/CmxyTVcZG_uAQJ3CAR9V7WOjBBE2143579";
		//String sendAdJson = "{\"location\": \""+channelPathInDfs+"\"}";
		//sendAdJson  = "{\"location\":\"app100/M00/21/71/CmxyTVcZG_uAQJ3CAR9V7WOjBBE2143579\"}";
		String url = "http://10.108.114.233:8889/addetector/12310";
		//System.out.println(sendAdJson);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("location", channelPathInDfs);
		System.out.println(jsonObject.toString());
		try {
	    System.out.println(HttpUtil.httpPostWithJSON(url, jsonObject.toString()));		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testTaskManager(){
		   String url = getConfigure.getWhiteListUrl()+"com.tencent.qq";
           String result =   HttpUtil.requestByGetMethod(url);
		   if(result.startsWith("bad get due to the server statuscode is"))
			   System.out.println("bad");
		   else 
			   System.out.println("good");
	}
	public static void reDo(){
		list.clear();
		ShellUtil.getShellEcho(list, "getUnDoTask.sh "+getConfigure.getDownloadChannelPath());
	   Iterator<String>  iter = list.iterator();
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
			try {
				new AnalysisUtil().dealTheApk(0,list.get(i),list.get(i),"");
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			   System.out.println("异常发生了");
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
  	System.out.println(myJsonObject1.toString());
  	/*SendResult sendResult1=new SendResult();
	sendResult1.send(myJsonObject1.toString());*/
/*	request.setCharacterEncoding("UTF-8");  
    response.setContentType("text/html;charset=UTF-8");  
    String acceptjson = "";  
    try {  
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));  
        StringBuffer sb = new StringBuffer("");  
        String temp;  
        while ((temp = br.readLine()) != null) {  
            sb.append(temp);  
        }  
        br.close();  
        acceptjson = sb.toString();  
        response.getWriter().write("收到"+acceptjson);  
        System.out.println("收到"+acceptjson);
    } catch (Exception e) {  
        e.printStackTrace();  
    }  */
	}
}
