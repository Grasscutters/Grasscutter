package emu.grasscutter.server.http.dispatch.cn.intercept;

import com.github.monkeywie.proxyee.intercept.HttpProxyIntercept;
import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import emu.grasscutter.server.http.dispatch.cn.ProxyConstant;
import emu.grasscutter.server.http.dispatch.cn.util.ProxyUtil;
import io.netty.channel.Channel;



/**
 *
 *
 * @author litht
 * @date 2022/05/18
 * testUrl（post）
 * https://log-upload.mihoyo.com/sdk/dataUpload
 */
public class LogUploadIntercept extends HttpProxyIntercept {
    @Override
    public void beforeConnect(Channel clientChannel, HttpProxyInterceptPipeline pipeline) throws Exception {

        /* 拦截日志上传，重定向到我们自己的服务器 */
        if (pipeline.getRequestProto().getHost().contains(ProxyConstant.LOG_UPLOAD_HOST)) {
            ProxyUtil.forwardToGrasscutter(pipeline);
        }
        super.beforeConnect(clientChannel, pipeline);

    }
}
