package org.hit.util;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
public class Main {

	 private static  List<String> processList = new ArrayList<String>();
	public static void main(String[] args)
	{
		String 	result =  "" ;
        Gson gson = new Gson();
       try {
	 	result = URLDecoder.decode("{\"clientsApp\":[]}", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
       System.out.println(result);
		MessageInfo info = gson.fromJson(result, MessageInfo.class);
		List<HashMap<String,String>> hashList  =  info.getClientsApp();
		if(hashList.isEmpty())
		System.out.println("the apkInfo is  empty");
		else
		System.out.println("the apkinfo is not empty");
		//List<HashMap<String,String>> hashList = info.getClientsApp();
	/*	GetConfigure getConfigure = new GetConfigure();
	     processList =  new AnalysisUtil().getShellEcho(processList,"info.sh "+getConfigure.getAnalyzerPath()+" "+0+" "+"20160504174727");
        System.out.println(processList.get(1));
        String clientSignatureList[] = processList.get(1).split(" ");
        System.out.println(clientSignatureList[0].substring(13));*/
		/*List<Object> apkinfo =  getInfoByRedis.getInfo();
		GetConfigure getConfigure=new GetConfigure();
		//logger.info("the apkinfo from the task is "+apkinfo.get(0));
		System.out.println("the apkinfo from the task is "+apkinfo.get(0));*/

	/*	System.out.println( getConfigure.getAnalyzerPath());
	       processList = new AnalysisUtil(). getShellEcho(processList,"info.sh "+getConfigure.getAnalyzerPath()+" "+0+" "+"1563");
	    processList = new AnalysisUtil().getShellEcho(processList,"analysis.sh "+ getConfigure.getAnalyzerPath()+" "+"1563");
	    */
	}
	    //System.out.println(processList.get(1));
      /*  String signatureList[] = processList.get(1).split(" ");
        System.out.println(signatureList[0]);
        System.out.println(signatureList[1]);
        System.out.println(signatureList[0].substring(13));*/
		/*List<Object> apkinfo =  getInfoByRedis.getInfo();
		System.out.println("the apkinfo from the task is "+apkinfo);
		Gson gson = new Gson();
		MessageInfo[] info = gson.fromJson(apkinfo.get(0).toString(), MessageInfo[].class);
		System.out.println(info[0].getSubtaskId());
		System.out.println(info[0].getChannelApp().get("hash"));*/
		
     	/* String url = "http://asec.buptnsrc.com:8000/whitelist/com.xiaoao.riskSnipe.nqq";
         String result =  HttpUtil.requestByGetMethod(url);
      	 System.out.println("alan ok"+result);
      	Gson gson = new Gson();
        try {
			result = URLDecoder.decode(result, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MessageInfo info = gson.fromJson(result, MessageInfo.class);
		List<HashMap<String,String>> hashList = info.getClientsApp();
		if(hashList.isEmpty()){
			System.out.println(1);
		}
		else
			System.out.println(hashList);*/
		
	      /* JSONObject json = new JSONObject();
	       GetConfigure getConfigure = new GetConfigure();
	       json.put("agentId", getConfigure.getName());
	      json.put("subtaskId",1 );
	       json.put("status", 4);
	       json.put("result", 1);
	       json.put("analysisReport",1);
	      SendResult sendResult=new SendResult();
	      sendResult.send(json.toString());
	      System.out.println(json.toString());*/
		/*List<Object> apkinfo =  getInfoByRedis.getInfo();
		System.out.println("the apkinfo from the task is "+apkinfo.get(0));
		if(apkinfo.get(0).toString()=="[]")
			System.out.println(1);
		else
			System.out.println(0);*/
		//AnalysisUtil.dealTheApk(0,"1");
	//	{"startTime":"---","fileSize":-1,"duration":-1,"tmpLocation":"/1","status":"0","agentId":"1","endTime":"---","subtaskId":1}
	/*	String taskresult
		= "{"
		+ "\"agentId\" : \""+1+"\", "
		+ "\"status\" : \"" + 0+ "\", "
		+ "\"tmpLocation\" : \"" + "" + "/" +1+ "\","
		+ "\"fileSize\" : " + "-1" + ","
		+ "\"startTime\" : \"" +"---"+ "\","
		+ "\"endTime\" : \"" + "---" + "\","
		+ "\"duration\" : " + "-1"+ ""
		+ "}";
JSONObject myJsonObject1=new JSONObject( taskresult);
myJsonObject1.put("subtaskId", 1);
System.out.println(myJsonObject1.toString());*/
/*SendResult sendResult1=new SendResult();
sendResult1.send(myJsonObject1.toString());*/
		/*HttpClientUtils.getInstance().download("http://dfs.asec.buptnsrc.com/app7/M00/8D/0F/wKhkA1cPLqKAf_AeAYv743RP2hw9841681", "client1.apk",new HttpClientDownLoadProgress() {
				public void onProgress(int progress) {
						System.out.println("download progress = " + progress);
					}
				});*/

		// POST 同步方法
		/*Map<String, String> params = new HashMap<String, String>();
		params.put("username", "admin");
		params.put("password", "admin");
		HttpClientUtils.getInstance().httpPost(
				"http://192.168.31.183:8080/SSHMySql/register", params);*/

		// GET 同步方法
		/*HttpClientUtils.getInstance().httpGet(
				"http://wthrcdn.etouch.cn/weather_mini?city=北京");*/

		// 上传文件 POST 同步方法
	/*	try {
			Map<String,String> uploadParams = new LinkedHashMap<String, String>();
			uploadParams.put("userImageContentType", "image");
			uploadParams.put("userImageFileName", "testaa.png");
			HttpClientUtils.getInstance().uploadFileImpl(
					"http://192.168.31.183:8080/SSHMySql/upload", "android_bug_1.png",
					"userImage", uploadParams);
		} catch (Exception e) {
			e.printStackTrace();
		}*/



}
