package org.hit.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hit.util.MessageInfo;
import org.hit.util.HttpUtil;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

public class SendServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SendServlet() {
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
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("subtaskId", "subtaskId");
        HashMap<String,String> channelmap  = new HashMap<String,String>();
		//channelmap.put("url", URLEncoder.encode("http://localhost:8080/Client/download?fileName=newUIchangeVersion.apk&filePath=/home/hit_alan/sendAPK/","UTF-8"));
        channelmap.put("url", URLEncoder.encode("app3/M00/38/21/wKgCAVaEKHSAJ_yLAARp7OGEqXo7644838","UTF-8"));
        channelmap.put("hash", "channelhash");
	    jsonObject.put("channelApp", channelmap);
	    Map<String,String> clientmap; 
	    List<HashMap<String, String>> clientsAppUrlList = new ArrayList<HashMap<String,String>>();
	    String urls[] = new String [2];
	    urls[1] = URLEncoder.encode("app5/M00/2F/85/Cmx6LlZ7rEyAS5J8ABxV-ZvVVKs9070081","UTF-8");
	    urls[0] = URLEncoder.encode("app0/M00/64/C0/wKgMAVcN81iAckh8AIg2kC8C2ig3365363","UTF-8");
	     for(int i = 0;i<urls.length;i++){
	    	 clientmap = new HashMap<String,String>();
		     clientmap.put("url", urls[i]);
		     clientmap.put("hash", "hash"+i+1);
	    	 clientsAppUrlList.add((HashMap<String, String>) clientmap);
	     }
	     jsonObject.put("clientsApp", clientsAppUrlList);
        try {
			HttpUtil.httpPostWithJSON("http://localhost:8080/Server/receiveTask", jsonObject.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
              
		
	/*	HttpUtil util = new HttpUtil();
		MessageInfo app = new MessageInfo();
		 app.setSubtaskId("task001");
		// app.setChannelId("channel001");
		 HashMap<String,String> channelmap  = new HashMap<String,String>();
		 channelmap.put("url", URLEncoder.encode("http://localhost:8080/Client/download?fileName=UIChangePackage.apk&filePath=/home/hit_alan/sendAPK/","UTF-8"));
	     channelmap.put("hash", "channelhash");
	     app.setChannelsAppUrl(channelmap);*/
		 //   app.setChannelsAppUrl(URLEncoder.encode("http://localhost:8080/Client/download?fileName=UIChangePackage.apk&filePath=/home/hit_alan/sendAPK/","UTF-8"));
	   /*  Map<String,String> clientmap; 
	     List<HashMap<String, String>> clientsAppUrlList = new ArrayList<HashMap<String,String>>();
	     String urls[] = new String [2];
	     urls[1] = URLEncoder.encode("http://localhost:8080/Client/download?fileName=UIBestPractice.apk&filePath=/home/hit_alan/sendAPK/","UTF-8");
	     urls[0] = URLEncoder.encode("http://localhost:8080/Client/download?fileName=UIChangeVersion.apk&filePath=/home/hit_alan/sendAPK/","UTF-8");
	     for(int i = 0;i<urls.length;i++){
	    	 clientmap = new HashMap<String,String>();
		     clientmap.put("url", urls[0]);
		     clientmap.put("hash", "hash"+i+1);
	    	 clientsAppUrlList.add((HashMap<String, String>) clientmap);
	     }
	     System.out.println("urls.length is "+urls.length);
	     app.setClientsAppUrls(clientsAppUrlList);
		 String info = util.post("http://localhost:8080/Server/receiveTask",app);
		 System.out.println(info+"is the info");
		 request.setAttribute("info", info);
		 request.getRequestDispatcher("index.jsp").forward(request, response);*/
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
