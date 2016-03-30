package org.hit.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hit.util.ApkInfo;
import org.hit.util.FTPUtil;
import org.hit.util.HttpUtil;

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
		Gson gson = new Gson();
		ApkInfo info = gson.fromJson(str, ApkInfo.class);
		String taskId = info.getTaskId();
		String channelId = info.getChannelId();
		String channelUrl = URLDecoder.decode(info.getChannelsAppUrl(),"UTF-8");
		List<String> clientUrl = info.getClientsAppUrls();
		for(int i = 0;i<clientUrl.size();i++){
			clientUrl.set(i,URLDecoder.decode(clientUrl.get(i),"UTF-8"));
		}
		System.out.println("clientUrl.size() is " + clientUrl.size());
		String [] clientUrls = new String[clientUrl.size()];
		String [] clientFileName = new String[clientUrl.size()];
		String [] clientFilePath = new String [clientUrl.size()];
		for(int i = 0;i<clientUrl.size();i++){
			
			clientUrls[i] = clientUrl.get(i).substring(0,clientUrl.get(i).lastIndexOf("?"));
			clientFileName[i] = clientUrl.get(i).substring(clientUrl.get(i).indexOf("=")+1,clientUrl.get(i).lastIndexOf("&"));
 			clientFilePath[i] = clientUrl.get(i).substring(clientUrl.get(i).lastIndexOf("=")+1);
		}
		System.out.println("the taskId is "+taskId+" the chanelId is "+channelId +" the channelUrl "+channelUrl+" the url is "+clientUrl.get(0));
		String channelUrlInfo = channelUrl.substring(0, channelUrl.lastIndexOf('?'));
		System.out.println("the channelurlinfo is "+channelUrlInfo);
		String channelFileName = channelUrl.substring(channelUrl.indexOf("=")+1,channelUrl.lastIndexOf("&") );
		System.out.println("the channelFileName is "+channelFileName);
		String channelFilePath = channelUrl.substring(channelUrl.lastIndexOf("=")+1);
		System.out.println("the channelFilePath is "+channelFilePath);
		
		
		//渠道下载采用http传送协议
		HttpUtil.download("D:\\downloadAPK\\", channelUrlInfo, channelFileName, channelFilePath);
		
		//客户上传的下载才用ftp协议
		for(int i = 0;i<clientUrl.size();i++){
			HttpUtil.post(clientUrls[i],clientFilePath[i], clientFileName[i]);
			try 
			{
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FTPUtil.downFile("10.109.252.36", 21, "student", "123456", "\\", clientFileName[i], "D:\\downloadAPK");
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("发送成功");
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
