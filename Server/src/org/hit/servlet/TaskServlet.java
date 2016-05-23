package org.hit.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hit.util.GetConfigure;
import org.hit.util.ShellUtil;

/**
 * Servlet implementation class TaskServlet
 */
public class TaskServlet extends HttpServlet {
	public static List <String> list = new ArrayList<String>();
	GetConfigure getConfigure=new GetConfigure();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// System.out.println("the task still remain for "+ShellUtil.getShellEcho(list, "checkTaskNum.sh "+getConfigure.getDownloadChannelPath()).get(0));
		response.getWriter().append("Served at: ").append(request.getContextPath()+"the task is remain "+ShellUtil.getShellEcho(list, "checkTaskNum.sh "+getConfigure.getDownloadChannelPath()).get(0)+" tasks");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
