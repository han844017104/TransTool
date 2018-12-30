package main.bean;

/**
 * 　　　Create   By   Mr.Han
 * 　                 　　　　　------   On   2018/12/23  15:42
 */
public enum ApiTypeInfo {
    BaiDu(1, "http://api.fanyi.baidu.com/api/trans/vip/translate"),
    Google(1, "http://translate.google.cn/translate_a/single"),
    YouDao(1, "http://fanyi.youdao.com/translate");

    private int type;

    private String url;

    ApiTypeInfo(int type, String url) {
        this.type = type;
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
