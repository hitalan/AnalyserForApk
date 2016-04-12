package org.hit.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
public class AnalysisUtil {
	
 private static String packageName,versionCode,versionName,dexHashName,dexHashCode,apkHashName,apkHashCode;
 public static void dealTheApk(int type){
  
       List<String> processList = new ArrayList<String>();
       processList = getShellEcho(processList,"info.sh "+type);
       int result = analysis(processList);
       if(result==0)
       {
    	  /* List<String> deleteList = new ArrayList<String>();
           deleteList = getShellEcho(deleteList,"delete.sh  deleteClient");*/
           dealDifferentPackage(packageName);
           /*List<String> checkNewPackageList = new ArrayList<String>();
           checkNewPackageList = getShellEcho(checkNewPackageList,"info.sh");
           int secondResult = analysis(checkNewPackageList);
           System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! the secondResult is"+secondResult);
            if(secondResult==1||secondResult==2){
                List<String> similarityList = new ArrayList<String>();
                similarityList = getShellEcho(similarityList,"analysis.sh");	
            }
            else
            	System.out.println("finish");*/
       }
       else if((result==1||result==2)&&type==1){
    	   List<String> similarityList = new ArrayList<String>();
           similarityList = getShellEcho(similarityList,"analysis.sh");	
           List<String> deleteList = new ArrayList<String>();
           deleteList = getShellEcho(deleteList,"delete.sh "+type);
    	   System.out.println("finish");
       }
       else{
       List<String> deleteList = new ArrayList<String>();
       deleteList = getShellEcho(deleteList,"delete.sh  "+type);
	   System.out.println("finish");
       }
    }
 
 
 
    public static int  analysis(List<String> processList){
    	 System.out.println("白名单信息");
         String apkName = processList.get(0);
         String apkNameList[] = apkName.split(" ");
         int length = apkNameList.length;
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
     		status=4;
     		break;
     	}
     	else
     		 if(apkInfoList[length].getPackageName().equals(apkInfoList[i].getPackageName())){
     			if(apkInfoList[length].getVersionCode().equals(apkInfoList[i].getVersionCode())) {
                      if(apkInfoList[length].getDexHashCode().equals(apkInfoList[i].getDexHashCode())){
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
     	 else if(status==1)
     		 System.out.println("疑似二次打包应用，也有可能是不存在对应版本的正版应用");
     	 else if(status==2)
     		 System.out.println("二次打包应用");
     	 else if(status==3)
     		 System.out.println("渠道信息不同但代码相同的正版应用");
     	 else
     		 System.out.println("纯正版应用");
     	 return status;
    }
      public static void dealDifferentPackage(String packageName){
    	      	  String url = "http://localhost:8080/Client/search?package="+packageName;
    	      	  HttpUtil.requestByGetMethod(url);
      }
      
      public static void checkSimilarity(){
    	  
    	  
    	  
      }
      
      public static List<String> getShellEcho(List<String> list,String sh){
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
