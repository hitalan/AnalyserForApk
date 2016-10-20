package org.hit.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hit.util.GetConfigure;
import org.hit.util.HttpUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class BadAnalyzerJob implements Job{
	private static Logger logger = Logger.getLogger(BadAnalyzerJob.class);  
	static GetConfigure getConfigure=new GetConfigure();
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		 System.out.println("执行失败任务的调度 ，当前时间为："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SS").format(new Date()));
		logger.info("执行失败任务的调度 ，当前时间为："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SS").format(new Date()));
		 String url =  getConfigure.getBadAnalyzerJobUrl();
         String result =  HttpUtil.requestByGetMethod(url);
         System.out.println(result);
	}

}
