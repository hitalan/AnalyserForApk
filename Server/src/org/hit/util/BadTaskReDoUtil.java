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
	   if(!iter.hasNext()||list.size()==1){
		   System.out.println("there are no bad analyzer task ");
		   logger.error("there are no bad analyzer task");
		   return unDoCount;
	   }
	   else
	   {
		   unDoCount = list.size();
		   System.out.println("the undo fail analyzer task is "+unDoCount);
		   logger.error("the undo fail analyzer task is "+unDoCount);
		   String temp;
		   	while(iter.hasNext()){  
		   		temp = iter.next();
			    System.out.println("the folder name is "+temp);
			    logger.error("the undo fail folder name is "+temp);
    		} 
	   		int remain;
	   				remain = list.size() ;
	   				System.out.println("we still remain bad analyzer task for"+remain);
	   				logger.error("we still remain bad analyzer task for "+remain);
		   
	   				try 
	   				{
	   					new AnalysisUtil().dealTheApk(0,list.get(0),list.get(0));//抽取出taskId和channelUrlInfo
	   				} catch (Exception e) {
	   					e.printStackTrace();
						System.out.println("***************************exception happend in the analyzer server");
					}
	   		return unDoCount;
	   } 
 }
}
