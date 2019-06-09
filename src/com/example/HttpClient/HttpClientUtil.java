package com.example.HttpClient;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

//需要引入的jar包
//compile('org.apache.httpcomponents:httpclient:4.5.6')
//compile('org.apache.httpcomponents:httpcore:4.4.3')

/**
 * @program: webservice_demo
 * @description: HttpClient工具类
 * @author: miaoqixin
 * @create: 2018-11-28 16:39
 **/
public class HttpClientUtil {

	private final String TOKEN_BEARER = "Bearer ";
	private CloseableHttpClient httpClient;

    public HttpClientUtil() {
        // 1 创建HttpClinet，相当于打开浏览器
        httpClient = HttpClients.createDefault();
    }

    /* *
     * get请求
     * @author miaoqixin
     * @date 2018/11/28 16:51
     * @param [url, map]
     * @return HttpResult
     */
    public HttpResult doGet(String url, Map<String, Object> map , final String TOEKN) throws Exception {

        // 声明URIBuilder
        URIBuilder uriBuilder = new URIBuilder(url);

        // 判断参数map是否为非空
        if (map != null) {
            // 遍历参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                // 设置参数
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 2 创建httpGet对象，相当于设置url请求地址
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader("Authorization", TOKEN_BEARER+TOEKN);

        // 3 使用HttpClient执行httpGet，相当于按回车，发起请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            HttpResult httpResult = new HttpResult();
            httpResult.setCode(404);
            httpResult.setBody("请求失败");
            return httpResult;
        }

        // 4 解析结果，封装返回对象httpResult，相当于显示相应的结果
        // 状态码
        // response.getStatusLine().getStatusCode();
        // 响应体，字符串，如果response.getEntity()为空，下面这个代码会报错,所以解析之前要做非空的判断
        // EntityUtils.toString(response.getEntity(), "UTF-8");
        HttpResult httpResult = new HttpResult();
        // 解析数据封装HttpResult
        if (response.getEntity() != null) {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(),EntityUtils.toString(response.getEntity(),"UTF-8"));
            httpResult.setCode(response.getStatusLine().getStatusCode());
            httpResult.setBody(EntityUtils.toString(response.getEntity(),"UTF-8"));

        } else {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(), "");
            httpResult.setCode(response.getStatusLine().getStatusCode());
            //httpResult.setBody("");
        }

        // 返回
        return httpResult;
    }



    /* *
     * post请求
     * @author miaoqixin
     * @date 2018/11/28 18:13
     * @param [url, map]
     * @return com.example.HttpClient.HttpResult
     */
    public HttpResult doPost(String url, Map<String, Object> map , final String TOEKN) throws Exception {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization",  TOKEN_BEARER+TOEKN);

        // 判断map不为空
        if (map != null) {
            // 声明存放参数的List集合
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // 遍历map，设置参数到list中
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }

            // 创建form表单对象
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");

            // 把表单对象设置到httpPost中
            httpPost.setEntity(formEntity);
        }

        // 使用HttpClient发起请求，返回response
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            HttpResult httpResult = new HttpResult();
            httpResult.setCode(404);
            httpResult.setBody("请求失败");
            return httpResult;
        }

        // 解析response封装返回对象httpResult
        HttpResult httpResult = new HttpResult();
        // 解析数据封装HttpResult
        if (response.getEntity() != null) {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(),EntityUtils.toString(response.getEntity(),"UTF-8"));
            httpResult.setCode(response.getStatusLine().getStatusCode());
            httpResult.setBody(EntityUtils.toString(response.getEntity(),"UTF-8"));

        } else {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(), "");
            httpResult.setCode(response.getStatusLine().getStatusCode());
            //httpResult.setBody("");
        }

        // 返回结果
        return httpResult;
    }



    /* *
     * Put请求
     * @author miaoqixin
     * @date 2018/11/28 18:14
     * @param [url, map]
     * @return com.example.HttpClient.HttpResult
     */
    public HttpResult doPut(String url, Map<String, Object> map , final String TOEKN) throws Exception {
        // 声明httpPost请求
        HttpPut httpPut = new HttpPut(url);
        httpPut.addHeader("Authorization",  TOKEN_BEARER+TOEKN);
        // 判断map不为空
        if (map != null) {
            // 声明存放参数的List集合
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // 遍历map，设置参数到list中
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 创建form表单对象
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");

            // 把表单对象设置到httpPost中
            httpPut.setEntity(formEntity);
        }

        // 使用HttpClient发起请求，返回response
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPut);
        } catch (IOException e) {
            HttpResult httpResult = new HttpResult();
            httpResult.setCode(404);
            httpResult.setBody("请求失败");
            return httpResult;
        }

        // 解析response封装返回对象httpResult
        HttpResult httpResult = new HttpResult();
        // 解析数据封装HttpResult
        if (response.getEntity() != null) {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(),EntityUtils.toString(response.getEntity(),"UTF-8"));
            httpResult.setCode(response.getStatusLine().getStatusCode());
            httpResult.setBody(EntityUtils.toString(response.getEntity(),"UTF-8"));

        } else {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(), "");
            httpResult.setCode(response.getStatusLine().getStatusCode());
            //httpResult.setBody("");
        }

        // 返回结果
        return httpResult;
    }

    /* *
     *  Delete请求
     * @author miaoqixin
     * @date 2018/11/28 18:20
     * @param [url, map]
     * @return com.example.HttpClient.HttpResult
     */
    public HttpResult doDelete(String url, Map<String, Object> map , final String TOEKN) throws Exception {

        // 声明URIBuilder
        URIBuilder uriBuilder = new URIBuilder(url);

        // 判断参数map是否为非空
        if (map != null) {
            // 遍历参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                // 设置参数
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 2 创建httpGet对象，相当于设置url请求地址
        HttpDelete httpDelete = new HttpDelete(uriBuilder.build());
        httpDelete.addHeader("Authorization",  TOKEN_BEARER+TOEKN);
        // 3 使用HttpClient执行httpGet，相当于按回车，发起请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpDelete);
        } catch (IOException e) {
            HttpResult httpResult = new HttpResult();
            httpResult.setCode(404);
            httpResult.setBody("请求失败");
            return httpResult;
        }
        // 4 解析结果，封装返回对象httpResult，相当于显示相应的结果
        // 状态码
        // response.getStatusLine().getStatusCode();
        // 响应体，字符串，如果response.getEntity()为空，下面这个代码会报错,所以解析之前要做非空的判断
        // EntityUtils.toString(response.getEntity(), "UTF-8");
        HttpResult httpResult = new HttpResult();
        // 解析数据封装HttpResult
        if (response.getEntity() != null) {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(),EntityUtils.toString(response.getEntity(),"UTF-8"));
            httpResult.setCode(response.getStatusLine().getStatusCode());
            httpResult.setBody(EntityUtils.toString(response.getEntity(),"UTF-8"));
        } else {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(), "");
            httpResult.setCode(response.getStatusLine().getStatusCode());
            //httpResult.setBody("");
        }
        // 返回
        return httpResult;
    }
    
    public static String sendFilesPost(String url, String fileNames) {
    	   HttpClient httpClient = null;
    	   HttpPost httpPost;
    	   String result = null;
    	   try {
    	      httpClient = new DefaultHttpClient();
    	      httpPost = new HttpPost(url);

    	      String[] filenames=fileNames.split(";");
    	      MultipartEntity reqEntity = new MultipartEntity();
    	      for(int i=0;i<filenames.length;i++) {
    	               String fileName=filenames[i];
    	               FileBody file = new FileBody(new File(fileName));

    	               reqEntity.addPart("file", file);// file1为请求后台的File upload;属性

    	       }
    	      httpPost.setEntity(reqEntity);
    	      HttpResponse response = httpClient.execute(httpPost);
    	      if (null != response && response.getStatusLine().getStatusCode() == 200) {
    	         HttpEntity resEntity = response.getEntity();
    	         if (null != resEntity) {
    	            result = EntityUtils.toString(resEntity, HTTP.UTF_8);
    	            System.out.println(result);
    	         }
    	      }
    	   } catch (UnsupportedEncodingException e) {
    	      e.printStackTrace();
    	   } catch (ClientProtocolException e) {
    	      e.printStackTrace();
    	   } catch (IOException e) {
    	      e.printStackTrace();
    	   } catch (Exception e) {
    	      e.printStackTrace();
    	   } finally {
    	      // 关闭连接，释放资源
    	      httpClient.getConnectionManager().shutdown();
    	   }
    	   return result;
    	}
}