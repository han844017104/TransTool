package main;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: YuHang Han
 * @Description:
 * @Date: Create  on  15:36 2018/12/14
 * @Version: v 0.1
 */
public class TranTest {

    private static HashMap<String, String> uf = undoFiles();

    private static HashMap<String, String> ut = undoText();

    public static void main(String[] args) throws Exception {
        String rex = "\"([^\"]*)\"";
        String rex2 = "\\[[^\\[\\]]+\\]";
        String rex3 = "\\{([^\\}]+)\\}";


//        String s = "\"{i}No, not that kind of thirst, but the kind that can only by quenched by your [mom].{/i}\"";
//        String[] bls = GetRegResult(rex2, s);
//        String sendSt = bianLiangChuLi(bls, s, 1);
//        String[] bls2 = GetRegResult(rex3, sendSt);
//        String sendStr = bianLiangChuLi2(bls2, sendSt, 1);
//        String retStr = trans(sendStr);
//        String c = bianLiangChuLi(bls, retStr, 2);
//        String cn = bianLiangChuLi2(bls2, c, 2);
//        String endStr = cn.replace("“", "\"").replace("”", "\"");
//        System.out.println(endStr);

//
        String path = "F:\\1\\1\\TakeOver-0.8-pc\\game";
        List<String> files = findFiles(path);
        files.forEach(nowFIle -> {
            System.out.println("目前处理文件 ： \n" + nowFIle
                    + "\n====================================================");
            try {

                String file = readFileText(nowFIle);
                StringBuilder out = new StringBuilder(file);
                String[] syss = GetRegResult(rex, file);
                for (String s : syss) {
                    try {
                        if (s.contains(".png") || s.contains(".jpg") || s.contains(".mp3") || s.contains(".wav") || s.contains(".txt") || s.contains(".ttf")) {
                            continue;
                        }
                        if (ut.get(s.replace("\"", "")) != null) {
                            continue;
                        }
                        String s1 = s.replace("’", "'");
                        System.out.println("原语句 ：" + s1);
                        String[] bls = GetRegResult(rex2, s1);
                        String sendSt = bianLiangChuLi(bls, s1, 1);
                        String[] bls2 = GetRegResult(rex3, sendSt);
                        String sendStr = bianLiangChuLi2(bls2, sendSt, 1);
                        String retStr = trans(sendStr);
                        String c = bianLiangChuLi(bls, retStr, 2);
                        String cn = bianLiangChuLi2(bls2, c, 2);
                        String endStr = cn.substring(1, cn.length()).replace("\"", "'");
                        endStr = "\"" + endStr + "\"";
                        System.out.println("处理后 ：" + endStr);
                        int start = out.indexOf(s);
                        int end = start + s.length();
                        out.replace(start, end, endStr);
//                        Thread.sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("-----!!!!------!!!!------!!!!------发生未知错误！！ 当前语句 ： \n" + s);
                    }

                }
                String outPath = nowFIle.replace("\\game\\", "\\trans\\");
                File outFile = new File(outPath);
                if (outFile.exists()) outFile.delete();
                outFile.createNewFile();
                FileWriter writer = new FileWriter(outPath);
                writer.write(out.toString());
                writer.close();
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("处理文件 ： \n" + nowFIle
                    + " 完成！\n=========================== Done =========================\n\n");
        });
    }

    private static HashMap<String, String> undoFiles() {
        HashMap<String, String> map = new HashMap<>();
        map.put("blank.rpy", "blank.rpy");
        map.put("gui.rpy", "blank.rpy");
        map.put("institutions.rpy", "blank.rpy");
        map.put("game control.rpy", "blank.rpy");
        map.put("images.rpy", "blank.rpy");
        map.put("names.rpy", "blank.rpy");
        map.put("navigation.rpy", "blank.rpy");
        map.put("options.rpy", "blank.rpy");
        map.put("screens.rpy", "blank.rpy");
        map.put("script.rpy", "blank.rpy");
        map.put("update.rpy", "blank.rpy");
        return map;
    }

    private static HashMap<String, String> undoText() {
        HashMap<String, String> map = new HashMap<>();
        map.put("Monday", "blank.rpy");
        map.put("Tuesday", "blank.rpy");
        map.put("Wednesday", "blank.rpy");
        map.put("Thursday", "blank.rpy");
        map.put("Friday", "blank.rpy");
        map.put("Saturday", "blank.rpy");
        map.put("Sunday", "blank.rpy");
        map.put("Secretary", "blank.rpy");
        map.put("Counselor", "blank.rpy");
        map.put("Research", "blank.rpy");
        map.put("Mechanic", "blank.rpy");
        return map;
    }

    private static String bianLiangChuLi(String[] bls, String orign, int type) {
        if (bls.length > 0) {
            if (type == 1) {
                StringBuilder sb = new StringBuilder(orign);
                for (int i = 0; i < bls.length; i++) {
                    int start;
                    sb.replace(start = sb.indexOf(bls[i]), start + bls[i].length(), "[****]");
                }
                return sb.toString();
            } else {
                StringBuilder sb = new StringBuilder(orign);
                try {
                    for (int i = 0; i < bls.length; i++) {
                        int leg = 6;
                        int start = sb.indexOf("[****]");
                        if (start < 0) {
                            leg = 4;
                            start = sb.indexOf("[**]");
                        }
                        if (start < 0) {
                            leg = 5;
                            start = sb.indexOf("[**.]");
                        }
                        if (start < 0) {
                            leg = 7;
                            start = sb.indexOf("[ *** ]");
                        }
                        if (start < 0) {
                            leg = 3;
                            start = sb.indexOf("***");
                        }
                        if (start > 0) {
                            sb.replace(start, start + leg, bls[i]);
                        } else {
                            System.out.println("-----------!!------------未找到 [] 变量坐标 ！ 语句 ：" + orign);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("-----------!!!!!!------------反填充 [] 变量出错！ 语句 ： \n" + orign + "\n变量集合：" + Arrays.toString(bls));
                }
                return sb.toString();
            }
        }
        return orign;
    }

    private static String bianLiangChuLi2(String[] bls, String orign, int type) {
        if (bls.length > 0) {
            if (type == 1) {
                StringBuilder sb = new StringBuilder(orign);
                for (int i = 0; i < bls.length; i++) {
                    int start;
                    sb.replace(start = sb.indexOf(bls[i]), start + bls[i].length(), "[[**]]");
                }
                return sb.toString();
            } else {
                StringBuilder sb = new StringBuilder(orign);
                try {
                    for (int i = 0; i < bls.length; i++) {
                        int leng = 6;
                        int start = sb.indexOf("[[**]]");
                        if (start < 0) {
                            leng = 8;
                            start = sb.indexOf("[[* *]]");
                        }
                        if (start < 0) {
                            start = sb.indexOf("* * * * * *");
                            leng = 11;
                        }
                        if (start > 0) {
                            sb.replace(start, start + leng, bls[i]);
                        } else {
                            System.out.println("-----------!!------------未找到 {} 变量坐标 ！语句 ：" + orign);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("-----------!!!!!!------------反填充 {} 变量出错！ 语句 ： \n" + orign + "\n变量集合：" + Arrays.toString(bls));
                }

                return sb.toString();
            }
        }
        return orign;
    }


    private static List<String> findFiles(String filepath) {
        ArrayList<String> files = new ArrayList<>();
        try {
            File file = new File(filepath);
            if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        if (readfile.getName().endsWith(".rpy")) {
                            if (uf.get(readfile.getName()) == null) {
                                files.add(readfile.getPath());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("readfile()   Exception:\n" + e.getMessage());
        }
        return files;
    }

    public static String transFullText(String path) {
        StringBuffer bf = new StringBuffer();
        try {
            String filePath = path;
            String file = readFileText(filePath);
            String[] result = file.split("[\n\r]");
            for (String s : result) {
                if (s.replace(" ", "").length() > 0) {
                    bf.append(trans(s)).append("\n");
                    Thread.sleep(2000);
                }
            }
            System.out.println(bf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return bf.toString();
        }
    }

    private static String readFileText(String path) throws IOException {
        File file = new File(path);
        byte[] filebytes = new byte[((Long) file.length()).intValue()];
        FileInputStream ips = new FileInputStream(file);
        try {
            ips.read(filebytes);
            ips.close();
        } finally {
            ips.close();
        }
        return new String(filebytes, StandardCharsets.UTF_8);
    }

    private static String[] GetRegResult(String rex, String text) {
        Pattern p = Pattern.compile(rex);
        Matcher m = p.matcher(text);

        ArrayList<String> tempList = new ArrayList<String>();
        while (m.find()) {
            tempList.add(m.group());
        }
        String[] res = new String[tempList.size()];
        int i = 0;
        for (String temp : tempList) {
            res[i] = temp;
            i++;
        }
        return res;
    }

    private static String trans(String line) throws UnsupportedEncodingException {
        String GoogleUrl = "http://translate.google.cn/translate_a/single";
        String YouDaoUrl = "http://fanyi.youdao.com/translate";
        String BaiduUrl = "http://api.fanyi.baidu.com/api/trans/vip/translate";
        Map<String, String> map = buildParam(line);
        return parseRet(post(BaiduUrl, map));
    }

    private static Map<String, String> buildParam(String text) throws UnsupportedEncodingException {
        return BaiduParam(text);
    }

    private static Map<String, String> GoogleParam(String text) {
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

    private static Map<String, String> YouDaoParam(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put("doctype", "json");
        map.put("type", "AUTO");
        map.put("i", text);
        return map;
    }

    private static Map<String, String> BaiduParam(String text) throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<>();
        String salt = random();
        String signUnCode = new String(("20181216000248995" + text + salt + "nCSND5kI8e2YvVmcMPgt").getBytes(), "UTF-8");
        String sign = DigestUtils.md5Hex(signUnCode);
        map.put("from", "en");
        map.put("to", "zh");
        map.put("appid", "20181216000248995");
        map.put("salt", salt);
        map.put("sign", sign);
        map.put("q", text);
        return map;
    }

    private static String random() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    private static String parseRet(String orign) {
        return BaiDuParse(orign);
    }

    private static String GoogleParse(String orign) {
        JSONArray array = JSONArray.parseArray(JSONObject.parseObject(orign).getString("sentences"));
        StringBuffer buffer = new StringBuffer();
        array.forEach(ret -> {
            JSONObject o = JSONObject.parseObject(ret.toString());
            buffer.append(o.getString("trans"));
        });
        return buffer.toString();
    }

    private static String YouDaoParse(String orign) {
        JSONArray array = JSONArray.parseArray(JSONObject.parseObject(orign).getString("translateResult")).getJSONArray(0);
        StringBuffer buffer = new StringBuffer();
        array.forEach(ret -> {
            JSONObject o = JSONObject.parseObject(ret.toString());
            buffer.append(o.getString("tgt"));
        });
        return buffer.toString();
    }

    private static String BaiDuParse(String orign) {
        JSONArray array = JSONArray.parseArray(JSONObject.parseObject(orign).getString("trans_result"));
        StringBuffer buffer = new StringBuffer();
        array.forEach(ret -> {
            JSONObject o = JSONObject.parseObject(ret.toString());
            buffer.append(o.getString("dst"));
        });
        return buffer.toString();
    }

    private static String post(String url, String stringEntity) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpclient = HttpClients.custom().build();
        String ret = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(5000).setConnectTimeout(2500)
                    .setSocketTimeout(5000).build();
            httpPost.setConfig(requestConfig);
            HttpEntity entity;
            entity = new StringEntity(stringEntity, Consts.UTF_8);
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpclient.execute(httpPost);
            ret = responseConvert(httpResponse);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.abort();
        }
        return ret;
    }

    private static String post(String url, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpclient = HttpClients.custom().build();
        String ret = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(5000).setConnectTimeout(2500)
                    .setSocketTimeout(5000).build();
            httpPost.setConfig(requestConfig);
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            Set<String> key = params.keySet();
            for (String k : key) {
                formparams.add(new BasicNameValuePair(k, params.get(k)));
            }
            UrlEncodedFormEntity entity;
            entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpclient.execute(httpPost);
            ret = responseConvert(httpResponse);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.abort();
        }
        return ret;
    }

    private static String responseConvert(HttpResponse httpResponse) throws IOException, HttpException {
        String result = "";
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            byte[] content = EntityUtils.toByteArray(entity);
            ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();
            if (charset == null) {
                charset = HTTP.DEF_CONTENT_CHARSET;
            }
            result = new String(content, charset.name());
        }
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            System.out.println(httpResponse);
            throw new HttpException();
        }
        return result;
    }
}
