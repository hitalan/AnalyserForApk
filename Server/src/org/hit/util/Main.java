package org.hit.util;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
public class Main {
	private static Logger logger = Logger.getLogger(Main.class);  
	 //private static  List<String> processList = new ArrayList<String>();
	public static void main(String[] args)
	{
		
		
		
		logger.debug("This is debug message.");  
        // 记录info级别的信息  
        logger.info("This is info message.");  
        // 记录error级别的信息  
        logger.error("This is error message.");  
		/*GetConfigure getConfigure = new GetConfigure();
		System.out.println( getConfigure.getAnalyzerPath());
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
