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

import com.google.gson.Gson;

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
		String packageName = request.getParameter("package");
		System.out.println("we all ready find the pacakage"+packageName);
	   //search send again
		MessageInfo app = new MessageInfo();
		 Map<String,String> clientmap; 
		 //如果查询结果不为空：则
	     List<HashMap<String, String>> clientsAppUrlList = new ArrayList<HashMap<String,String>>();
	     String urls[] = new String [2];
	     urls[0] = URLEncoder.encode("app3/M00/38/21/wKgCAVaEKHSAJ_yLAARp7OGEqXo7644838","UTF-8");
	     urls[1] = URLEncoder.encode("app7/M00/CB/CB/wKhkBFbPr5GAYNXMAOhxbnhHbsY4348423","UTF-8");
	     for(int i = 0;i<urls.length;i++){
	    	 clientmap = new HashMap<String,String>();
		     clientmap.put("url", urls[i]);
		     clientmap.put("hash", "hash"+i);
	    	 clientsAppUrlList.add((HashMap<String, String>) clientmap);
	     }
	     System.out.println("urls.length is "+urls.length);
	     app.setClientsApp(clientsAppUrlList);
		response.setCharacterEncoding("UTF-8");
		  PrintWriter out = response.getWriter();
	       Gson gson = new Gson();
		   out.print(gson.toJson(app));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
