package org.hit.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hit.util.BadTaskReDoUtil;

/**
 * Servlet implementation class BadTaskReDoServlet
 */
public class BadTaskReDoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BadTaskReDoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int remainFailTask = -1;
		try{
		 remainFailTask = new BadTaskReDoUtil().reDoBadTask();
		} catch(Exception e)
		{
			System.out.println("there are exception in the analyses");
		}
		if(remainFailTask==-1)
		response.getWriter().append("Served at: ").append(request.getContextPath()+"there are errors in the analyzer");
		else
		response.getWriter().append("Served at: ").append(request.getContextPath()+"and the remain failed analyzer  task is"+remainFailTask);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
