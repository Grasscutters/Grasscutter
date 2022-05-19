package emu.grasscutter.server.http.dispatch.cn.intercept;

import com.github.monkeywie.proxyee.intercept.HttpProxyIntercept;
import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import emu.grasscutter.server.http.dispatch.cn.ProxyConstant;
import emu.grasscutter.server.http.dispatch.cn.util.ProxyUtil;
import io.netty.channel.Channel;


/**
 * testUrl（post）
 * https://hk4e-sdk.mihoyo.com/hk4e_cn/combo/granter/login/v2/login?
 *
 * @author litht
 * @date 2022/05/19
 */
public class LoginV2Intercept extends HttpProxyIntercept {
    @Override
    public void beforeConnect(Channel clientChannel, HttpProxyInterceptPipeline pipeline) throws Exception {
        if (pipeline.getRequestProto().getHost().contains(ProxyConstant.HK4E_SDK_HOST)) {
            if (pipeline.getHttpRequest().uri().contains(ProxyConstant.HK4E_CN_COMBO_GRANTER_LOGIN_V2_LOGIN_PATH)) {
                ProxyUtil.forwardToGrasscutter(pipeline);
            }
        }
        super.beforeConnect(clientChannel, pipeline);
    }


}
