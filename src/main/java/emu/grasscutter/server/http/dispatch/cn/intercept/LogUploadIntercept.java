package emu.grasscutter.server.http.dispatch.cn.intercept;

import com.github.monkeywie.proxyee.intercept.HttpProxyIntercept;
import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;

import static emu.grasscutter.server.http.dispatch.cn.ProxyConstant.LOG_UPLOAD_HOST;

/**
 * 日志上传拦截
 *
 * @author litht
 * @date 2022/05/18
 * 请求测试例子 方法post
 * https://log-upload.mihoyo.com/sdk/dataUpload
 */
public class LogUploadIntercept extends HttpProxyIntercept {
    @Override
    public void beforeConnect(Channel clientChannel, HttpProxyInterceptPipeline pipeline) throws Exception {
        System.out.println("1");
        /* 拦截日志上传，重定向到我们自己的服务器 */
        if (pipeline.getRequestProto().getHost().contains(LOG_UPLOAD_HOST)) {
            pipeline.getRequestProto().setHost("127.0.0.1");
            pipeline.getRequestProto().setPort(443);
            System.out.println("2");
        }
        pipeline.beforeConnect(clientChannel);

    }

    @Override
    public void beforeRequest(Channel clientChannel, HttpRequest httpRequest, HttpProxyInterceptPipeline pipeline) throws Exception {
        System.out.println("2");
        if (pipeline.getRequestProto().getHost().contains(LOG_UPLOAD_HOST)) {
            httpRequest.setMethod(HttpMethod.POST);
        }
        super.beforeRequest(clientChannel, httpRequest, pipeline);
    }
}
