package org.hit.util;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
public class ShellUtil {
	 public static void  runShell(String shStr) throws Exception {  
	        List<String> strList = new ArrayList<String>();  
	        Process process;  
	        process = Runtime.getRuntime().exec(shStr);  
	        InputStreamReader ir = new InputStreamReader(process.getInputStream());  
	        LineNumberReader input = new LineNumberReader(ir);  
	        String line;  
	        process.waitFor();  
	        while ((line = input.readLine()) != null){  
	            strList.add(line);  
	        }  
	          
	        for (String lines : strList) {  
	            System.out.println(lines);  
	        }  
	    } 
}
