package org.hit.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.hit.util.HttpUtil;

import net.sf.json.JSONObject;

public class DataBaseTest {
	private static Logger logger = Logger.getLogger(DataBaseTest.class);  
	public static void main(String[] args) throws SQLException {
        Connection conn=null;
        Connection conn_1 = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt_1 = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://asec.buptnsrc.com:13306/asec", "root",
                    "123456");
            conn_1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/adresult","root","598086");
            stmt = conn.prepareStatement("SELECT  subtask_id,app_name, app_version,  app_url_bin  FROM asec_analyze_record as a, asec_app_meta as b where a.app_meta_id = b.app_meta_id and a.result = ? ");
            int type = 10;
            stmt.setInt(1, type);
            rs = stmt.executeQuery();
            String subtask_id,app_name,app_version,app_url_bin;
            while (rs.next()) {
            	subtask_id = rs.getString("subtask_id");
                app_name = rs.getString("app_name");
            	app_version=rs.getString("app_version");
            	app_url_bin=rs.getString("app_url_bin");
                System.out.println(app_name);
                System.out.println("----------------------------");
                stmt_1  = conn_1.prepareStatement("insert into adresult (app_subtaskid,app_name,app_version,app_url_bin,app_result_type) values(?,?,?,?,?)");
                stmt_1.setString(1,subtask_id );
                stmt_1.setString(2, app_name);
                stmt_1.setString(3, app_version);
                stmt_1.setString(4, app_url_bin);
                if(type==8||type==7||type==6)
                    stmt_1.setString(5, "二次打包应用");
                else if(type==9)
                    stmt_1.setString(5, "盗版应用");
                else
                	 stmt_1.setString(5, "钓鱼应用");
                stmt_1.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }
    }
  	public  static void  testAD(String channelPathInDfs,String taskId){
		String url ="http://10.108.114.233:8889/addetector/"+taskId;
		JSONObject jsonObject=new JSONObject();
		if(channelPathInDfs==null||channelPathInDfs=="")
		{
			 System.out.println("the taskId "+taskId+" url is bad ");
		}
		jsonObject.put("location", channelPathInDfs);
		jsonObject.put("port", "7777");
		jsonObject.put("path", "/ADDetector/advertise/");
		System.out.println(jsonObject.toString());
        logger.info("the ad analyse we send url  is "+channelPathInDfs);
		try {
			String adSendResult = HttpUtil.httpPostWithJSON(url, jsonObject.toString());
	        System.out.println("post send result is "+adSendResult);
	      logger.info("the ad analyse post result is "+adSendResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
