package emu.grasscutter.server.http.dispatch.cn.util;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static emu.grasscutter.Configuration.HTTP_INFO;
import static emu.grasscutter.Configuration.lr;


/**
 * Proxy util
 *
 * @author litht
 * @date 2022/05/19
 */
public class ProxyUtil {
    /**
     * @param pipeline
     */
    public static void forwardToGrasscutter(HttpProxyInterceptPipeline pipeline) {
        pipeline.getRequestProto().setHost(HTTP_INFO.accessAddress);
        pipeline.getRequestProto().setPort(lr(HTTP_INFO.bindPort, HTTP_INFO.bindPort));
    }
    /**

     * 在指定url后追加参数

     * @param url

     * @param data 参数集合 key = value

     * @return

     */

    public static String appendUrl(String url, Map data) {

        String newUrl = url;

        StringBuffer param = new StringBuffer();

        for (Object key : data.keySet()) {

            param.append(key + "=" + data.get(key).toString() + "&");

        }

        String paramStr = param.toString();

        paramStr = paramStr.substring(0, paramStr.length() - 1);

        if (newUrl.indexOf("?") >= 0) {

            newUrl += "&" + paramStr;

        } else {

            newUrl += "?" + paramStr;

        }

        return newUrl;

    }

/**

 * 获取指定url中的某个参数

 * @param url

 * @param name

 * @return

 */
public static String getParamByUrl(String url, String name) {

    url += "&";

    String pattern = "(\\?|&){1}#{0,1}" + name + "=[a-zA-Z0-9]*(&{1})";

    Pattern r = Pattern.compile(pattern);

    Matcher m = r.matcher(url);

    if (m.find( )) {

        System.out.println(m.group(0));

        return m.group(0).split("=")[1].replace("&", "");

    } else {

        return null;

    }

}
    public static String replaceUrlParam(String url, String paramName, String paramValue) {
        if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(paramValue)) {
            int index = url.indexOf(paramName + "=");
            if (index != -1) {
                StringBuilder sb = new StringBuilder();
                sb.append(url.substring(0, index)).append(paramName).append("=").append(paramValue);
                int idx = url.indexOf("&", index);
                if (idx != -1) {
                    sb.append(url.substring(idx));
                }
                url = sb.toString();
            }
        }
        return url;
    }



}
