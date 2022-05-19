package emu.grasscutter.server.http.dispatch.cn.util;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;

import static emu.grasscutter.Configuration.HTTP_INFO;
import static emu.grasscutter.Configuration.lr;


/**
 * 代理工具类
 *
 * @author litht
 * @date 2022/05/19
 */
public class ProxyUtil {
    /**
     * 连接重定向到grasscutter的dispatch
     *
     * @param pipeline 管道
     */
    public static void forwardToGrasscutter(HttpProxyInterceptPipeline pipeline) {
        pipeline.getRequestProto().setHost(HTTP_INFO.accessAddress);
        pipeline.getRequestProto().setPort(lr(HTTP_INFO.bindPort, HTTP_INFO.bindPort));
    }
}
