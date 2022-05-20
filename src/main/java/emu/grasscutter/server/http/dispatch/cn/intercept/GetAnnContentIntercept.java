package emu.grasscutter.server.http.dispatch.cn.intercept;

import com.github.monkeywie.proxyee.intercept.HttpProxyInterceptPipeline;
import com.github.monkeywie.proxyee.intercept.common.FullRequestIntercept;
import emu.grasscutter.server.http.dispatch.cn.ProxyConstant;
import emu.grasscutter.server.http.dispatch.cn.util.ProxyUtil;
import emu.grasscutter.server.http.dispatch.cn.util.UrlUtils;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author litht
 * https://hk4e-api-static.mihoyo.com/common/hk4e_cn/announcement/api/getAnnContent?game=hk4e&game_biz=hk4e_cn&lang=zh-cn&bundle_id=hk4e_cn&platform=pc&region=cn_gf01&t=1652104806&level=59&channel_id=1
 */
public class GetAnnContentIntercept extends FullRequestIntercept {
    @Override
    public boolean match(HttpRequest httpRequest, HttpProxyInterceptPipeline pipeline) {
        if(pipeline.getRequestProto().getHost().contains(ProxyConstant.HK4E_API_STATIC_HOST)){
            if(httpRequest.uri().contains(ProxyConstant.COMMON_HK4E_CN_ANNOUNCEMENT_API_GETANNCONTENT_PATH)){
                return true;
            }
        }
        return false;
    }
    @Override
    public void handleRequest(FullHttpRequest httpRequest, HttpProxyInterceptPipeline pipeline) {
        String uri = httpRequest.uri();
        uri=UrlUtils.addParam(uri,"level","60",true);
        httpRequest.setUri(uri);
    }
}
