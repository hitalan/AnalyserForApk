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

import org.hit.util.HttpUtil;
import org.hit.util.MessageInfo;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
       response.getWriter().append("Served at: ").append(request.getContextPath());
		String packageName = request.getParameter("package");
		System.out.println("we all ready find the pacakage"+packageName);
	   //search send again
		HttpUtil util = new HttpUtil();
		MessageInfo app = new MessageInfo();
		// app.setTaskId("task001");
		 app.setSubtaskId("task001");
		 //app.setChannelId("channel001");
		 
		 Map<String,String> clientmap; 
	     List<HashMap<String, String>> clientsAppUrlList = new ArrayList<HashMap<String,String>>();
	     String urls[] = new String [2];
	     urls[1] = URLEncoder.encode("http://localhost:8080/Client/download?fileName=newUIchangeActivity.apk&filePath=/home/hit_alan/sendAPK/","UTF-8");
	     urls[0] = URLEncoder.encode("http://localhost:8080/Client/download?fileName=newUIchangeXml.apk&filePath=/home/hit_alan/sendAPK/","UTF-8");
	     for(int i = 0;i<urls.length;i++){
	    	 clientmap = new HashMap<String,String>();
		     clientmap.put("url", urls[0]);
		     clientmap.put("hash", "hash"+i+1);
	    	 clientsAppUrlList.add((HashMap<String, String>) clientmap);
	     }
	    /* List<String> clientsAppUrlList = new ArrayList<String>();
	     String urls[] = new String [1];
	     urls[0] = URLEncoder.encode("http://localhost:8080/Client/download?fileName=newUIchangeActivity.apk&filePath=/home/hit_alan/sendAPK/","UTF-8");
	     //urls[0] = URLEncoder.encode("http://localhost:8080/Client/download?fileName=newUIchangeXml.apk&filePath=/home/hit_alan/sendAPK/","UTF-8");
	     for(int i = 0;i<urls.length;i++){
	    	 clientsAppUrlList.add(urls[i]);
	     }*/
	     System.out.println("urls.length is "+urls.length);
	     app.setClientsAppUrls(clientsAppUrlList);
		 String info = util.post("http://localhost:8080/Server/secondReceiveTask",app);
		 System.out.println(info+"is the info");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
