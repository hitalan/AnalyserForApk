package org.hit.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hit.util.ApkInfo;
import org.hit.util.HttpUtil;

import com.google.gson.Gson;

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
		HttpUtil util = new HttpUtil();
		ApkInfo app = new ApkInfo();
		 app.setTaskId("task001");
		 app.setChannelId("channel001");
	     app.setChannelsAppUrl(URLEncoder.encode("http://10.108.115.251:8080/Client/download?fileName=牛客.apk&filePath=E:\\sendAPk\\","UTF-8"));
	     List<String> clientsAppUrlList = new ArrayList<String>();
	     String urls[] = new String [2];
	     urls[0] = URLEncoder.encode("http://10.108.115.251:8080/Client/upload?fileName=中国联航.apk&filePath=E:\\sendAPK\\","UTF-8");
	     urls[1] = URLEncoder.encode("http://10.108.115.251:8080/Client/upload?fileName=蚂蜂窝自由行.apk&filePath=E:\\sendAPK\\","UTF-8");
	     for(int i = 0;i<urls.length;i++){
	    	 clientsAppUrlList.add(urls[i]);
	     }
	     System.out.println("urls.length is "+urls.length);
	     app.setClientsAppUrls(clientsAppUrlList);
		 String info = util.post("http://10.108.115.188:8080/Server/receiveTask",app);
		 System.out.println(info+"is the info");
		 request.setAttribute("info", info);
		 request.getRequestDispatcher("index.jsp").forward(request, response);
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
