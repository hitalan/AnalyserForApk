package org.hit.util;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hit.servlet.ReceiveServlet;
import org.hit.util.HttpClientUtils.HttpClientDownLoadProgress;
import com.google.gson.Gson;
public class DownLoadUtil {
	private static Logger logger = Logger.getLogger(ReceiveServlet.class);
	private int count;
	private boolean isBad;
	private int whileTime;
    int size;
	Counter counter = new Counter();
	public boolean download(List<Object> apkinfo){		
		Gson gson = new Gson();
		GetConfigure getConfigure=new GetConfigure();
		MessageInfo[] info = gson.fromJson(apkinfo.get(0).toString(), MessageInfo[].class);
		String urlhost = getConfigure.getDownloadUrl();
		String channelpath = getConfigure.getDownloadChannelPath();
		String clientpath =getConfigure.getDownloadClientPath();
		String taskId = info[0].getSubtaskId();
		 String channelUrl = "";
		HashMap <String ,String> hash =  info[0].getChannelApp();
		Iterator iter = hash.entrySet().iterator();
		while (iter.hasNext()) {
		Map.Entry entry = (Map.Entry) iter.next();
	    Object key = entry.getKey();
	    if(key.equals("url")){
	    	 try {
				channelUrl = URLDecoder.decode((String)entry.getValue(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	    }
		}
		List<HashMap<String,String>> hashList = info[0].getClientsApp();
		final List<String> clientUrl = new ArrayList<String>();
		for(HashMap hashmap : hashList){
			iter = hashmap.entrySet().iterator();
			while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
		    Object key = entry.getKey();
		    if(key.equals("url")){
		    	try {
					clientUrl.add( URLDecoder.decode((String)entry.getValue(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
		    }
		    }
		    }
		System.out.println("clientUrl.size() is " + clientUrl.size());
		String [] clientUrls = new String[clientUrl.size()];
		boolean isClientUrlEmptyTrue =  false;
		int emptyClientUrl = 0;
		for(int i = 0;i<clientUrl.size();i++){
			clientUrls[i] = clientUrl.get(i);
			if(clientUrls[i].equals(""))
			{
				emptyClientUrl++;
				logger.error("there are empty url in the clienturls");
			}
		}
		if(emptyClientUrl==clientUrl.size()){
			 isClientUrlEmptyTrue =  true;
		}
		if(channelUrl.equals("")||isClientUrlEmptyTrue||clientUrl.size()==0){
			 DealException.sendFailInfo(taskId, "clientapk or channelapk url is empty");
			 isBad = true;
		}
		else
		{
		System.out.println("the taskId is "+taskId+" the channelUrl "+channelUrl+" the url is "+clientUrl.get(0));
		String channelUrlInfo = channelUrl;
		String channelFileName = "channel.apk";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");//设置日期格式
        String time = 	df.format(new Date());
        String dirPath = taskId;
		logger.info("the folder we make in the engine is"+time +"the folder name is"+taskId);
        List<String> makeDirList = new ArrayList<String>();
        makeDirList =new AnalysisUtil().getShellEcho(makeDirList,"makeDir.sh  0  "+getConfigure.getAnalyzerPath()+" "+dirPath);
		HttpClientUtils.getInstance().download(urlhost+channelUrlInfo, channelpath+dirPath+"/channel.apk",new HttpClientDownLoadProgress() { 
			  public void onProgress(int progress) 
			  {
				  if(progress==100)
				  {
						logger.info("download channel progress = " + progress);
						System.out.println("download channel progress = " + progress);
				       count++;
				  }
			  }
		}
		,"channelapk",counter);
		if(counter.getBadChannelCount()==1){
			
			
		}
		else{

	   			if(clientUrl.size()>=2)//白名单最多下载两个  这是很精妙的地方
	   				size = 2;
	   			else
	   				size = clientUrl.size();
			for(int i = 0;i<size;i++){
				if(clientUrls[i].equals(""))
				{

				}
				else{
					HttpClientUtils.getInstance().download(urlhost+clientUrls[i], clientpath+dirPath+"/client"+i+".apk",new HttpClientDownLoadProgress() {
						public void onProgress(int progress) {
							if	(progress==100){
								System.out.println("download client progress = " + progress);
								logger.info("download client progress = " + progress);	
								count++;
							}    
						}
					},"clientapk",counter);
				}
		    }	
		}
		while(count<=size+1-counter.getBadClientCount()-emptyClientUrl)
			{
			try {
					Thread.sleep(1000);
					whileTime++;
					System.out.println("the subtaskid is "+taskId+" the count is "+count);
					System.out.println("the subtaskid is "+taskId+" whileTimes is "+whileTime);
				  if(counter.getBadClientCount()==size||counter.getBadChannelCount()==1)
				  {
				  	logger.error("we finish this analyze because the bad 404 or bad 502 download");
					System.out.println("we finish this analyze because the bad 404 or bad 502 download");
					 DealException.sendFailInfo(taskId, "404 or 502 for download the apk");
                     isBad = true;
				     break;
				  }			  
				  else
				  {
					  if(count==size+1-counter.getBadClientCount()-emptyClientUrl){
						  logger.info("finish download task");
						  System.out.println("finish download task");
							Thread.sleep(2000);
						   isBad = false;
						   try
						   {
								new AnalysisUtil().dealTheApk(0,taskId,dirPath);
						   }catch(Exception e){
							   System.out.println("there are bad apk xml error happened when they are analyzed");
							   logger.error("there are bad apk xml error happened when they are analyzed and the taskId is "+taskId );
						   }
							break;
			            }
					  else if(whileTime>=100){
							 DealException.sendFailInfo(taskId, "download time out ");
							 isBad = true;
							 break;
					  }
				  }
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
        return isBad;
	}
	
}
