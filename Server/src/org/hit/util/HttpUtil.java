package org.hit.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
public class HttpUtil {
	static GetConfigure getConfigure=new GetConfigure();
public static  String getIp() {
			final String port=getConfigure.getPort();
	    	String result = "";
	    	Enumeration allNetInterfaces;
			try {
				allNetInterfaces = NetworkInterface.getNetworkInterfaces();
				InetAddress ip = null;
		    	while (allNetInterfaces.hasMoreElements())
		    	{
		    	NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
		    	Enumeration addresses = netInterface.getInetAddresses();
		    		while (addresses.hasMoreElements())
		    		{
		    			ip = (InetAddress) addresses.nextElement();
		    			if (ip != null && ip instanceof Inet4Address&&ip.getHostAddress().substring(0, 6).equals("10.108"))
		    			{
		    				System.out.println("本机的IP = " + ip.getHostAddress());
		    				result = ip.getHostAddress().replace(".", "_")+":"+port;
		    			} 
		    		}
		    	}
				} catch (SocketException e) {
					e.printStackTrace();
					result = "";
				}
			finally{
				return result;
			}
	   }

	  public static void download(String localfile,String url,String fileName/*,String filePath*/){
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url); 
			/*List<NameValuePair> formparams = new ArrayList<NameValuePair>();
				formparams.add(new BasicNameValuePair("fileName", fileName));
			    formparams.add(new BasicNameValuePair("filePath", filePath));*/
				//UrlEncodedFormEntity uefEntity;
				/*	try {*/
					   /*uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
						post.setEntity(uefEntity);*/
						CloseableHttpResponse response;
						try {
							response = client.execute(post);
							InputStream is;
							is = response.getEntity().getContent();
							File file = new File(localfile+fileName);
							if(!file.exists()){
								file.createNewFile();
							}
							OutputStream os = new FileOutputStream(file);
							int read = 0;
							byte[] temp = new byte[1024*1024];
								while((read=is.read(temp))>0){
									byte[] bytes = new byte[read];
									System.arraycopy(temp, 0, bytes, 0, read);
									os.write(bytes);
									}
								os.flush();
								os.close();
						   } catch (ClientProtocolException e) {
							e.printStackTrace();
						   } catch (IOException e) {
							e.printStackTrace();
						   }
					  /*  } catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}*/
			  System.out.println("Done");
	  }
	  /*public static String post(String url,String filePath,String fileName) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url); 
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("filePath",filePath));
		formparams.add(new BasicNameValuePair("fileName", fileName));
		UrlEncodedFormEntity uefEntity;
		String result =""; 
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					System.out.println("--------------------------------------");
					result =  EntityUtils.toString(entity, "UTF-8");
					System.out.println("Response content: " +result);
					System.out.println("--------------------------------------");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}*/
	  
	  public static String requestByGetMethod(String urlString){
	        CloseableHttpClient httpClient =  HttpClients.createDefault();
	        String result = "";
	        int statuCode = 0;
	        try {
	            HttpGet get = new HttpGet(urlString);
	            System.out.println("执行get请求:...."+get.getURI());
	            CloseableHttpResponse httpResponse = null;
	            httpResponse = httpClient.execute(get);
	            try{
	                //response实体
	                HttpEntity entity = httpResponse.getEntity();
	                if (null != entity){
	                	int  line =  httpResponse.getStatusLine().getStatusCode();
	                    System.out.println("响应状态码:"+ httpResponse.getStatusLine());
	                     result = EntityUtils.toString(entity);
	                     System.out.println("响应内容为"+result);
	                }
	            }
	            finally{
	                httpResponse.close();
	            }
	        }
	        catch (Exception e) {
	        	System.out.println("异常被捕捉到了");
	        	result =  "bad get due to the server statuscode is  "+statuCode;
	           // e.printStackTrace();
	        }
	        finally{
	            try{
	            	httpClient.close();
	            } catch (IOException e){
	                e.printStackTrace();
	            }
	        }
	        return result;
	  }
	  
		private static final String APPLICATION_JSON = "application/json";
		private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
		public static String httpPostWithJSON(String url, String json) throws Exception {
	        // 将JSON进行UTF-8编码,以便传输中文
	        //String encoderJson = URLEncoder.encode(json, "UTF-8");
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(url);
	        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
	        StringEntity se = new StringEntity(json);
	        se.setContentType(CONTENT_TYPE_TEXT_JSON);
	        se.setContentType(APPLICATION_JSON);
	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
	        httpPost.setEntity(se);
	        CloseableHttpResponse response  =  httpClient.execute(httpPost);
	        int code = response.getStatusLine().getStatusCode();
	        HttpEntity entity = response.getEntity();
	        String responseContent = getRespString(entity)+" and the responsecode is"+code;
	        return responseContent;
		}
		
		private static String getRespString(HttpEntity entity) throws Exception {
			if (entity == null) {
				return null;
			}
			InputStream is = entity.getContent();
			StringBuffer strBuf = new StringBuffer();
			byte[] buffer = new byte[4096];
			int r = 0;
			while ((r = is.read(buffer)) > 0) {
				strBuf.append(new String(buffer, 0, r, "UTF-8"));
			}
			return strBuf.toString();
		}
}
