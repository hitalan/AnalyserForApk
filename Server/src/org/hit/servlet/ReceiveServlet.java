package org.hit.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hit.util.MessageInfo;
import org.hit.util.AnalysisUtil;
import org.hit.util.FTPUtil;
import org.hit.util.HttpUtil;
import org.hit.util.ShellUtil;

import com.google.gson.Gson;
public class ReceiveServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ReceiveServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String str =  request.getParameter("str");
		System.out.println("alan "+str);
	    Gson gson = new Gson();
		MessageInfo info = gson.fromJson(str, MessageInfo.class);
		String taskId = info.getSubtaskId();
		 String channelUrl = "";
		HashMap <String ,String> hash =  info.getChannelsAppUrl();
		Iterator iter = hash.entrySet().iterator();
		while (iter.hasNext()) {
		Map.Entry entry = (Map.Entry) iter.next();
	    Object key = entry.getKey();
	    if(key.equals("url")){
	    	 channelUrl = URLDecoder.decode((String)entry.getValue(),"UTF-8");
	    }
		}
		//String channelUrl = URLDecoder.decode(info.getChannelsAppUrl(),"UTF-8");
		List<HashMap<String,String>> hashList = info.getClientsAppUrls();
		List<String> clientUrl = new ArrayList<String>();
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
	    /*for(int i = 0;i<clientUrl.size();i++){
			clientUrl.set(i,URLDecoder.decode(clientUrl.get(i),"UTF-8"));
		}*/
		System.out.println("clientUrl.size() is " + clientUrl.size());
		String [] clientUrls = new String[clientUrl.size()];
		String [] clientFileName = new String[clientUrl.size()];
		String [] clientFilePath = new String [clientUrl.size()];
		for(int i = 0;i<clientUrl.size();i++){
			
			clientUrls[i] = clientUrl.get(i).substring(0,clientUrl.get(i).lastIndexOf("?"));
			clientFileName[i] = clientUrl.get(i).substring(clientUrl.get(i).indexOf("=")+1,clientUrl.get(i).lastIndexOf("&"));
 			clientFilePath[i] = clientUrl.get(i).substring(clientUrl.get(i).lastIndexOf("=")+1);
		}
		System.out.println("the taskId is "+taskId+" the channelUrl "+channelUrl+" the url is "+clientUrl.get(0));
		String channelUrlInfo = channelUrl.substring(0, channelUrl.lastIndexOf('?'));
		System.out.println("the channelurlinfo is "+channelUrlInfo);
		String channelFileName = channelUrl.substring(channelUrl.indexOf("=")+1,channelUrl.lastIndexOf("&") );
		System.out.println("the channelFileName is "+channelFileName);
		String channelFilePath = channelUrl.substring(channelUrl.lastIndexOf("=")+1);
		System.out.println("the channelFilePath is "+channelFilePath);
		HttpUtil.download("/home/hit_alan/zhoufandi/somefilebetweenlinuxandwindows/channelapk/", channelUrlInfo, channelFileName, channelFilePath);
		for(int i = 0;i<clientUrl.size();i++){
			HttpUtil.download("/home/hit_alan/zhoufandi/somefilebetweenlinuxandwindows/clientapk/", clientUrls[i] , clientFileName[i], clientFilePath[i]);
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("下载成功");
		out.flush();
		out.close();  
	    AnalysisUtil.dealTheApk(0);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
