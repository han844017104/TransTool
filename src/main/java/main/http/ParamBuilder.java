package main.http;

import main.bean.ApiTypeInfo;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/23  15:14
 */
public class ParamBuilder {

    private static final String BaiDuAppid = "20181216000248995";

    private static final String BaiDuKey = "nCSND5kI8e2YvVmcMPgt";

    public static Map<String, String> build(String text, ApiTypeInfo type) {
        switch (type) {
            case BaiDu:
                return BaiduParam(text);
            case Google:
                return GoogleParam(text);
            case YouDao:
                return YouDaoParam(text);
            default:
                return new HashMap<>();
        }
    }

    public static Map<String, String> GoogleParam(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put("client", "gtx");
        map.put("dt", "t");
        map.put("dj", "1");
        map.put("ie", "UTF-8");
        map.put("sl", "auto");
        map.put("tl", "zh_CN");
        map.put("q", text);
        return map;
    }

    public static Map<String, String> YouDaoParam(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put("doctype", "json");
        map.put("type", "AUTO");
        map.put("i", text);
        return map;
    }

    public static Map<String, String> BaiduParam(String text) {
        HashMap<String, String> map = new HashMap<>();
        String salt = random();
        String signUnCode = null;
        try {
            signUnCode = new String((BaiDuAppid + text + salt + BaiDuKey).getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sign = DigestUtils.md5Hex(signUnCode);
        map.put("from", "en");
        map.put("to", "zh");
        map.put("appid", BaiDuAppid);
        map.put("salt", salt);
        map.put("sign", sign);
        map.put("q", text);
        return map;
    }

    private static String random() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

}
