package main.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import main.bean.ApiTypeInfo;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/23  15:29
 */
public class RespParse {

    public static String parse(String resp, ApiTypeInfo type){
        switch (type) {
            case BaiDu:
                return BaiDuParse(resp);
            case Google:
                return GoogleParse(resp);
            case YouDao:
                return YouDaoParse(resp);
            default:
                return resp;
        }
    }

    public static String GoogleParse(String orign) {
        JSONArray array = JSONArray.parseArray(JSONObject.parseObject(orign).getString("sentences"));
        StringBuffer buffer = new StringBuffer();
        array.forEach(ret -> {
            JSONObject o = JSONObject.parseObject(ret.toString());
            buffer.append(o.getString("trans"));
        });
        return buffer.toString();
    }

    public static String YouDaoParse(String orign) {
        JSONArray array = JSONArray.parseArray(JSONObject.parseObject(orign).getString("translateResult")).getJSONArray(0);
        StringBuffer buffer = new StringBuffer();
        array.forEach(ret -> {
            JSONObject o = JSONObject.parseObject(ret.toString());
            buffer.append(o.getString("tgt"));
        });
        return buffer.toString();
    }

    public static String BaiDuParse(String orign) {
        JSONArray array = JSONArray.parseArray(JSONObject.parseObject(orign).getString("trans_result"));
        StringBuffer buffer = new StringBuffer();
        array.forEach(ret -> {
            JSONObject o = JSONObject.parseObject(ret.toString());
            buffer.append(o.getString("dst"));
        });
        return buffer.toString();
    }
}
