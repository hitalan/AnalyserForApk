package org.hit.util;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
public class HttpClientUtils 
{
public static  String taskId;
public  int badClientCount;
public  int badChannelCount;
public static final int THREAD_POOL_SIZE = 100;
private static Logger logger = Logger.getLogger(HttpClientUtils.class);  
	public interface HttpClientDownLoadProgress {
		public void onProgress(int progress);
	}
	private static HttpClientUtils httpClientDownload;
	private ExecutorService downloadExcutorService;
	private HttpClientUtils() {
		downloadExcutorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}
	public static HttpClientUtils getInstance() {
		if (httpClientDownload == null) {
			httpClientDownload = new HttpClientUtils();
		}
		return httpClientDownload;
	}
	public void download( final String url, final String filePath,final String type,final Counter counter) {
		downloadExcutorService.execute(new Runnable() {
			public void run() {
				httpDownloadFile(url, filePath, null, null,type,counter);
			}
		});
	}
public void  download(final String url, final String filePath,
			final HttpClientDownLoadProgress progress,final String type,final Counter counter) {
		downloadExcutorService.execute(new Runnable() {
			public void run() {
				httpDownloadFile(url, filePath, progress, null,type,counter);
			}
		});
	}
	public  void httpDownloadFile(String url, String filePath,HttpClientDownLoadProgress progress, Map<String, String> headMap,String type,Counter counter) {
		    CloseableHttpClient httpclient = HttpClients.createDefault();
			badChannelCount  = counter.getBadChannelCount();
			badClientCount = counter.getBadClientCount();
		try {
			HttpGet httpGet = new HttpGet(url);
			setGetHead(httpGet, headMap);
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			try {
				System.out.println(response1.getStatusLine());
				if(response1.getStatusLine().getStatusCode()!=200)
				{
					if(type.equals("channelapk")){
							badChannelCount++;
							counter.setBadChannelCount(badChannelCount);
						    logger.error("there are "+response1.getStatusLine().getStatusCode()+" errors in the channel url");
							System.out.println("there are  "+response1.getStatusLine().getStatusCode()+" errors in the channel url");
					}
					else{
						badClientCount++;
						counter.setBadClientCount(badClientCount);
						logger.error("there are  "+response1.getStatusLine().getStatusCode()+" errors in the client url");
						System.out.println("there are  "+response1.getStatusLine().getStatusCode()+" errors in the client url");
					}
				}
				else
				{
				HttpEntity httpEntity = response1.getEntity();
				long contentLength = httpEntity.getContentLength();
				InputStream is = httpEntity.getContent();
				// 根据InputStream 下载文件
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				byte[] buffer = new byte[8192];
				int r = 0;
				long totalRead = 0;
				while ((r = is.read(buffer)) > 0) {
					output.write(buffer, 0, r);
					totalRead += r;
					if (progress != null) {// 回调进度
						progress.onProgress((int) (totalRead * 100 / contentLength));
					}
				}
				FileOutputStream fos = new FileOutputStream(filePath);
				output.writeTo(fos);
				output.flush();
				output.close();
				fos.close();
				EntityUtils.consume(httpEntity);
				}

			} finally {
				response1.close();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("stop the download due to the exception");
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public String httpGet(String url) {
		return httpGet(url, null);
	}


	public String httpGet(String url, Map<String, String> headMap) {
		String responseContent = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			setGetHead(httpGet, headMap);
			try {
				System.out.println(response1.getStatusLine());
				HttpEntity entity = response1.getEntity();
				responseContent = getRespString(entity);
				System.out.println("debug:" + responseContent);
				EntityUtils.consume(entity);
			} finally {
				response1.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	public String httpPost(String url, Map<String, String> paramsMap) {
		return httpPost(url, paramsMap, null);
	}
	public String httpPost(String url, Map<String, String> paramsMap,
			Map<String, String> headMap) {
		String responseContent = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			setPostHead(httpPost, headMap);
			setPostParams(httpPost, paramsMap);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				System.out.println(response.getStatusLine());
				HttpEntity entity = response.getEntity();
				responseContent = getRespString(entity);
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("responseContent = " + responseContent);
		return responseContent;
	}


	private void setPostParams(HttpPost httpPost, Map<String, String> paramsMap)
			throws Exception {
		if (paramsMap != null && paramsMap.size() > 0) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<String> keySet = paramsMap.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, paramsMap.get(key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		}
	}


	private void setPostHead(HttpPost httpPost, Map<String, String> headMap) {
		if (headMap != null && headMap.size() > 0) {
			Set<String> keySet = headMap.keySet();
			for (String key : keySet) {
				httpPost.addHeader(key, headMap.get(key));
			}
		}
	}

	private void setGetHead(HttpGet httpGet, Map<String, String> headMap){
		if (headMap != null && headMap.size() > 0) {
			Set<String> keySet = headMap.keySet();
			for (String key : keySet) {
				httpGet.addHeader(key, headMap.get(key));
			}
		}
	}

private String getRespString(HttpEntity entity) throws Exception {
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