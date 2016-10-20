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

import org.apache.log4j.Logger;
import org.hit.util.HttpClientUtils.HttpClientDownLoadProgress;
import org.json.JSONObject;
import com.google.gson.Gson;
public class AnalysisUtil{
private static Logger logger = Logger.getLogger(AnalysisUtil.class);  
GetConfigure getConfigure=new GetConfigure();
 private   String packageName,versionCode,versionName,dexHashName,dexHashCode,apkHashName,apkHashCode,analysisReport;
 private  List<String> processList = new ArrayList<String>();
 private  List<String> similarityList = new ArrayList<String>();
 private  List<String> deleteList = new ArrayList<String>();
 private  List<String> copyBadAppList = new ArrayList<String>();
 Counter counter = new Counter();
 private  int result;
private  int count;
 private int length;
 private ApkInfo  []apkInfoList;
 public  String dirPath;
 public boolean isBad;
// public String  channelUrlInfo;
 public  String getDirPath() {
	return dirPath;
}
public  void setDirPath(String dirPath) {
	this.dirPath = dirPath;
}
//public String getChannelUrlInfo() {
//	return channelUrlInfo;
//}
//public void setChannelUrlInfo(String channelUrlInfo) {
//	this.channelUrlInfo = channelUrlInfo;
//}
public  void dealTheApk(int type,String taskId,String dirPath){
	  setDirPath(dirPath);
	  //setChannelUrlInfo(channelUrlInfo);
	  boolean isFinish  = false;
       if(type==0){ 
       processList = getShellEcho(processList,"info.sh "+getConfigure.getAnalyzerPath()+" "+type+" "+dirPath);
       try{
           result = analysis(processList,type);
       }
       catch(Exception e){
    	   System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++exception happened in the analysis");
// 		   copyBadAppList = getShellEcho(copyBadAppList,"mvChannel.sh "+getConfigure.getDownloadChannelPath()+" "+dirPath+" "+3+" "+apkInfoList[length].getPackageName()+" "+taskId);
// 		   copyBadAppList .clear();//清空
    	   isBad = true;
    	   DealException.sendFailInfo(taskId, "exception happened in the analysis");
       }
       }
       else  if(type==1){
    	   processList.clear();//当计算新请求的包名版本号等一系列信息的时候需要将client和channel的信息进行清空 重新得到新的second目录下的元素信息
           processList = getShellEcho(processList,"info.sh "+getConfigure.getAnalyzerPath()+" "+type+" "+dirPath);
           try{
        	   result = analysis(processList,type);  
           } catch(Exception e){
        	   System.out.println("exception happened in the analysis");
//        	   copyBadAppList = getShellEcho(copyBadAppList,"mvChannel.sh "+getConfigure.getDownloadChannelPath()+" "+dirPath+" "+3+" "+apkInfoList[length].getPackageName()+" "+taskId);
//        	   copyBadAppList .clear();//清空
        	   isBad = true;
        	   DealException.sendFailInfo(taskId, "exception happened in the second  analysis");
           }
       }
       else
       {   
    	   System.out.println("do no info analyze"); //type 2 直接进行相似度分析就好
       }
       if(result==-1&&type==0)
       {
           try {
			dealDifferentPackage(packageName,taskId);
		   } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		   }
       }
       //相似度分析的情况
       else if(((result==-1||result==6||result==7||result==8)&&type==1)||(type==2))
       {
           similarityList = getShellEcho(similarityList,"analysis.sh "+ getConfigure.getAnalyzerPath()+" "+dirPath);
           if( similarityList.get(1).equals( "seems that the two apks is not related")){
        	   result = 10;//钓鱼应用
        		analysisReport = "钓鱼应用";
           }
           else{
        	   result = 9;//盗版应用  
        		analysisReport = "盗版应用";
           }
           isFinish = true;
           similarityList.clear(); //清空
       }
       else
       {
       isFinish = true;
       }
       if (isFinish&&!isBad)
       {
    	   String badType = null;
    	   if(result==6||result==7||result==8)
    		   badType = "0";
    	   else if (result==9)
    		   badType = "1";
    	   else if(result==10)
    		    badType ="2";
    	   if(badType!=null) //留存问题app到本地目录下
    	   {
    		 //  copyBadAppList = getShellEcho(copyBadAppList,"mvChannel.sh "+getConfigure.getDownloadChannelPath()+" "+dirPath+" "+badType+" "+channelPackageName+" "+taskId);
    		 //  copyBadAppList .clear();//清空
    		   processList.clear(); //清空
    	   }
           sendResult(taskId,type);
       }
    }
   public void sendResult(String taskId,int type){
 	      deleteList = getShellEcho(deleteList,"delete.sh  "+getConfigure.getAnalyzerPath()+" "+type+" "+dirPath);
 	      deleteList.clear();///清空
 	 	  logger.info("finish the analyze task");
 	 	  System.out.println("finish the analyze task");
	      JSONObject json = new JSONObject();
	      GetConfigure getConfigure = new GetConfigure();
	      json.put("agentId", getConfigure.getName()+HttpUtil.getIp());
	      json.put("subtaskId",taskId );
	      json.put("status", 4);
	      json.put("result", result);
	      json.put("analysisReport",analysisReport);
	      SendResult sendResult=new SendResult();
	      sendResult.send(json.toString());
	      logger.info("finish the send result task the result is"+ json.toString());
	      System.out.println("finish the send result task the result is"+ json.toString());
	   
	   
   }
   
    public  int  analysis(List<String> processList,int type){
    	 logger.info("白名单信息");
    	  System.out.println("白名单信息");
         String apkName = processList.get(0);
         logger.info("the apkName is "+apkName);
         System.out.println("the apkName is "+apkName);
         String apkNameList[] = apkName.split(" ");
         length = apkNameList.length;
        logger.info("the length is "+length);
         System.out.println("the length is "+length);
         apkInfoList = new ApkInfo[length+1];
         String hashCodeList[] = processList.get(6).split(",");
         String clientSignatureList[] = processList.get(1).split(" ");
         for(int i = 1;i<length*2;i++){ //apk and dex
         	hashCodeList[i]=hashCodeList[i].substring(1);
         }
         for (int i = 0;i<length ; i++) {
         	apkInfoList[i] = new ApkInfo();
         	apkInfoList[i].setApkName(apkNameList[i]);
         	apkInfoList[i].setSignaTure(clientSignatureList[i].substring(13));
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
         String packageList[] = processList.get(2).split("package: ");
       
         for (int i = 0;i<length ; i++) {
         	logger.info("the client apk name is "+apkNameList[i]);
        	 System.out.println("the client apk name is "+apkNameList[i]);
        	logger.info("the apk hash is "+apkInfoList[i].getApkHashCode());
        	 System.out.println("the apk hash is "+apkInfoList[i].getApkHashCode());
         	packageName = packageList[i+1].substring(5,packageList[i+1].indexOf("versionCode"));
         	logger.info("the packageName is "+packageName);
         	 System.out.println("the packageName is "+packageName);
         	logger.info("the apk signature is "+apkInfoList[i].getSignaTure());
         	 System.out.println("the apk signature is "+apkInfoList[i].getSignaTure());
            apkInfoList[i].setPackageName(packageName);
            versionCode =  packageList[i+1].substring(packageList[i+1].indexOf("versionCode")+12,packageList[i+1].indexOf("versionName"));
            logger.info("the apk versionCode is "+versionCode);
             System.out.println("the apk versionCode is "+versionCode);
            apkInfoList[i].setVersionCode(versionCode);
            versionName=packageList[i+1].substring(packageList[i+1].indexOf("versionName")+12);
           logger.info("the apkVersionName is "+versionName);
            System.out.println("the apkVersionName is "+versionName);
            apkInfoList[i].setVersionName(versionName);
            apkInfoList[i].setApkType("clientApk");
            logger.info("the apk dex hash is "+apkInfoList[i].getDexHashCode());
            System.out.println("the apk dex hash is "+apkInfoList[i].getDexHashCode());
         }
         
         logger.info( "带检测apk信息");
         System.out.println( "带检测apk信息");
         String channelHash = processList.get(7);
    	 dexHashCode = channelHash.substring(channelHash.indexOf(" ")+1,channelHash.indexOf(","));
    	 apkHashCode=channelHash.substring(channelHash.lastIndexOf(" ")+1);
         apkInfoList[length] = new ApkInfo();
         String channelSignature = processList.get(4).substring(13);
   	     apkInfoList[length].setDexHashCode(dexHashCode);
 	     apkInfoList[length].setApkHashCode(apkHashCode);
 	     apkInfoList[length].setSignaTure(channelSignature);;
         String channelName = processList.get(3);
         logger.info("the channel apk is "+channelName);
         System.out.println("the channel apk is "+channelName);
         logger.info("the apk hash code is "+apkHashCode);
         System.out.println("the apk hash code is "+apkHashCode);
          apkInfoList[length].setApkName(channelName);
          String channelpackageList = processList.get(5);
          packageName = channelpackageList.substring(14,channelpackageList.indexOf("versionCode"));
          logger.info("the apk package name is "+packageName);
          System.out.println("the apk package name is "+packageName);
          logger.info("the apk signature is "+apkInfoList[length].getSignaTure());
          System.out.println("the apk signature is "+apkInfoList[length].getSignaTure());
          apkInfoList[length].setPackageName(packageName);
          versionCode =  channelpackageList.substring(channelpackageList.indexOf("versionCode")+12,channelpackageList.indexOf("versionName"));
          logger.info("the versionCode is "+versionCode);
          System.out.println("the versionCode is "+versionCode);
          apkInfoList[length].setVersionCode(versionCode);
          versionName=channelpackageList.substring(channelpackageList.indexOf("versionName")+12);
          logger.info("the versionName is "+versionName);
          System.out.println("the versionName is "+versionName);
          apkInfoList[length].setVersionName(versionName);
          logger.info("the apk dex hash is "+dexHashCode);
          System.out.println("the apk dex hash is "+dexHashCode);
          apkInfoList[length].setApkType("channelApk");
     	 int status = -1;
     for(int i = 0;i<length;i++)
     {
     	if(apkInfoList[length].getApkHashCode().equals(apkInfoList[i].getApkHashCode()))
     	{
     		if(type==1)
     			status=5;
     		else
     		status=0;
     	}
     	else
     	{
     		 if(apkInfoList[length].getPackageName().equals(apkInfoList[i].getPackageName())){
     			 				if(apkInfoList[length].getSignaTure().equals(apkInfoList[i].getSignaTure())){
     			 						if(apkInfoList[length].getVersionCode().equals(apkInfoList[i].getVersionCode()))
     			 						{ 
     			 							if(type==1)
     			 								status=5;
     			 							else
     			 							status = 1;
     			 						}
     			 						else
     			 						{
     			 							if(type==1)
     			 								status=5;
     			 							else
     			 							status=2;
     			 						}
     			 					} 
     			 				else{
     			 							if(apkInfoList[length].getVersionCode().equals(apkInfoList[i].getVersionCode())) {
     			 												if(apkInfoList[length].getDexHashCode().equals(apkInfoList[i].getDexHashCode())){
     			 														if(type==1)
     			 													     status=5;
     			 														else
     			 														status=3;
     			 										              }
     			 										       else{
     			 											            status=7;
     			 										                }
     			                               }
     			 							else 
     			 							   {
     			 								if(apkInfoList[length].getDexHashCode().equals(apkInfoList[i].getDexHashCode()))
     				                               {
     					                                     status=6;
     				                                }
     				                       else{
         				                                  status=8;
     				                                }
     			 							    }
     		                          }
     			 								
     	}
     		 else
     		 {
      			if(apkInfoList[length].getSignaTure().equals(apkInfoList[i].getSignaTure()))
 						status = 4; 
     		 }
     }
     	/*int temp = 0; // 用来做status的标记位
     	if(i==0){
     		temp = status;
     	}
     	else{
     		if(temp<=status)
     			status=temp; //例如通过对后面的应用进行比对发现
     		else
     			temp = status;
     	}*/
     	if(status==-1&&type==0) //只有在发现包名不同、且签名不同的情况下。再对下一个进行检测，防止出现
     		//，误认为是不相关应用，而非后面的应用可以给出的正版应用信息 参考齐鲁银行可能白名单
     		//中第一个可能给出的是旧版本应用。则后面的可能是正版应用。这样就不会因为是第一个就
     		//包名不同，然后直接白名单了。确保所有的都是包名不同的情况下，再进行包名请求和分析
     		//对于type = 1的情况 因为第二次请求都是同包名的 所以检测一个就可以了 不需要continue
     		continue;
     	else
     		break;
    }
     	 if(status==-1)
     	 {
     	     logger.info("没有与之相同的包名，需要进行包名不同情况下的相似度分析");
     		 System.out.println("没有与之相同的包名，需要进行包名不同情况下的相似度分析");
     	      packageName  = apkInfoList[length].getPackageName().substring(1, apkInfoList[length].getPackageName().lastIndexOf("'"));  
     	 }
     	 else if(status==0){
     		 logger.info("纯正版应用");
     		 System.out.println("纯正版应用");
     		analysisReport = "纯正版应用";
     	 }

     	 else if(status==1){
     	    logger.info("版本相同的其他渠道正版应用");
     		 System.out.println("版本相同的其他渠道正版应用");
     		 analysisReport = "版本相同的其他渠道正版应用";
     	 }

     	 else if(status==2){
     		 logger.info("版本不同的正版应用");
     		 System.out.println("版本不同的正版应用");
     		 analysisReport = "版本不同的正版应用";
     	 }
     	 else if(status==3){
     		 logger.info("签名信息不同的正版应用");
     		 System.out.println("签名信息不同的正版应用");
     		 analysisReport = "签名信息不同的正版应用";
     	 }
     	 else if(status==4){
     		 logger.info("同公司旗下不相关应用");
     		 System.out.println("同公司旗下不相关应用");
     		 analysisReport = "同公司旗下不相关应用";
     	 }
     	 else if(status==5){
     	     logger.info("不相关应用");
     		 System.out.println("不相关应用");
     		 analysisReport = "不相关应用";
     	 }
     	 else if(status==6){
     		 logger.info("二次打包应用");
     		 System.out.println("二次打包应用");
     		 analysisReport="二次打包应用";
     	 }
     	 else if(status==7){
     		 logger.info("二次打包应用");
     		 System.out.println("二次打包应用");
     		 analysisReport="二次打包应用";
     	 }
     	 else if(status==8){
     		 logger.info("高度疑似二次打包应用");
     		 System.out.println("高度疑似二次打包应用");
     		 analysisReport="高度疑似二次打包应用";
     	 }
     	 return status;
    }
    
      public  void dealDifferentPackage(String packageName,String taskId) throws UnsupportedEncodingException{
          List<String> makeDirList = new ArrayList<String>();
          makeDirList = getShellEcho(makeDirList,"makeDir.sh  1 "+ getConfigure.getAnalyzerPath()+" "+dirPath);  
    	   String url = getConfigure.getWhiteListUrl()+packageName;
                 String result =   HttpUtil.requestByGetMethod(url);
      		   if(result.startsWith("bad get due to the server statuscode is"))
      		   {
      			 DealException.sendFailInfo(taskId, "bad connection with the taskmanager");;
      		   }
      			   
      		   else
      		   {
      			   	logger.info("we get the whitelist result of the diffrent package after we second post"+result);
      			   	System.out.println("we get the whitelist result of the diffrent package after we second post"+result);
      			   	Gson gson = new Gson();
      			   	try {
					result = URLDecoder.decode(result, "UTF-8");
      			   	} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
      			   	}
      			   	MessageInfo info = gson.fromJson(result, MessageInfo.class);
      			   	List<HashMap<String,String>> hashList = info.getClientsApp();
      			   	if(!hashList.isEmpty())
      			   	{
      			   		final List<String> clientUrl = new ArrayList<String>();
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
      			   			logger.info("clientUrl.size() is " + clientUrl.size());
      			   			String [] clientUrls = new String[clientUrl.size()];
      			   			for(int i = 0;i<clientUrl.size();i++){
      			   			clientUrls[i] = clientUrl.get(i);
      			   			}
      			   			String urlhost =getConfigure.getDownloadUrl();
      			   			String clientpath =getConfigure.getDownloadSecondClientPath();
      			   			int emptyClientUrl = 0;
      			   			int size;
      			   			if(clientUrl.size()>=1)//白名单最多下载1个  这是很精妙的地方
      			   				size = 1;
      			   			else
      			   				size = clientUrl.size();
      			   			for(int i = 0;i<size;i++)
      			   			{
      			   				if(clientUrls[i].equals(""))
      			   				{
      			   				emptyClientUrl++;
      			   				logger.error("there are empty urls in the client url list ");
      			   				}

      			   				else
      			   				{
      			   					HttpClientUtils.getInstance().download(urlhost+clientUrls[i], clientpath+dirPath+"/secondclient"+i+".apk",new HttpClientDownLoadProgress() {
      			   					public void onProgress(int progress) {
      			   						if(progress==100){
      			   							logger.info("download client progress = " + progress);
      			   							System.out.println();
      			   							count++;
      			   							}    
      			   							}
      			   				},"clientapk",counter);
      			   				}
      			   			}
      			   			while(count<=size-emptyClientUrl-counter.getBadClientCount()){
      			   				try {
      			   					Thread.sleep(1000);
      			   					if(counter.getBadClientCount()==size||emptyClientUrl==size)
      			   					{
      			   						dealTheApk(2,taskId,dirPath);//搜索后发现提供的下载链接全部失败或者全部为空进行下面的操作。
      			   						break;
      			   					}
      			   					else
      			   					{	  
      			   						if(count==size-emptyClientUrl-counter.getBadClientCount()){
      			   							logger.info("we already download the new apk from our second post");
      			   							System.out.println("we already download the new apk from our second post");
      			   							dealTheApk(1,taskId,dirPath);//查询对应的包 返回对应结果 但是要防止再次出现包名不同的情况
      			   							break;
      			   							}
      			   					}
      			   					} catch (InterruptedException e) {
      			   						e.printStackTrace();
      			   					}
      			   					}
    			}
      			else{
    			       dealTheApk(2,taskId,dirPath);//搜索后发现提供的相关的包名为空，进行下面的操作。
      		   }
      			   
      		   }
                
      }
      public  List<String> getShellEcho(List<String> list,String sh){
      Process process = null;
      try {
      	String shellPath =getConfigure.getShellPath();
      	 System.out.println("the shellpath is "+shellPath);
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
    	 logger.info("the shell result is "+line);
    	 System.out.println("the shell result is "+line);
      }
     return list;
      }
      
  	public  void  testAD(String channelPathInDfs,String taskId){
		String url =getConfigure.getAd_url()+taskId;
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("location", channelPathInDfs);
		jsonObject.put("port", getConfigure.getAd_receive_port());
		jsonObject.put("path", getConfigure.getAd_receive_path());
		System.out.println(jsonObject.toString());
		try {
			String adSendResult = HttpUtil.httpPostWithJSON(url, jsonObject.toString());
	        System.out.println("post send result is "+adSendResult);
	        logger.info("the ad analyse post result is"+adSendResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    }
