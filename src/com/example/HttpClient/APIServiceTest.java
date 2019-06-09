package com.example.HttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.example.entity.Menu;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @program: webservice_demo
 * @description: 调用测试
 * @author: miaoqixin
 * @create: 2018-11-28 17:21
 **/

@SuppressWarnings("all")
public class APIServiceTest {

	private HttpClientUtil apiService;
	private final static String API_URL = "http://192.168.1.116:9527/";
	private final static String TOKEN = "";

	String jsonData = "";

	@Before
	public void init() {
		this.apiService = new HttpClientUtil();
	}

	// 查询

	public void testQueryItemById() throws Exception {
		// http://manager.aaaaaa.com/rest/item/interface/{id}
		String url = API_URL + "/api/plant/getCkxx";
		Map<String, Object> map = new HashMap<>();
		map.put("ckxxId", 22222);
		// map.put("name",33333);
		HttpResult httpResult = apiService.doPost(url, map, TOKEN);
		System.out.println(httpResult.getCode());
		System.out.println(httpResult.getBody());

	}

	// 新增

	public void testSaveItem() throws Exception {
		// http://manager.aaaaaa.com/rest/item/interface/{id}
		String url = API_URL + "/login";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", "ff");
		map.put("password", "123456");
		HttpResult httpResult = apiService.doPost(url, map, TOKEN);
		System.out.println(httpResult.getCode());
		System.out.println(httpResult.getBody());

	}

	// 修改

	public void testUpdateItem() throws Exception {
		// http://manager.aaaaaa.com/rest/item/interface/{id}
		String url = "http://manager.aaaaaa.com/rest/item/interface";
		Map<String, Object> map = new HashMap<String, Object>();
		// title=测试RESTful风格的接口&price=1000&num=1&cid=888&status=1
		map.put("title", "测试APIService调用修改接口");
		map.put("id", "44");
		HttpResult httpResult = apiService.doPut(url, map, TOKEN);
		System.out.println(httpResult.getCode());
		System.out.println(httpResult.getBody());
	}

	// 删除
	@Test
	public void testDeleteItemById() throws Exception {
		// http://manager.aaaaaa.com/rest/item/interface/{id}
		String url = "http://localhost:9527/api/file/upload";
		Map<String, String> map = new HashMap<String, String>();

		String data = apiService.sendFilesPost(url, "D://api.zip;D://近在远方.mp3");
		System.out.println(data);

	}

	public static String txt2String(File file) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result.append(System.lineSeparator() + s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public static Map<String, Object> convertBeanToMap(Menu menu) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("uuid", menu.getUuid());
		data.put("module_name", menu.getModule_name());
		data.put("pid", menu.getPid());
		data.put("moduel", menu.getModuel());
		data.put("create_date", menu.getCreate_date());
		return data;

	}

	
	public void main() {
		String url = "http://192.168.1.116:9527/api/user/saveUser";
		try {
			String txt2String = txt2String(new File("D://menu.txt"));
			Menu[] fromJson = new Gson().fromJson(txt2String, Menu[].class);
			for (Menu menu : fromJson) {
				Map<String, Object> menuData = convertBeanToMap(menu);
				HttpResult doPost = apiService.doPost(url, menuData, TOKEN);
				System.out.println(doPost.getBody());

			}

		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	 

}