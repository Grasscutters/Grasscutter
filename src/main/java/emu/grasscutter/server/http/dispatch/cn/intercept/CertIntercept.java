package emu.grasscutter.server.http.dispatch.cn.intercept;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.CertDownIntercept;
import io.netty.channel.Channel;

public class CertIntercept extends CertDownIntercept {
    @Override
    public void beforeConnect(Channel clientChannel, HttpProxyInterceptPipeline pipeline) throws Exception {
        pipeline.beforeConnect(clientChannel);
    }
}
