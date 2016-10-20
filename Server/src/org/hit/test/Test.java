package org.hit.test;

import org.hit.util.GetConfigure;

public class Test {
public static void main(String args[]){
	 GetConfigure getConfigure=new GetConfigure();
	     String url = "";
		 url = getConfigure.getQuartzJobUrl();
		 String s = getConfigure.getAnalyzerPath();
		 System.out.println("url is" + url+s);
	 }
}
