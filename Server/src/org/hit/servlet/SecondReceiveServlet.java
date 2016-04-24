package org.hit.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import org.hit.util.AnalysisUtil;
import org.hit.util.HttpUtil;
import org.hit.util.MessageInfo;

import com.google.gson.Gson;

/**
 * Servlet implementation class SecondReceiveServlet
 */
public class SecondReceiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SecondReceiveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String str =  request.getParameter("str");
	/*	Gson gson = new Gson();
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        // 将资料解码
        String reqBody = sb.toString();
        String result = URLDecoder.decode(reqBody, "UTF-8");
		MessageInfo info = gson.fromJson(result, MessageInfo.class);
		String taskId = info.getSubtaskId();
		//String channelId = info.getChannelId();
		//String channelUrl = URLDecoder.decode(info.getChannelsAppUrl(),"UTF-8");
		List<HashMap<String,String>> hashList = info.getClientsApp();
	if(hashList!=null)
		{
		List<String> clientUrl = new ArrayList<String>();
		for(HashMap hashmap : hashList){
			Iterator iter = hashmap.entrySet().iterator();
			while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
		    Object key = entry.getKey();
		    if(key.equals("url")){
		    	clientUrl.add( URLDecoder.decode((String)entry.getValue(),"UTF-8"));
		    }
		    }
		    }
	/*	List<String> clientUrl = info.getClientsAppUrls();
		for(int i = 0;i<clientUrl.size();i++){
			clientUrl.set(i,URLDecoder.decode(clientUrl.get(i),"UTF-8"));
		}*/
	/*	System.out.println("clientUrl.size() is " + clientUrl.size());
		String [] clientUrls = new String[clientUrl.size()];
		String [] clientFileName = new String[clientUrl.size()];
		String [] clientFilePath = new String [clientUrl.size()];
		for(int i = 0;i<clientUrl.size();i++){
			clientUrls[i] = clientUrl.get(i).substring(0,clientUrl.get(i).lastIndexOf("?"));
			clientFileName[i] = clientUrl.get(i).substring(clientUrl.get(i).indexOf("=")+1,clientUrl.get(i).lastIndexOf("&"));
 			clientFilePath[i] = clientUrl.get(i).substring(clientUrl.get(i).lastIndexOf("=")+1);
		}
		//System.out.println("the taskId is "+taskId+" the chanelId is "+channelId +" the channelUrl "+channelUrl+" the url is "+clientUrl.get(0));
		for(int i = 0;i<clientUrl.size();i++){
			HttpUtil.download("/home/hit_alan/zhoufandi/somefilebetweenlinuxandwindows/secondclientapk/", clientUrls[i] , clientFileName[i], clientFilePath[i]);
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("再次下载成功");
		out.flush();
		out.close();
		AnalysisUtil.dealTheApk(1);
		}
	
	else 
	{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("再次下载成功");
		out.flush();
		out.close();
		AnalysisUtil.dealTheApk(1);

	}
*/
	}

}
