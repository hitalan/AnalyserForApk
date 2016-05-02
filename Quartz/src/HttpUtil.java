import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
public class HttpUtil {
	public static String requestByGetMethod(String urlString){
        CloseableHttpClient httpClient =  HttpClients.createDefault();
        String result = "";
        try {
            HttpGet get = new HttpGet(urlString);
            System.out.println("执行get请求:...."+get.getURI());
            CloseableHttpResponse httpResponse = null;
            httpResponse = httpClient.execute(get);
            try{
                //response实体
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity){
                    System.out.println("响应状态码:"+ httpResponse.getStatusLine());
                    System.out.println("-------------------------------------------------");
                    result = EntityUtils.toString(entity, "UTF-8");
                    //System.out.println("响应内容:" + EntityUtils.toString(entity));
                    System.out.println("-------------------------------------------------");                    
             
                }
            }
            finally{
                httpResponse.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
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
}
