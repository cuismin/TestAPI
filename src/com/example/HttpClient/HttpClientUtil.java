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

//��Ҫ�����jar��
//compile('org.apache.httpcomponents:httpclient:4.5.6')
//compile('org.apache.httpcomponents:httpcore:4.4.3')

/**
 * @program: webservice_demo
 * @description: HttpClient������
 * @author: miaoqixin
 * @create: 2018-11-28 16:39
 **/
public class HttpClientUtil {

	private final String TOKEN_BEARER = "Bearer ";
	private CloseableHttpClient httpClient;

    public HttpClientUtil() {
        // 1 ����HttpClinet���൱�ڴ������
        httpClient = HttpClients.createDefault();
    }

    /* *
     * get����
     * @author miaoqixin
     * @date 2018/11/28 16:51
     * @param [url, map]
     * @return HttpResult
     */
    public HttpResult doGet(String url, Map<String, Object> map , final String TOEKN) throws Exception {

        // ����URIBuilder
        URIBuilder uriBuilder = new URIBuilder(url);

        // �жϲ���map�Ƿ�Ϊ�ǿ�
        if (map != null) {
            // ��������
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                // ���ò���
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 2 ����httpGet�����൱������url�����ַ
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader("Authorization", TOKEN_BEARER+TOEKN);

        // 3 ʹ��HttpClientִ��httpGet���൱�ڰ��س�����������
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            HttpResult httpResult = new HttpResult();
            httpResult.setCode(404);
            httpResult.setBody("����ʧ��");
            return httpResult;
        }

        // 4 �����������װ���ض���httpResult���൱����ʾ��Ӧ�Ľ��
        // ״̬��
        // response.getStatusLine().getStatusCode();
        // ��Ӧ�壬�ַ��������response.getEntity()Ϊ�գ������������ᱨ��,���Խ���֮ǰҪ���ǿյ��ж�
        // EntityUtils.toString(response.getEntity(), "UTF-8");
        HttpResult httpResult = new HttpResult();
        // �������ݷ�װHttpResult
        if (response.getEntity() != null) {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(),EntityUtils.toString(response.getEntity(),"UTF-8"));
            httpResult.setCode(response.getStatusLine().getStatusCode());
            httpResult.setBody(EntityUtils.toString(response.getEntity(),"UTF-8"));

        } else {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(), "");
            httpResult.setCode(response.getStatusLine().getStatusCode());
            //httpResult.setBody("");
        }

        // ����
        return httpResult;
    }



    /* *
     * post����
     * @author miaoqixin
     * @date 2018/11/28 18:13
     * @param [url, map]
     * @return com.example.HttpClient.HttpResult
     */
    public HttpResult doPost(String url, Map<String, Object> map , final String TOEKN) throws Exception {
        // ����httpPost����
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization",  TOKEN_BEARER+TOEKN);

        // �ж�map��Ϊ��
        if (map != null) {
            // ������Ų�����List����
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // ����map�����ò�����list��
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }

            // ����form������
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");

            // �ѱ��������õ�httpPost��
            httpPost.setEntity(formEntity);
        }

        // ʹ��HttpClient�������󣬷���response
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            HttpResult httpResult = new HttpResult();
            httpResult.setCode(404);
            httpResult.setBody("����ʧ��");
            return httpResult;
        }

        // ����response��װ���ض���httpResult
        HttpResult httpResult = new HttpResult();
        // �������ݷ�װHttpResult
        if (response.getEntity() != null) {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(),EntityUtils.toString(response.getEntity(),"UTF-8"));
            httpResult.setCode(response.getStatusLine().getStatusCode());
            httpResult.setBody(EntityUtils.toString(response.getEntity(),"UTF-8"));

        } else {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(), "");
            httpResult.setCode(response.getStatusLine().getStatusCode());
            //httpResult.setBody("");
        }

        // ���ؽ��
        return httpResult;
    }



    /* *
     * Put����
     * @author miaoqixin
     * @date 2018/11/28 18:14
     * @param [url, map]
     * @return com.example.HttpClient.HttpResult
     */
    public HttpResult doPut(String url, Map<String, Object> map , final String TOEKN) throws Exception {
        // ����httpPost����
        HttpPut httpPut = new HttpPut(url);
        httpPut.addHeader("Authorization",  TOKEN_BEARER+TOEKN);
        // �ж�map��Ϊ��
        if (map != null) {
            // ������Ų�����List����
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // ����map�����ò�����list��
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // ����form������
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");

            // �ѱ��������õ�httpPost��
            httpPut.setEntity(formEntity);
        }

        // ʹ��HttpClient�������󣬷���response
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPut);
        } catch (IOException e) {
            HttpResult httpResult = new HttpResult();
            httpResult.setCode(404);
            httpResult.setBody("����ʧ��");
            return httpResult;
        }

        // ����response��װ���ض���httpResult
        HttpResult httpResult = new HttpResult();
        // �������ݷ�װHttpResult
        if (response.getEntity() != null) {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(),EntityUtils.toString(response.getEntity(),"UTF-8"));
            httpResult.setCode(response.getStatusLine().getStatusCode());
            httpResult.setBody(EntityUtils.toString(response.getEntity(),"UTF-8"));

        } else {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(), "");
            httpResult.setCode(response.getStatusLine().getStatusCode());
            //httpResult.setBody("");
        }

        // ���ؽ��
        return httpResult;
    }

    /* *
     *  Delete����
     * @author miaoqixin
     * @date 2018/11/28 18:20
     * @param [url, map]
     * @return com.example.HttpClient.HttpResult
     */
    public HttpResult doDelete(String url, Map<String, Object> map , final String TOEKN) throws Exception {

        // ����URIBuilder
        URIBuilder uriBuilder = new URIBuilder(url);

        // �жϲ���map�Ƿ�Ϊ�ǿ�
        if (map != null) {
            // ��������
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                // ���ò���
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 2 ����httpGet�����൱������url�����ַ
        HttpDelete httpDelete = new HttpDelete(uriBuilder.build());
        httpDelete.addHeader("Authorization",  TOKEN_BEARER+TOEKN);
        // 3 ʹ��HttpClientִ��httpGet���൱�ڰ��س�����������
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpDelete);
        } catch (IOException e) {
            HttpResult httpResult = new HttpResult();
            httpResult.setCode(404);
            httpResult.setBody("����ʧ��");
            return httpResult;
        }
        // 4 �����������װ���ض���httpResult���൱����ʾ��Ӧ�Ľ��
        // ״̬��
        // response.getStatusLine().getStatusCode();
        // ��Ӧ�壬�ַ��������response.getEntity()Ϊ�գ������������ᱨ��,���Խ���֮ǰҪ���ǿյ��ж�
        // EntityUtils.toString(response.getEntity(), "UTF-8");
        HttpResult httpResult = new HttpResult();
        // �������ݷ�װHttpResult
        if (response.getEntity() != null) {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(),EntityUtils.toString(response.getEntity(),"UTF-8"));
            httpResult.setCode(response.getStatusLine().getStatusCode());
            httpResult.setBody(EntityUtils.toString(response.getEntity(),"UTF-8"));
        } else {
            //httpResult = new HttpResult(response.getStatusLine().getStatusCode(), "");
            httpResult.setCode(response.getStatusLine().getStatusCode());
            //httpResult.setBody("");
        }
        // ����
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

    	               reqEntity.addPart("file", file);// file1Ϊ�����̨��File upload;����

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
    	      // �ر����ӣ��ͷ���Դ
    	      httpClient.getConnectionManager().shutdown();
    	   }
    	   return result;
    	}
}