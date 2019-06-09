package com.example.HttpClient;
import java.io.Serializable;

import lombok.Data;
/**
 * @program: webservice_demo
 * @description: HttpClient���ض���
 * @author: miaoqixin
 * @create: 2018-11-28 16:54
 **/
@Data
public class HttpResult implements Serializable {

    // ��Ӧ��״̬��
    private int code;

    // ��Ӧ����Ӧ��
    private String body;

}