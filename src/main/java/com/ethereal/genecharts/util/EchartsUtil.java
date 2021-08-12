package com.ethereal.genecharts.util;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
/**
 * @author Administrator
 * @Description
 * @create 2021-06-09 13:22
 */
public class EchartsUtil {
    private static String url = "http://localhost:6666";
    private static final String SUCCESS_CODE = "1";

    public static String generateEchartsBase64(String option) throws IOException {
        String base64 = "";
        if (option == null) {
            return base64;
        }
        option = option.replaceAll("\\s+", "").replaceAll("\"", "'");

        // 将option字符串作为参数发送给echartsConvert服务器
        Map<String, String> params = new HashMap<>(2);
        params.put("opt", option);
        params.put("width", "1000");
        params.put("height", "600");
        String response = HttpUtil.post(url, params, "utf-8");

        // 解析echartsConvert响应
        JSONObject responseJson = JSON.parseObject(response);
        String code = responseJson.getString("code");

        // 如果echartsConvert正常返回
        if (SUCCESS_CODE.equals(code)) {
            base64 = responseJson.getString("data");
        }
        // 未正常返回
        else {
            String string = responseJson.getString("msg");
            throw new RuntimeException(string);
        }

        return base64;
    }
}
