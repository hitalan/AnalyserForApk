package org.hit.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
public class ShellUtil {
    public static List<String> strList = new ArrayList<String>();
    private static Logger logger = Logger.getLogger(ShellUtil.class);  
    public static  List<String> getShellEcho(List<String> list,String sh){
    Process process = null;
    try {
    	GetConfigure getConfigure=new GetConfigure();
    	String shellPath =getConfigure.getShellPath();
    	 System.out.println("the shellpath is "+shellPath);
        process = Runtime.getRuntime().exec("sh " +shellPath+sh);
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = input.readLine()) != null) {
            list.add(line);
        }
        input.close();
        process.destroy();
    } catch (IOException e) {
        e.printStackTrace();
    }
  for (String line :list) {
  	 logger.info("the task remain  "+line);
    }
   return list;
    }
}
