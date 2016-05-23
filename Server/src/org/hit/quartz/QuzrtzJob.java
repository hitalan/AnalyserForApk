package org.hit.quartz;
import java.util.ArrayList;
import java.util.List;
import org.hit.util.GetConfigure;
import org.hit.util.HttpUtil;
import org.hit.util.ShellUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
public class QuzrtzJob implements Job {
 /**
  * 需要定时调度的方法
  */
public static int i;
public static List <String> list = new ArrayList<String>();
GetConfigure getConfigure=new GetConfigure();
 public void execute(JobExecutionContext arg0) throws JobExecutionException {
 	try {
		i++;
         //String url = "http://docker.buptnsrc.com:1563/receiveTask";
		 String url = "http://localhost:8080/Server/receiveTask";
         String result =  HttpUtil.requestByGetMethod(url);
      	 System.out.println("the task still remain for "+ShellUtil.getShellEcho(list, "checkTaskNum.sh "+getConfigure.getDownloadChannelPath()).get(0));
      	 System.out.println("we have already do the result for"+i+"times");
         System.out.println("-------------------------------------------------");                    
         System.out.println("-------------------------------------------------");                    
         list.clear();
	} catch (Exception e) {
		e.printStackTrace();
	}
 }
}
