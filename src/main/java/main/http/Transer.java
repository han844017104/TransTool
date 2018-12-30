package main.http;


import main.bean.ApiTypeInfo;

import java.util.Map;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/23  15:37
 */
public class Transer {

    private ApiTypeInfo type;

    public ApiTypeInfo getType() {
        return type;
    }

    public void setType(ApiTypeInfo type) {
        this.type = type;
    }

    public Transer(ApiTypeInfo type) {

        this.type = type;
    }

    public String trans(String text){
        Map<String, String> params = ParamBuilder.build(text, this.type);
        HttpTool.RespData data = HttpTool.post(this.type.getUrl(), params, 5000);
        return RespParse.parse(data.getResponse(), this.type);
    }
}
