package com.example.HttpClient;
import java.io.Serializable;

import lombok.Data;
/**
 * @program: webservice_demo
 * @description: HttpClient返回对象
 * @author: miaoqixin
 * @create: 2018-11-28 16:54
 **/
@Data
public class HttpResult implements Serializable {

    // 响应的状态码
    private int code;

    // 响应的响应体
    private String body;

}