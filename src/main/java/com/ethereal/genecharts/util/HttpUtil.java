package com.ethereal.genecharts.util;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author Administrator
 * @Description
 * @create 2021-06-09 13:20
 */
public class HttpUtil {

    public static String post(String url, Map<String, String> params, String charset)
            throws IOException {
        String responseEntity = "";

        // 创建CloseableHttpClient对象
        CloseableHttpClient client = HttpClients.createDefault();

        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        // 设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        httpPost.setConfig(requestConfig);

        // 生成请求参数
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if (params != null) {
            for (Entry<String, String> entry : params.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

        }

        // 将参数添加到post请求中
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, charset));

        // 发送请求，获取结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);

        // 获取响应实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 按指定编码转换结果实体为String类型
            responseEntity = EntityUtils.toString(entity, charset);
        }

        // 释放资源
        EntityUtils.consume(entity);
        response.close();

        return responseEntity;
    }
}