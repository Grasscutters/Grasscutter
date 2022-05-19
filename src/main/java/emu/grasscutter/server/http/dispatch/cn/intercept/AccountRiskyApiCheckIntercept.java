package emu.grasscutter.server.http.dispatch.cn.intercept;

import com.github.monkeywie.proxyee.intercept.HttpProxyIntercept;
import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import emu.grasscutter.server.http.dispatch.cn.ProxyConstant;
import emu.grasscutter.server.http.dispatch.cn.util.ProxyUtil;
import io.netty.channel.Channel;


/**
 * 账户风险api检查拦截(验证码)
 *
 * @author litht
 * @date 2022/05/19
 * 测试例子（post）
 * https://gameapi-account.mihoyo.com/account/risky/api/check?
 */
public class AccountRiskyApiCheckIntercept extends HttpProxyIntercept {
    @Override
    public void beforeConnect(Channel clientChannel, HttpProxyInterceptPipeline pipeline) throws Exception {
        if (pipeline.getRequestProto().getHost().contains(ProxyConstant.GAMEAPI_ACCOUNT_HOST)) {
            if (pipeline.getHttpRequest().uri().contains(ProxyConstant.ACCOUNT_RISKY_API_CHECK_PATH)) {
                ProxyUtil.forwardToGrasscutter(pipeline);
            }
        }
        super.beforeConnect(clientChannel, pipeline);
    }
}
