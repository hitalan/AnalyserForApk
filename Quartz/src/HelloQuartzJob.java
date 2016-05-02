import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloQuartzJob implements Job {
public static int i;
    public void execute(JobExecutionContext context) 
            throws JobExecutionException {
    	try {
    		i++;
	         String url = "http://docker.buptnsrc.com:1563/receiveTask";
    		//String url = "http://localhost:8080/Server/receiveTask";
             String result =  HttpUtil.requestByGetMethod(url);
	      	 System.out.println("alan ok"+result);
	      	 System.out.println("we have already do the result for"+i+"times");
	      	 //System.out.println("the task still remain for "+runShell("sh  /home/hit_alan/shellforserver/checkTaskNum.sh ").get(0));
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       /* System.out.println("Hello, Quartz! - executing its JOB at "+ 
            new Date() + " by " + context.getTrigger().getName());*/
    }
    
	 public static List<String>  runShell(String shStr) throws Exception {  
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
	          return strList;
	       /* for (String lines : strList) {  
	            System.out.println(lines);  
	        }  */
	    } 
}