package org.hit.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hit.util.HttpClientUtils.HttpClientDownLoadProgress;
import org.json.JSONObject;

import com.google.gson.Gson;


//输出的结果类型有：   1 疑似二次打包应用，也有可能是不存在对应版本的正版应用 2.二次打包应用 3.渠道信息不同但代码相同的正版应用 4.纯正版应用  5.钓鱼应用 6.盗版应用,7正版的不相关应用 8 正版的渠道信息不同的不相关应用
public class AnalysisUtil {
 private static String packageName,versionCode,versionName,dexHashName,dexHashCode,apkHashName,apkHashCode,analysisReport;
// private static HashMap<String,List<String>> processHash = new HashMap<String,List<String>>();
 private  List<String> processList = new ArrayList<String>();
 private  List<String> similarityList = new ArrayList<String>();
 private  List<String> deleteList = new ArrayList<String>();
 private static int result;
 private static int count;
 public  String dirPath;
 public  String getDirPath() {
	return dirPath;
}
public  void setDirPath(String dirPath) {
	this.dirPath = dirPath;
}
public  void dealTheApk(int type,String taskId,String dirPath){
	  setDirPath(dirPath);
	  boolean isFinish  = false;
       if(type==0||type==1){ 
       processList = getShellEcho(processList,"info.sh "+type+" "+dirPath);
       result = analysis(processList,type);
       }
       
       if(result==0&&type==0)
       {
           try {
			dealDifferentPackage(packageName,taskId);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
       //相似度分析的情况
       else if(((result==1||result==2||result==0)&&type==1)||(result==0&&type==2)){
           similarityList = getShellEcho(similarityList,"analysis.sh "+dirPath);
           System.out.println("alan" + similarityList.get(1));
           if( similarityList.get(1).equals( "seems that the two apks is not related")){
        	   result = 5;//钓鱼应用
        		analysisReport = "钓鱼应用";
           }

           else{
        	   result = 6;//盗版应用  
        		analysisReport = "盗版应用";
           }
           isFinish = true;
           similarityList.clear(); //清空
       }
       else
       {
       isFinish = true;
       }
       if (isFinish)
       {
    	   processList.clear(); //清空
    	   deleteList = getShellEcho(deleteList,"delete.sh  "+type+" "+dirPath);
    	   deleteList.clear();//清空
	       System.out.println("finish");
	       JSONObject json = new JSONObject();
	       GetConfigure getConfigure = new GetConfigure();
 	       json.put("agentId", getConfigure.getName());
 	      json.put("subtaskId",taskId );
 	       json.put("status", 4);
 	       if(result==1||result==3||result==4)
 	    	   result=0;
 	       else if(result==7||result==8)
 	    	   result=1;
 	       else if(result==2||result==6)
 	         result=2;
 	       else
 	    	  result=3;
 	       json.put("result", result);
 	       json.put("analysisReport",analysisReport);
 	      SendResult sendResult=new SendResult();
 	      sendResult.send(json.toString());
 	      System.out.println(json.toString());
       }
    }
    public static int  analysis(List<String> processList,int type){
    	 System.out.println("白名单信息");
         String apkName = processList.get(0);
         System.out.println("the apkName is "+apkName);
         String apkNameList[] = apkName.split(" ");
         int length = apkNameList.length;
         System.out.println("the length is "+length);
         ApkInfo  []apkInfoList = new ApkInfo[length+1];
         String hashCodeList[] = processList.get(4).split(",");
         for(int i = 1;i<length*2;i++){ //apk and dex
         	hashCodeList[i]=hashCodeList[i].substring(1);
         }
         for (int i = 0;i<length ; i++) {
         	apkInfoList[i] = new ApkInfo();
         	apkInfoList[i].setApkName(apkNameList[i]);
         }
          for (int i =0;i<length;i++)
           {
         	 dexHashName = hashCodeList[i].substring(0, hashCodeList[i].indexOf("."))+".apk";
         	 dexHashCode = hashCodeList[i].substring(hashCodeList[i].indexOf(" ")+1);
            	 apkHashName = hashCodeList[i+length].substring(0, hashCodeList[i+length].indexOf(" "));
            	 apkHashCode = hashCodeList[i+length].substring(hashCodeList[i+length].indexOf(" ")+1);
               for(int j=0;j<length;j++){
             	 if(apkInfoList[j].getApkName().equals(dexHashName)){
             		 apkInfoList[j].setDexHashCode(dexHashCode);
             		 break;
             	 }
                }
               for(int j=0;j<length;j++){
              	 if(apkInfoList[j].getApkName().equals(apkHashName)){
              		 apkInfoList[j].setApkHashCode(apkHashCode);
              		 break;
              	 }
                 }
            }
         String packageList[] = processList.get(1).split("package: ");
       
         for (int i = 0;i<length ; i++) {
         	System.out.println(apkNameList[i]);
             System.out.println( apkInfoList[i].getApkHashCode());
         	packageName = packageList[i+1].substring(5,packageList[i+1].indexOf("versionCode"));
        	   System.out.println(packageName);
            apkInfoList[i].setPackageName(packageName);
            versionCode =  packageList[i+1].substring(packageList[i+1].indexOf("versionCode")+12,packageList[i+1].indexOf("versionName"));
            System.out.println(versionCode);
            apkInfoList[i].setVersionCode(versionCode);
            versionName=packageList[i+1].substring(packageList[i+1].indexOf("versionName")+12);
            System.out.println(versionName);
            apkInfoList[i].setVersionName(versionName);
            apkInfoList[i].setApkType("clientApk");
            System.out.println( apkInfoList[i].getDexHashCode());
         }
         System.out.println("带检测apk信息");
         String channelHash = processList.get(5);
    	    dexHashCode = channelHash.substring(channelHash.indexOf(" ")+1,channelHash.indexOf(","));
    	    apkHashCode=channelHash.substring(channelHash.lastIndexOf(" ")+1);
         apkInfoList[length] = new ApkInfo();
   	    apkInfoList[length].setDexHashCode(dexHashCode);
 	    apkInfoList[length].setApkHashCode(apkHashCode);
          String channelName = processList.get(2);
          System.out.println(channelName);
    	     System.out.println(apkHashCode);
          apkInfoList[length].setApkName(channelName);
          String channelpackageList = processList.get(3);
          packageName = channelpackageList.substring(14,channelpackageList.indexOf("versionCode"));
      	  System.out.println(packageName);
          apkInfoList[length].setPackageName(packageName);
          versionCode =  channelpackageList.substring(channelpackageList.indexOf("versionCode")+12,channelpackageList.indexOf("versionName"));
          System.out.println(versionCode);
          apkInfoList[length].setVersionCode(versionCode);
          versionName=channelpackageList.substring(channelpackageList.indexOf("versionName")+12);
          System.out.println(versionName);
          apkInfoList[length].setVersionName(versionName);
     	 System.out.println(dexHashCode);
     	 apkInfoList[length].setApkType("channelApk");
     	 int status = 0;
     	
     	 for(int i = 0;i<length;i++){
     	if(apkInfoList[length].getApkHashCode().equals(apkInfoList[i].getApkHashCode()))
     	{
     		if(type==1)
     			status=7;
     			else;
     		status=4;
     		break;
     	}
     	else
     		 if(apkInfoList[length].getPackageName().equals(apkInfoList[i].getPackageName())){
     			if(apkInfoList[length].getVersionCode().equals(apkInfoList[i].getVersionCode())) {
                      if(apkInfoList[length].getDexHashCode().equals(apkInfoList[i].getDexHashCode())){
                    	  if(type==1)
                     		  status=8;
                    	  else
                     	     status=3;
                     	 break;
                      }
                      else{
                     	 status=2;
                     	 break;
                      }
     			}
     			else
     				if(apkInfoList[length].getDexHashCode().equals(apkInfoList[i].getDexHashCode()))
     				{
     					status=2;
         			    break;
     				
     				}
     				else{
         				status=1;
    					continue;
     				}
     		 }
     		 else
     			 break;
     	 }
     	 if(status==0)
     	 {
     		 System.out.println("没有与之相同的包名，需要进行包名不同情况下的相似度分析");
     	      packageName  = apkInfoList[length].getPackageName().substring(1, apkInfoList[length].getPackageName().lastIndexOf("'"));  
     	 }
     	 else if(status==1){
     		 System.out.println("疑似二次打包应用，也有可能是不存在对应版本的正版应用");
     		analysisReport = "疑似二次打包应用，也有可能是不存在对应版本的正版应用";
     	 }

     	 else if(status==2){
     		 System.out.println("二次打包应用");
     		 analysisReport = "二次打包应用";
     	 }

     	 else if(status==3){
     		 System.out.println("渠道信息不同但代码相同的正版应用");
     	    analysisReport = "渠道信息不同但代码相同的正版应用";
     	 }
     	 else if(status==4){
     		 System.out.println("纯正版应用");
     	     analysisReport = "纯正版应用";
     	 }
     	 else if(status==7){
     		 System.out.println("正版的不相关应用");
     	     analysisReport = "正版的不相关应用";
     	 }
     	 else if(status==8){
     		 System.out.println("正版的渠道信息不同的不相关应用");
     	     analysisReport = "正版的渠道信息不同的不相关应用";
     	 }
     	 return status;
    }
    
      public  void dealDifferentPackage(String packageName,String taskId) throws UnsupportedEncodingException{
    	      	  //String url = "http://localhost:8080/Client/search?package="+packageName;
          List<String> makeDirList = new ArrayList<String>();
          makeDirList = getShellEcho(makeDirList,"makeDir.sh  0 "+dirPath);  
    	   String url = "http://asec.buptnsrc.com:8000/whitelist/"+packageName;
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
    		if(!hashList.isEmpty())
    			{
    			List<String> clientUrl = new ArrayList<String>();
    			for(HashMap hashmap : hashList){
    				Iterator iter = hashmap.entrySet().iterator();
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
    			String urlhost = "http://dfs.asec.buptnsrc.com/";
    			String clientpath = "/home/hit_alan/zhoufandi/somefilebetweenlinuxandwindows/secondclientapk/";
    			for(int i = 0;i<clientUrl.size();i++){
    				HttpClientUtils.getInstance().download(urlhost+clientUrls[i], clientpath+dirPath+"/secondclient"+i+".apk",new HttpClientDownLoadProgress() {
    					public void onProgress(int progress) {
    						if(progress==100){
    							System.out.println("download client progress = " + progress);	
    							count++;
    							System.out.println("the count is "+count);
    						}    
    					}
    				});
    			}
    			while(count<=clientUrl.size()){
    				System.out.println("the other count is"+count);
    				if(count==clientUrl.size()){
    					count = 0;
    		   			dealTheApk(1,taskId,dirPath);//查询对应的包 返回对应结果 但是要防止再次出现包名不同的情况
    		   			break;
    				}
    			}
    			}
    		else{
    			       dealTheApk(2,taskId,dirPath);//搜索后发现提供的相关的包名为空，进行下面的操作。
    		}
      }

      
      public  List<String> getShellEcho(List<String> list,String sh){
      Process process = null;
      try {
      	String shellPath = "/home/hit_alan/";
          process = Runtime.getRuntime().exec("sh " +shellPath+sh);
          BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
          String line = "";
          while ((line = input.readLine()) != null) {
              list.add(line);
          }
          input.close();
          process.destroy();
      } catch (IOException e) {
          e.printStackTrace();
      }
    for (String line :list) {
          System.out.println(line);
      }
     return list;
      }
    }
