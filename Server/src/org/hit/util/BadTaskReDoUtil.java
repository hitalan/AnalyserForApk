package org.hit.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class BadTaskReDoUtil {
	private static Logger logger = Logger.getLogger(BadTaskReDoUtil.class);  
	public static List<String> list = new  ArrayList<String>();
	static GetConfigure getConfigure=new GetConfigure();
 public int reDoBadTask(){
	   int unDoCount = 0;
	   list.clear();
		ShellUtil.getShellEcho(list, "getUnDoTask.sh "+getConfigure.getDownloadChannelPath());
	    Iterator<String>  iter = list.iterator();
	   if(!iter.hasNext()){
		   System.out.println("there are no bad analyzer task ");
		   logger.error("there are no bad analyzer task");
		   return unDoCount;
	   }
	   else
	   {
		   unDoCount = list.size();
		   logger.error("the undo fail analyzer task is "+unDoCount);
		   String temp;
		   	while(iter.hasNext()){  
		   		temp = iter.next();
			    System.out.println("the folder name is "+temp);
			    logger.error("the undo fail folder name is "+temp);
    		} 
	   		int remain,index;
	   		for (int i = 0; i<list.size()-1;i++)  //默认当前最新的一个正常的分析任务不必执行这个操作
	   		{
		    remain = list.size() -  i;
		   System.out.println("we still remain bad analyzer task for"+remain);
		   logger.error("we still remain bad analyzer task for "+remain);
		   index =  list.get(i).indexOf("app");
	    	//	new AnalysisUtil().dealTheApk(0,list.get(i).substring(0, index),list.get(i),list.get(i).substring(index+1));//抽取出taskId和channelUrlInfo
		   new AnalysisUtil().dealTheApk(0,list.get(i),list.get(i),null);//抽取出taskId和channelUrlInfo
			try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   		}
	   		return unDoCount;
	   } 
 }
}
