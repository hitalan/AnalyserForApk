package org.hit.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hit.util.MessageInfo;
import org.apache.log4j.Logger;
import org.hit.util.AnalysisUtil;
import org.hit.util.DealException;
import org.hit.util.GetConfigure;
import org.hit.util.HttpClientUtils;
import org.hit.util.getInfoByRedis;
import org.hit.util.HttpClientUtils.HttpClientDownLoadProgress;
import com.google.gson.Gson;
public class ReceiveServlet extends HttpServlet{
	private static Logger logger = Logger.getLogger(ReceiveServlet.class);  
	public static boolean isBad;
	public static int failtimes;
	public static String failTaskId;
	private static final long serialVersionUID = 6731251846319286501L;
	public ReceiveServlet() {
		super();
	}
	public void destroy() {
		super.destroy(); 
	}
	public  int count;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Object> apkinfo =  getInfoByRedis.getInfo();
		GetConfigure getConfigure=new GetConfigure();
		logger.info("the apkinfo from the task is "+apkinfo.get(0));
		System.out.println("the apkinfo from the task is "+apkinfo.get(0));
		if(apkinfo.get(0).toString().equals("[]")){
			 logger.info("we have no task");	
			   System.out.println("we have no task");	
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.print("结束");
				out.flush();
				out.close();  
		}
		else
		{
		Gson gson = new Gson();
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
	    	 channelUrl = URLDecoder.decode((String)entry.getValue(),"UTF-8");
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
		    	clientUrl.add( URLDecoder.decode((String)entry.getValue(),"UTF-8"));
		    }
		    }
		    }
		System.out.println("clientUrl.size() is " + clientUrl.size());
		//logger.info("clientUrl.size() is " + clientUrl.size());
		String [] clientUrls = new String[clientUrl.size()];
		boolean isClientUrlEmptyTrue =  false;
		for(int i = 0;i<clientUrl.size();i++){
			clientUrls[i] = clientUrl.get(i);
			if(clientUrls[i].equals(""))
				 isClientUrlEmptyTrue =  true;
		}
		
		if(channelUrl.equals("")||isClientUrlEmptyTrue){
			 new DealException().	sendWrongAnswerByEmptyUrl(taskId);
		}
		else{
		System.out.println("the taskId is "+taskId+" the channelUrl "+channelUrl+" the url is "+clientUrl.get(0));
		//logger.info("the taskId is "+taskId+" the channelUrl "+channelUrl+" the url is "+clientUrl.get(0));
		String channelUrlInfo = channelUrl;
		String channelFileName = "channel.apk";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String dirPath = 	df.format(new Date());
        List<String> makeDirList = new ArrayList<String>();
        makeDirList =new AnalysisUtil().getShellEcho(makeDirList,"makeDir.sh  0  "+getConfigure.getAnalyzerPath()+" "+dirPath);
        HttpClientUtils.taskId=taskId;
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
		,"channelapk");
		int emptyClientUrl = 0;
		for(int i = 0;i<clientUrl.size();i++){
			if(clientUrls[i].equals(""))
			{
				emptyClientUrl++;
				logger.error("there are empty url in the clienturls");
				if(emptyClientUrl==clientUrl.size()){
					new DealException().sendWrongAnswerByEmptyUrl(taskId);
					try {
							Thread.sleep(3000);
					} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
				   count=0;
			       List<String> deleteList = new ArrayList<String>();
					 deleteList = new AnalysisUtil().getShellEcho(deleteList,"delete.sh  "+getConfigure.getAnalyzerPath()+"  0  "+dirPath);
				}
			}
				else{
			HttpClientUtils.getInstance().download(urlhost+clientUrls[i], clientpath+dirPath+"/client"+i+".apk",new HttpClientDownLoadProgress() {
				public void onProgress(int progress) {
				//System.out.println("the badClientCount is "+HttpClientUtils.badClientCount);
					if(progress==100){
						System.out.println("download client progress = " + progress);
						logger.info("download client progress = " + progress);	
						count++;
					}    
				}
			},"clientapk");
			}
		}
	while(count<=clientUrl.size()+1)
		{
			try {
				Thread.sleep(1000);
				System.out.println("the count is "+count);
				  if(HttpClientUtils.badClientCount==clientUrl.size()||HttpClientUtils.badChannelCount==1/*||emptyClientUrl==clientUrl.size()*/)
				  {
				     HttpClientUtils.badChannelCount=0;
				     HttpClientUtils.badClientCount=0;
				  	logger.error("we finish this analyze because the bad 404 or bad 502 download");
					System.out.println("we finish this analyze because the bad 404 or bad 502 download");
				/*	if(failtimes==0){
						failTaskId=taskId;
						failtimes++;
					}  //第一次失败的时候把taskid存在对应failtaskid
					else{//第二次失败的时候跳到后面的循环 如果和上一次保存的failtaskid一致则增加失败次数 如果和id不同则重置为0 重新设置新的taskid值
						if(failTaskId.equals(taskId))
							failtimes++;
						else{
							failtimes=0;
						    failTaskId=taskId;
						    failtimes++;
						}
						if(failtimes>=3){//当失败的次数超过限制 则放弃该任务重新做下一个任务
						     apkinfo =  getInfoByRedis.getInfo();
							logger.error("the bad apkinfo from the task is "+apkinfo.get(0));
							System.out.println("the bad apkinfo from the task we delete  is "+apkinfo.get(0));
							failtimes=0;
						}
					}*/
					 new DealException().sendWrongAnswerBy404(taskId);
					Thread.sleep(3000);
					 count=0;
				     List<String> deleteList = new ArrayList<String>();
				     deleteList = new AnalysisUtil().getShellEcho(deleteList,"delete.sh  "+getConfigure.getAnalyzerPath()+"  0  "+dirPath);
				      break;
				  }			  
				  else
				  {
					  if(count<=clientUrl.size()+1&&count>=2){
						  logger.info("finish download task");
						  System.out.println("finish download task");
							Thread.sleep(1000);
						   count=0;
						   HttpClientUtils.badClientCount=0;
						   HttpClientUtils.badChannelCount=0;
									response.setCharacterEncoding("UTF-8");
									PrintWriter out = response.getWriter();
									out.print("下载成功");
									out.flush();
									out.close();  
									new AnalysisUtil().dealTheApk(0,taskId,dirPath);
							break;
			            }
				  }
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	}
	}
	public void init() throws ServletException {
	}
}